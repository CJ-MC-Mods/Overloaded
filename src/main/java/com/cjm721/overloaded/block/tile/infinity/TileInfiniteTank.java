package com.cjm721.overloaded.block.tile.infinity;

import com.cjm721.overloaded.storage.fluid.LongFluidStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperFluid.HYPER_FLUID_HANDLER;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class TileInfiniteTank extends TileEntity {

    private LongFluidStorage fluidStorage;

    public TileInfiniteTank() {
        fluidStorage = new LongFluidStorage();
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);

        return fluidStorage.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);

        fluidStorage.readFromNBT(compound);
    }

    @Nonnull
    public LongFluidStorage getStorage() {
        return fluidStorage;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability,@Nullable EnumFacing facing)
    {
        if(capability == FLUID_HANDLER_CAPABILITY || capability == HYPER_FLUID_HANDLER)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability,@Nullable EnumFacing facing)
    {
        if(capability == FLUID_HANDLER_CAPABILITY || capability == HYPER_FLUID_HANDLER)
        {
            markDirty();
            return (T) fluidStorage;
        }
        return super.getCapability(capability, facing);
    }
}
