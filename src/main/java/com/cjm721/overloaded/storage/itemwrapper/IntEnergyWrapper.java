package com.cjm721.overloaded.storage.itemwrapper;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class IntEnergyWrapper implements ICapabilityProvider, IEnergyStorage, IDataUpdate {

  @Nonnull private final ItemStack stack;

  public IntEnergyWrapper(@Nonnull ItemStack stack) {
    this.stack = stack;

    CompoundNBT tagCompound = this.stack.getTag();
    if (tagCompound == null) {
      tagCompound = new CompoundNBT();
      this.stack.setTag(tagCompound);
    }
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> cap, @Nullable Direction direction) {
    return getCapability(cap);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
    if (cap == ENERGY) {
      return LazyOptional.of(() -> this).cast();
    }
    return LazyOptional.empty();
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    EnergyStorage storage = getStorage();
    try {
      return storage.receiveEnergy(maxReceive, simulate);
    } finally {
      this.setStorage(storage);
    }
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    EnergyStorage storage = getStorage();
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
  private EnergyStorage getStorage() {
    if (stack.getTag() == null) {
      Overloaded.logger.error(
          (CharSequence)
              "Something has changed private internal state in an invalid way. Resetting State.",
          new IllegalStateException(
              "private internal state changed. Stack's Tag Compound is null"));
      stack.setTag(new CompoundNBT());
    }
    int energy = stack.getTag().getInt("IntEnergyStorage");

    return new EnergyStorage(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, energy);
  }

  private void setStorage(@Nonnull EnergyStorage storage) {
    stack.getTag().putInt("IntEnergyStorage", storage.getEnergyStored());
  }

  @Override
  public void dataUpdated() {
    // TODO: Find a way to use this for writing data instead of triggering a save call on every use.
  }
}
