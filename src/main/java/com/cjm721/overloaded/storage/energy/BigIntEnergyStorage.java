package com.cjm721.overloaded.storage.energy;

import com.cjm721.overloaded.storage.stacks.bigint.BigIntEnergyStack;
import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.math.BigInteger;

public class BigIntEnergyStorage implements IHyperHandlerEnergy, INBTSerializable<CompoundNBT> {

  @Nonnull private final IDataUpdate dataUpdate;
  @Nonnull private BigIntEnergyStack energy;

  public BigIntEnergyStorage(@Nonnull IDataUpdate dataUpdate) {
    energy = new BigIntEnergyStack(BigInteger.ZERO);
    this.dataUpdate = dataUpdate;
  }

  @Nonnull
  public BigIntEnergyStack bigStatus() {
    return energy;
  }

  @Nonnull
  @Override
  public LongEnergyStack status() {
    return new LongEnergyStack(
        energy.amount.min(BigInteger.valueOf(Long.MAX_VALUE)).longValueExact());
  }

  @Nonnull
  @Override
  public LongEnergyStack take(@Nonnull LongEnergyStack stack, boolean doAction) {
    BigInteger takenAmount = energy.amount.min(BigInteger.valueOf(stack.amount));
    LongEnergyStack result = new LongEnergyStack(takenAmount.longValueExact());

    if (doAction) {
      energy.amount = energy.amount.subtract(takenAmount);
      dataUpdate.dataUpdated();
    }

    return result;
  }

  @Nonnull
  @Override
  public LongEnergyStack give(@Nonnull LongEnergyStack stack, boolean doAction) {
    if (doAction) {
      energy.amount = energy.amount.add(BigInteger.valueOf(stack.amount));
      dataUpdate.dataUpdated();
    }

    return new LongEnergyStack(0);
  }

  @Override
  public CompoundNBT serializeNBT() {
    CompoundNBT compound = new CompoundNBT();
    compound.putByteArray("Count", energy.amount.toByteArray());
    return compound;
  }

  @Override
  public void deserializeNBT(CompoundNBT nbt) {
    energy =
        new BigIntEnergyStack(
            nbt.contains("Count") ? new BigInteger(nbt.getByteArray("Count")) : BigInteger.ZERO);
  }
}
