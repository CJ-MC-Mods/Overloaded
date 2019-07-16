package com.cjm721.overloaded.storage.energy;

import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;

public class ForgeEnergyDataUpdateWrapper extends EnergyStorage {

  @Nonnull
  private final IDataUpdate dataUpdate;

  public ForgeEnergyDataUpdateWrapper(int capacity, int maxReceive, int maxExtract, int energy, IDataUpdate dataUpdate) {
    super(capacity, maxReceive, maxExtract, energy);
    this.dataUpdate = dataUpdate;
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    int extracted = super.extractEnergy(maxExtract, simulate);

    if(!simulate && extracted != 0) {
      dataUpdate.dataUpdated();
    }
    return extracted;
  }


  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    int inserted = super.receiveEnergy(maxReceive, simulate);

    if(!simulate && inserted != 0) {
      dataUpdate.dataUpdated();
    }
    return inserted;
  }

  public ForgeEnergyDataUpdateWrapper setEnergy(int amount) {
    this.energy = MathHelper.clamp(amount, 0, this.capacity);

    return this;
  }
}
