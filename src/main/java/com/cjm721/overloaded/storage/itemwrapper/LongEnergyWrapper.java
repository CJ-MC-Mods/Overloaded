package com.cjm721.overloaded.storage.itemwrapper;

import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class LongEnergyWrapper
    implements ICapabilityProvider, IEnergyStorage, IHyperHandlerEnergy, IDataUpdate {

  private final ItemStack stack;

  public LongEnergyWrapper(ItemStack stack) {
    this.stack = stack;

    CompoundNBT tagCompound = this.stack.getTag();
    if (tagCompound == null) {
      tagCompound = new CompoundNBT();
    }

    if (!tagCompound.contains("EnergyStorage")) {
      LongEnergyStorage storage = new LongEnergyStorage(this);

      CompoundNBT storageTag = storage.serializeNBT();
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
    CompoundNBT compound = stack.getTag().getCompound("EnergyStorage");

    LongEnergyStorage storage = new LongEnergyStorage(this);
    storage.deserializeNBT(compound);

    return storage;
  }

  private void setStorage(@Nonnull LongEnergyStorage storage) {
    CompoundNBT compound = storage.serializeNBT();
    stack.getTag().put("EnergyStorage", compound);
  }

  @Override
  public void dataUpdated() {
    // TODO: Find a way to use this for writing data instead of triggering a save call on every use.
  }
}
