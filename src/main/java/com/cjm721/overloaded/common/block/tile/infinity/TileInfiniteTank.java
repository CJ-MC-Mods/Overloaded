package com.cjm721.overloaded.common.block.tile.infinity;

import com.cjm721.overloaded.common.storage.fluid.LongFluidStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.common.util.CapabilityHyperFluid.HYPER_FLUID_HANDLER;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class TileInfiniteTank extends TileEntity {

    @Nonnull
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
    public boolean hasCapability(@Nonnull Capability<?> capability,@Nonnull EnumFacing facing)
    {
        if(capability == FLUID_HANDLER_CAPABILITY || capability == HYPER_FLUID_HANDLER)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability,@Nonnull EnumFacing facing)
    {
        if(capability == FLUID_HANDLER_CAPABILITY || capability == HYPER_FLUID_HANDLER)
        {
            return (T) fluidStorage;
        }
        return super.getCapability(capability, facing);
    }
}
