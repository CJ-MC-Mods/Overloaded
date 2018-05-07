package com.cjm721.overloaded.block.reactor;

import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.storage.fluid.LongFluidStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class TileFusionCore extends TileEntity {

    private LongFluidStorage fluidStorage;
    private LongEnergyStorage energyStorage;

    public TileFusionCore() {

    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing != null && facing.getAxis().isVertical()) {
            if (capability == FLUID_HANDLER_CAPABILITY) {
                return true;
            }
        } else {
            if (capability == ENERGY) {
                return true;
            }
        }

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing != null && facing.getAxis().isVertical()) {
            if (capability == FLUID_HANDLER_CAPABILITY) {
                return null;
            }
        } else {
            if (capability == ENERGY) {
                return null;
            }
        }
        return null;
    }

}
