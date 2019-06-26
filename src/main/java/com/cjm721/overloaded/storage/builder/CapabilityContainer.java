package com.cjm721.overloaded.storage.builder;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CapabilityContainer implements ICapabilityProvider {

  @Nonnull private final List<ICapabilityProvider> capabilityProviders;

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

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
    return capabilityProviders.stream()
        .map(cap -> cap.getCapability(capability, facing))
        .filter(LazyOptional::isPresent)
        .findAny()
        .orElse(LazyOptional.empty());
  }
}
