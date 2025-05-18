package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.capabilities.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.neoforged.energy.CapabilityEnergy.ENERGY;

public class TileAlmostInfiniteCapacitor extends AbstractTileHyperStorage<LongEnergyStorage> implements IDataUpdate {

  @Nonnull private final LongEnergyStorage energyStorage;
  @Nonnull private final LazyOptional<?> capability;

  public TileAlmostInfiniteCapacitor(BlockPos pos, BlockState state) {
    super(ModTiles.almostInfiniteCapacitor, pos,state);
    energyStorage = new LongEnergyStorage(this);
    capability = LazyOptional.of(() -> energyStorage);
  }

  @Override
  @Nonnull
  public CompoundNBT save(@Nonnull CompoundNBT compound) {
    super.save(compound);
    CompoundNBT energy = energyStorage.serializeNBT();
    compound.put("LongEnergyStorage", energy);
    return compound;
  }

  @Override
  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
    super.load(state, compound);
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
    setChanged();
  }

  @Override
  public void onChunkUnloaded() {
    capability.invalidate();
  }
}
