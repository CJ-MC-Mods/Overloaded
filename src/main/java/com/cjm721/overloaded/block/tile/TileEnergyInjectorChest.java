package com.cjm721.overloaded.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileEnergyInjectorChest extends AbstractTileEntityFaceable implements ITickable {

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        BlockPos me = this.getPos();
        TileEntity frontTE = getWorld().getTileEntity(me.add(getFacing().getDirectionVec()));

        if(frontTE == null || !frontTE.hasCapability(ENERGY, getFacing().getOpposite()))
            return;

        IEnergyStorage storage = frontTE.getCapability(ENERGY, getFacing().getOpposite());
        int energy = storage.extractEnergy(Integer.MAX_VALUE,false);
        for(EnumFacing facing: EnumFacing.values()) {
            if(energy == 0)
                return;

            if(facing == getFacing())
                continue;

            TileEntity te = world.getTileEntity(me.add(facing.getDirectionVec()));
            if(te == null || !te.hasCapability(ITEM_HANDLER_CAPABILITY, facing.getOpposite()))
                continue;

            IItemHandler inventory = te.getCapability(ITEM_HANDLER_CAPABILITY, facing.getOpposite());

            for(int i = 0; i < inventory.getSlots(); i++) {
                ItemStack stack = inventory.getStackInSlot(i);

                if(!stack.hasCapability(ENERGY, facing.getOpposite())) {
                    continue;
                }

                stack = inventory.extractItem(i,1,false);

                if(stack.isEmpty())
                    continue;

                IEnergyStorage receiver = stack.getCapability(ENERGY, facing.getOpposite());
                if(!receiver.canReceive())
                    continue;

                int acceptedAmount = receiver.receiveEnergy(energy,true);
                if(acceptedAmount != 0) {
                    receiver.receiveEnergy(acceptedAmount,false);
                    energy -= storage.receiveEnergy(energy, true);
                }

                stack = inventory.insertItem(i,stack,false);

                if(stack.isEmpty()) {
                    continue;
                }

                getWorld().spawnEntity(new EntityItem(getWorld(),getPos().getX(),getPos().getY(),getPos().getZ(),stack));
            }
        }
    }
}
