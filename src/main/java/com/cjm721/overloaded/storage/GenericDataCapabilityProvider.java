package com.cjm721.overloaded.storage;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GenericDataCapabilityProvider extends GenericDataStorage implements ICapabilityProvider {

    public GenericDataCapabilityProvider() { }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing enumFacing) {
        return capability == GENERIC_DATA_STORAGE;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing enumFacing) {
        if (capability == GENERIC_DATA_STORAGE) {
            return GENERIC_DATA_STORAGE.cast(this);
        }
        return null;
    }
}
