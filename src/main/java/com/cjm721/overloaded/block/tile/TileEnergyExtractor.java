package com.cjm721.overloaded.block.tile;

import com.cjm721.overloaded.storage.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileEnergyExtractor extends TileEntity implements ITickable {

    private EnumFacing front;

    public TileEnergyExtractor() {

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.front = EnumFacing.getFront(compound.getInteger("Front"));
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Front", this.front.getIndex());

        return super.writeToNBT(compound);
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        BlockPos me = this.getPos();
        TileEntity frontTE = getWorld().getTileEntity(me.add(front.getDirectionVec()));

        if(frontTE == null || !frontTE.hasCapability(HYPER_ENERGY_HANDLER, front.getOpposite()))
            return;

        IHyperHandlerEnergy storage = frontTE.getCapability(HYPER_ENERGY_HANDLER, front.getOpposite());
        LongEnergyStack energy = storage.take(new LongEnergyStack(Long.MAX_VALUE),false);
        for(EnumFacing facing: EnumFacing.values()) {
            if(energy.getAmount() == 0L)
                return;

            if(facing == front)
                continue;

            TileEntity te = world.getTileEntity(me.add(facing.getDirectionVec()));
            if(te == null || !te.hasCapability(ENERGY, facing.getOpposite()))
                continue;

            IEnergyStorage receiver = te.getCapability(ENERGY, facing.getOpposite());
            if(!receiver.canReceive())
                continue;

            int acceptedAmount = receiver.receiveEnergy((int) Math.min(energy.getAmount(), Integer.MAX_VALUE),true);
            if(acceptedAmount != 0) {
                receiver.receiveEnergy(acceptedAmount,false);
                energy = storage.take(new LongEnergyStack(acceptedAmount), true);
            }
        }
    }

    public TileEnergyExtractor setFacing(EnumFacing front) {
        this.front = front;
        return this;
    }

    public EnumFacing getFacing() {
        return front;
    }
}
