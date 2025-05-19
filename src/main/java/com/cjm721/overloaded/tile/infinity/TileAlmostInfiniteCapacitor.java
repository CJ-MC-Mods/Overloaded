package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TileAlmostInfiniteCapacitor extends AbstractTileHyperStorage<LongEnergyStorage> implements IDataUpdate {

  @Nonnull private final LongEnergyStorage energyStorage;
//  @Nonnull private final LazyOptional<?> capability;

  public TileAlmostInfiniteCapacitor(BlockPos pos, BlockState state) {
    super(ModTiles.almostInfiniteCapacitor.get(), pos,state);
    energyStorage = new LongEnergyStorage(this);
//    capability = LazyOptional.of(() -> energyStorage);
  }

//  @Override
//  @Nonnull
//  public CompoundTag save(@Nonnull CompoundTag compound) {
//    super.save(compound);
//    CompoundTag energy = energyStorage.serializeNBT();
//    compound.put("LongEnergyStorage", energy);
//    return compound;
//  }
//
//  @Override
//  public void load(@Nonnull BlockState state, @Nonnull CompoundTag compound) {
//    super.load(state, compound);
//    if(compound.contains("LongEnergyStorage")) {
//      energyStorage.deserializeNBT((CompoundTag) compound.get("LongEnergyStorage"));
//    }
//  }

  @Override
  @Nonnull
  public LongEnergyStorage getStorage() {
    return energyStorage;
  }

//  @Override
//  @Nonnull
//  public <T> LazyOptional<T> getCapability(
//      @Nonnull Capability<T> cap, @Nullable Direction side) {
//    if (cap == ENERGY || cap == HYPER_ENERGY_HANDLER) {
//      return capability.cast();
//    }
//    return super.getCapability(cap, side);
//  }

  @Override
  public void dataUpdated() {
    setChanged();
  }

//  @Override
//  public void onChunkUnloaded() {
//    capability.invalidate();
//  }
}
