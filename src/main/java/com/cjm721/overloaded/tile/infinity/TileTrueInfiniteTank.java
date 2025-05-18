package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.fluid.BigIntFluidStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TileTrueInfiniteTank extends AbstractTileHyperStorage<BigIntFluidStorage> implements IDataUpdate {

  @Nonnull private final BigIntFluidStorage fluidStorage;
//  @Nonnull private final LazyOptional<?> capability;

  public TileTrueInfiniteTank(BlockPos pos, BlockState state) {
    super(ModTiles.trueInfiniteTank, pos,state);
    fluidStorage = new BigIntFluidStorage(this);
//    capability = LazyOptional.of(() -> fluidStorage);
  }
//
//  @Override
//  @Nonnull
//  public CompoundNBT save(@Nonnull CompoundNBT compound) {
//    compound = super.save(compound);
//    compound.put("BigIntFluidStorage", fluidStorage.serializeNBT());
//    return compound;
//  }
//
//  @Override
//  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
//    super.load(state, compound);
//
//    if(compound.contains("BigIntFluidStorage")) {
//      fluidStorage.deserializeNBT((CompoundNBT) compound.get("BigIntFluidStorage"));
//    }
//  }
//
//  @Nonnull
//  @Override
//  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//    if (cap == HYPER_FLUID_HANDLER) {
//      return capability.cast();
//    }
//    return super.getCapability(cap, side);
//  }

  @Override
  public void dataUpdated() {
    setChanged();
  }

  @Nonnull
  @Override
  public BigIntFluidStorage getStorage() {
    return fluidStorage;
  }

//  @Override
//  public void onChunkUnloaded() {
//    capability.invalidate();
//  }
}
