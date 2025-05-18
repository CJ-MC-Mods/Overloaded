package com.cjm721.overloaded.storage.itemwrapper;

import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Direction;
import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.capabilities.ICapabilityProvider;
import net.neoforged.common.util.LazyOptional;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.capabilities.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.neoforged.energy.CapabilityEnergy.ENERGY;

public class LongEnergyWrapper
    implements ICapabilityProvider, IEnergyStorage, IHyperHandlerEnergy, IDataUpdate {

  private final ItemStack stack;

  public LongEnergyWrapper(ItemStack stack) {
    this.stack = stack;

    CompoundTag tagCompound = this.stack.getTag();
    if (tagCompound == null) {
      tagCompound = new CompoundTag();
    }

    if (!tagCompound.contains("EnergyStorage")) {
      LongEnergyStorage storage = new LongEnergyStorage(this);

      CompoundTag storageTag = storage.serializeNBT();
      tagCompound.put("EnergyStorage", storageTag);
      this.stack.setTag(tagCompound);
    }
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
    if (capability == ENERGY || capability == HYPER_ENERGY_HANDLER) {
      return LazyOptional.of(() -> this).cast();
    }
    return LazyOptional.empty();
  }

  @Nonnull
  @Override
  public LongEnergyStack status() {
    return getStorage().status();
  }

  @Nonnull
  @Override
  public LongEnergyStack take(@Nonnull LongEnergyStack stack, boolean doAction) {
    LongEnergyStorage storage = getStorage();
    try {
      return storage.take(stack, doAction);
    } finally {
      this.setStorage(storage);
    }
  }

  @Nonnull
  @Override
  public LongEnergyStack give(@Nonnull LongEnergyStack stack, boolean doAction) {
    LongEnergyStorage storage = getStorage();
    try {
      return storage.give(stack, doAction);
    } finally {
      this.setStorage(storage);
    }
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    LongEnergyStorage storage = getStorage();
    try {
      return storage.receiveEnergy(maxReceive, simulate);
    } finally {
      this.setStorage(storage);
    }
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    LongEnergyStorage storage = getStorage();
    try {
      return storage.extractEnergy(maxExtract, simulate);
    } finally {
      this.setStorage(storage);
    }
  }

  @Override
  public int getEnergyStored() {
    return getStorage().getEnergyStored();
  }

  @Override
  public int getMaxEnergyStored() {
    return getStorage().getMaxEnergyStored();
  }

  @Override
  public boolean canExtract() {
    return getStorage().canExtract();
  }

  @Override
  public boolean canReceive() {
    return getStorage().canReceive();
  }

  @Nonnull
  private LongEnergyStorage getStorage() {
    CompoundTag compound = stack.getTag().getCompound("LongEnergyStorage");

    LongEnergyStorage storage = new LongEnergyStorage(this);
    storage.deserializeNBT(compound);

    return storage;
  }

  private void setStorage(@Nonnull LongEnergyStorage storage) {
    stack.getTag().put("LongEnergyStorage", storage.serializeNBT());
  }

  @Override
  public void dataUpdated() {
    // TODO: Find a way to use this for writing data instead of triggering a save call on every use.
  }
}
