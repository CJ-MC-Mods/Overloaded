package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.item.LongItemStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TileAlmostInfiniteBarrel extends AbstractTileHyperStorage<LongItemStorage> implements IDataUpdate {

  @Nonnull private final LongItemStorage itemStorage;
//  @Nonnull private final LazyOptional<?> capability;

  public TileAlmostInfiniteBarrel(BlockPos pos, BlockState state) {
    super(ModTiles.almostInfiniteBarrel, pos,state);
    itemStorage = new LongItemStorage(this);
//    capability = LazyOptional.of(() -> itemStorage);
  }
//
//  @Override
//  @Nonnull
//  public CompoundTag save(@Nonnull CompoundTag compound) {
//    compound = super.save(compound);
//    compound.put("LongItemStorage", itemStorage.serializeNBT());
//    return compound;
//  }
//
//  @Override
//  public void load(@Nonnull BlockState state, @Nonnull CompoundTag compound) {
//    super.load(state, compound);
//
//    if(compound.contains("LongItemStorage")) {
//      itemStorage.deserializeNBT((CompoundTag) compound.get("LongItemStorage"));
//    }
//  }
//
//  @Nonnull
//  @Override
//  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
//        || cap == CapabilityHyperItem.HYPER_ITEM_HANDLER) {
//      return capability.cast();
//    }
//
//    return super.getCapability(cap, side);
//  }

  @Override
  @Nonnull
  public LongItemStorage getStorage() {
    return itemStorage;
  }

  @Override
  public void dataUpdated() {
    setChanged();
  }

//  @Override
//  public void onChunkUnloaded() {
//    capability.invalidate();
//  }
}
