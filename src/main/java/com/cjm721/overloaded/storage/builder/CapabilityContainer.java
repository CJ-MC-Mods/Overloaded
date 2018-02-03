package com.cjm721.overloaded.storage.builder;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CapabilityContainer implements ICapabilityProvider {

    @Nonnull
    private List<ICapabilityProvider> capabilityProviders;

    public CapabilityContainer() {
        capabilityProviders = new ArrayList<>();
    }

    public CapabilityContainer addCapability(ICapabilityProvider capabilityProvider) {
        capabilityProviders.add(capabilityProvider);
        return this;
    }

    public CapabilityContainer addCapability(Collection<ICapabilityProvider> capabilityProvider) {
        capabilityProviders.addAll(capabilityProvider);
        return this;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capabilityProviders.stream().anyMatch(cap -> cap.hasCapability(capability, facing));
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capabilityProviders.stream().map(cap -> cap.getCapability(capability, facing)).filter(o -> o != null).findAny().orElse(null);
    }
}
