package com.cjm721.overloaded.storage.itemwrapper;

import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

public class IntEnergyWrapper implements IEnergyStorage, IDataUpdate {

  @Nonnull private final ItemStack stack;

  public IntEnergyWrapper(@Nonnull ItemStack stack) {
    this.stack = stack;
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
    // TODO Look into mutable storage stuff
    return new EnergyStorage(
        Integer.MAX_VALUE,
        Integer.MAX_VALUE,
        Integer.MAX_VALUE,
        this.stack.getOrDefault(ModItems.STORED_ENERGY, new EnergyStored(0)).energy());
  }

  private void setStorage(@Nonnull EnergyStorage storage) {
    stack.set(ModItems.STORED_ENERGY, new EnergyStored(storage.getEnergyStored()));
  }

  @Override
  public void dataUpdated() {
    // TODO: Find a way to use this for writing data instead of triggering a save call on every use.
  }
}
