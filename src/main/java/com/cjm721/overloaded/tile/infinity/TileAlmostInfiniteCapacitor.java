package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.capabilities.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileAlmostInfiniteCapacitor extends AbstractTileHyperStorage<LongEnergyStorage> implements IDataUpdate {

  @Nonnull private final LongEnergyStorage energyStorage;
  @Nonnull private final LazyOptional<?> capability;

  public TileAlmostInfiniteCapacitor() {
    super(ModTiles.almostInfiniteCapacitor);
    energyStorage = new LongEnergyStorage(this);
    capability = LazyOptional.of(() -> energyStorage);
  }

  @Override
  @Nonnull
  public CompoundNBT write(@Nonnull CompoundNBT compound) {
    super.write(compound);
    CompoundNBT energy = energyStorage.serializeNBT();
    compound.put("LongEnergyStorage", energy);
    return compound;
  }

  @Override
  public void read(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
    super.read(state, compound);
    if(compound.contains("LongEnergyStorage")) {
      energyStorage.deserializeNBT((CompoundNBT) compound.get("LongEnergyStorage"));
    }
  }

  @Override
  @Nonnull
  public LongEnergyStorage getStorage() {
    return energyStorage;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == ENERGY || cap == HYPER_ENERGY_HANDLER) {
      return capability.cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }

  @Override
  public void onChunkUnloaded() {
    capability.invalidate();
  }
}
