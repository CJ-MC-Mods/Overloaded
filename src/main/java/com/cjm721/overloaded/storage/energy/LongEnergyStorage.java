package com.cjm721.overloaded.storage.energy;

import com.cjm721.overloaded.storage.LongEnergyStack;
import com.cjm721.overloaded.util.IDataUpdate;
import com.cjm721.overloaded.util.NumberUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

public class LongEnergyStorage
    implements IEnergyStorage, IHyperHandlerEnergy, INBTSerializable<CompoundNBT> {

  @Nonnull private final IDataUpdate dataUpdate;
  @Nonnull private LongEnergyStack energy;

  public LongEnergyStorage(@Nonnull IDataUpdate dataUpdate) {
    energy = new LongEnergyStack(0);
    this.dataUpdate = dataUpdate;
  }

  @Override
  public CompoundNBT serializeNBT() {
    CompoundNBT compound = new CompoundNBT();
    compound.putLong("Count", energy.amount);
    return compound;
  }

  @Override
  public void deserializeNBT(@Nonnull CompoundNBT compound) {
    energy = new LongEnergyStack(compound.contains("Count") ? compound.getLong("Count") : 0L);
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    LongEnergyStack result = give(new LongEnergyStack(maxReceive), !simulate);

    return (int) (maxReceive - result.amount);
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    LongEnergyStack result = take(new LongEnergyStack(maxExtract), !simulate);

    return (int) result.amount;
  }

  @Override
  public int getEnergyStored() {
    return (int) (((double) energy.amount / (double) Long.MAX_VALUE) * Integer.MAX_VALUE);
  }

  @Override
  public int getMaxEnergyStored() {
    return Integer.MAX_VALUE;
  }

  @Override
  public boolean canExtract() {
    return true;
  }

  @Override
  public boolean canReceive() {
    return true;
  }

  @Override
  @Nonnull
  public LongEnergyStack status() {
    return energy;
  }

  @Override
  @Nonnull
  public LongEnergyStack give(@Nonnull LongEnergyStack stack, boolean doAction) {
    NumberUtil.AddReturn<Long> longAddReturn = NumberUtil.addToMax(energy.amount, stack.amount);

    if (doAction) {
      energy.amount = longAddReturn.result;
      dataUpdate.dataUpdated();
    }

    return new LongEnergyStack(longAddReturn.overflow);
  }

  @Override
  @Nonnull
  public LongEnergyStack take(@Nonnull LongEnergyStack stack, boolean doAction) {
    long newStoredAmount = Math.max(energy.amount - stack.amount, 0);
    LongEnergyStack result = new LongEnergyStack(Math.min(energy.amount, stack.amount));

    if (doAction) {
      energy.amount = newStoredAmount;
      dataUpdate.dataUpdated();
    }

    return result;
  }
}
