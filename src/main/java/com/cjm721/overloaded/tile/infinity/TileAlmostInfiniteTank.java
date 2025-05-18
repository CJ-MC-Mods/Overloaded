package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.fluid.LongFluidStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TileAlmostInfiniteTank extends AbstractTileHyperStorage<LongFluidStorage> implements IDataUpdate {

  @Nonnull private final LongFluidStorage fluidStorage;
//  @Nonnull private final LazyOptional<?> capability;

  public TileAlmostInfiniteTank(BlockPos pos, BlockState state) {
    super(ModTiles.almostInfiniteTank, pos,state);
    fluidStorage = new LongFluidStorage(this);
//    capability = LazyOptional.of(() -> fluidStorage);
  }
//
//  @Override
//  @Nonnull
//  public CompoundNBT save(@Nonnull CompoundNBT compound) {
//    compound = super.save(compound);
//    compound.put("LongFluidStorage", fluidStorage.serializeNBT());
//    return compound;
//  }
//
//  @Override
//  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
//    super.load(state, compound);
//
//    if(compound.contains("LongFluidStorage")) {
//      fluidStorage.deserializeNBT((CompoundNBT) compound.get("LongFluidStorage"));
//    }
//  }

  @Nonnull
  @Override
  public LongFluidStorage getStorage() {
    return fluidStorage;
  }
//
//  @Override
//  @Nonnull
//  public <T> LazyOptional<T> getCapability(
//      @Nonnull Capability<T> cap, @Nullable Direction side) {
//    if (cap == FLUID_HANDLER_CAPABILITY || cap == HYPER_FLUID_HANDLER) {
//      return capability.cast();
//    }
//    return super.getCapability(cap, side);
//  }

  @Override
  public void dataUpdated() {
    setChanged();
  }
//
//  @Override
//  public void onChunkUnloaded() {
//    capability.invalidate();
//  }
}
