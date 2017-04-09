package com.cjm721.overloaded.common.block.tile;

import com.cjm721.overloaded.common.storage.fluid.LongFluidStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

/**
 * Created by CJ on 4/8/2017.
 */
public class TileInfiniteTank extends TileEntity {

    LongFluidStorage fluidStorage;

    public TileInfiniteTank() {
        fluidStorage = new LongFluidStorage();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        return fluidStorage.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        fluidStorage.readFromNBT(compound);
    }

    public LongFluidStorage getStorage() {
        return fluidStorage;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return (T) fluidStorage;
        }
        return super.getCapability(capability, facing);
    }
}
