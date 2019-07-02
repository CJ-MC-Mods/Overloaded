package com.cjm721.overloaded.storage;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE;

public class GenericDataCapabilityProvider extends GenericDataStorage
    implements ICapabilityProvider {

  public GenericDataCapabilityProvider() {}

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction Direction) {
    // Defensive Hack because some reason addTooltip data is being called before mod even inited.
    if (capability == null) {
      return LazyOptional.empty();
    }
    if (capability == GENERIC_DATA_STORAGE) {
      return LazyOptional.of(() -> this).cast();
    }
    return LazyOptional.empty();
  }
}
