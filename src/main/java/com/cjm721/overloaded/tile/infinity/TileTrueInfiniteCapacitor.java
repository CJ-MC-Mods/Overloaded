package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.energy.BigIntEnergyStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TileTrueInfiniteCapacitor extends AbstractTileHyperStorage<BigIntEnergyStorage> implements IDataUpdate {

  @Nonnull private final BigIntEnergyStorage energyStorage;
//  @Nonnull private final LazyOptional<?> capability;

  public TileTrueInfiniteCapacitor(BlockPos pos, BlockState state) {
    super(ModTiles.trueInfiniteCapacitor, pos,state);
    energyStorage = new BigIntEnergyStorage(this);
//    capability = LazyOptional.of(() -> energyStorage);
  }
//
//  @Override
//  @Nonnull
//  public CompoundNBT save(@Nonnull CompoundNBT compound) {
//    compound = super.save(compound);
//    compound.put("BigIntEnergyStorage", energyStorage.serializeNBT());
//    return compound;
//  }
//
//  @Override
//  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
//    super.load(state, compound);
//    if(compound.contains("BigIntEnergyStorage")) {
//      energyStorage.deserializeNBT((CompoundNBT) compound.get("BigIntEnergyStorage"));
//    }
//  }
//
//  @Nonnull
//  @Override
//  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//    if (cap == HYPER_ENERGY_HANDLER) {
//      return capability.cast();
//    }
//    return super.getCapability(cap, side);
//  }

  @Override
  public void dataUpdated() {
    setChanged();
  }

  @Override
  @Nonnull
  public BigIntEnergyStorage getStorage() {
    return energyStorage;
  }

//  @Override
//  public void onChunkUnloaded() {
//    capability.invalidate();
//  }
}
