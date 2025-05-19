package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.item.BigIntItemStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TileTrueInfiniteBarrel extends AbstractTileHyperStorage<BigIntItemStorage>
    implements IDataUpdate {

  @Nonnull private final BigIntItemStorage itemStorage;
//  @Nonnull private final LazyOptional<?> capability;

  public TileTrueInfiniteBarrel(BlockPos pos, BlockState state) {
    super(ModTiles.trueInfiniteBarrel.get(), pos,state);
    itemStorage = new BigIntItemStorage(this);
//    capability = LazyOptional.of(() -> itemStorage);
  }
//
//  @Override
//  @Nonnull
//  public CompoundTag save(@Nonnull CompoundTag compound) {
//    compound = super.save(compound);
//    compound.put("BigIntItemStorage", itemStorage.serializeNBT());
//    return compound;
//  }
//
//  @Override
//  public void load(@Nonnull BlockState state, @Nonnull CompoundTag compound) {
//    super.load(state, compound);
//    if (compound.contains("BigIntItemStorage")) {
//      itemStorage.deserializeNBT((CompoundTag) compound.get("BigIntItemStorage"));
//    }
//  }
//
//  @Nonnull
//  @Override
//  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//    if (cap == HYPER_ITEM_HANDLER) {
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
  public BigIntItemStorage getStorage() {
    return itemStorage;
  }

//  @Override
//  public void onChunkUnloaded() {
//    capability.invalidate();
//  }
}
