package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.energy.BigIntEnergyStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class TileTrueInfiniteCapacitor extends AbstractTileHyperStorage<BigIntEnergyStorage> implements IDataUpdate {

  private final BigIntEnergyStorage energyStorage;

  public TileTrueInfiniteCapacitor() {
    super(ModTiles.trueInfiniteCapacitor);
    energyStorage = new BigIntEnergyStorage(this);
  }

  @Override
  @Nonnull
  public CompoundNBT write(CompoundNBT compound) {
    compound = super.write(compound);
    compound.put("BigIntEnergyStorage", energyStorage.serializeNBT());
    return compound;
  }

  @Override
  public void read(CompoundNBT compound) {
    super.read(compound);
    if(compound.contains("BigIntEnergyStorage")) {
      energyStorage.deserializeNBT((CompoundNBT) compound.get("BigIntEnergyStorage"));
    }
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == HYPER_ENERGY_HANDLER) {
      return LazyOptional.of(() -> energyStorage).cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }

  @Override
  @Nonnull
  public BigIntEnergyStorage getStorage() {
    return energyStorage;
  }
}
