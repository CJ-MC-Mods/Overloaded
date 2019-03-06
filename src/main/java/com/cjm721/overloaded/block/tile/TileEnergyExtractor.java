package com.cjm721.overloaded.block.tile;

import com.cjm721.overloaded.storage.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.ForgeEnergyZero;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileEnergyExtractor extends AbstractTileEntityFaceable implements ITickable {

    public TileEnergyExtractor() {
    }


    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if(getWorld().isRemote) {
            return;
        }

        BlockPos me = this.getPos();
        TileEntity frontTE = getWorld().getTileEntity(me.add(getFacing().getDirectionVec()));

        if (frontTE == null || !frontTE.hasCapability(HYPER_ENERGY_HANDLER, getFacing().getOpposite()))
            return;

        IHyperHandlerEnergy storage = frontTE.getCapability(HYPER_ENERGY_HANDLER, getFacing().getOpposite());
        for (EnumFacing facing : EnumFacing.values()) {
            LongEnergyStack energy = storage.take(new LongEnergyStack(Long.MAX_VALUE), false);
            if (energy.getAmount() == 0L)
                return;

            if (facing == getFacing())
                continue;

            TileEntity te = world.getTileEntity(me.add(facing.getDirectionVec()));
            if (te == null || !te.hasCapability(ENERGY, facing.getOpposite()))
                continue;

            IEnergyStorage receiver = te.getCapability(ENERGY, facing.getOpposite());
            if (!receiver.canReceive())
                continue;

            int acceptedAmount = receiver.receiveEnergy((int) Math.min(energy.getAmount(), Integer.MAX_VALUE), true);
            if (acceptedAmount != 0) {
                LongEnergyStack actualTaken = storage.take(new LongEnergyStack(acceptedAmount), true);
                receiver.receiveEnergy((int) Math.min(actualTaken.getAmount(), Integer.MAX_VALUE), false);
            }
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == ENERGY) {
            return (T) new ForgeEnergyZero();
        }
        return super.getCapability(capability, facing);
    }
}
