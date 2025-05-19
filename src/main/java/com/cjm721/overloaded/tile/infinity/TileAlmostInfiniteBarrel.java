package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.item.LongItemStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TileAlmostInfiniteBarrel extends AbstractTileHyperStorage<LongItemStorage> implements IDataUpdate {

  @Nonnull private final LongItemStorage itemStorage;

  public TileAlmostInfiniteBarrel(BlockPos pos, BlockState state) {
    super(ModTiles.almostInfiniteBarrel.get(), pos,state);
    itemStorage = new LongItemStorage(this);
  }

  @Override
  protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    super.saveAdditional(compound, registries);
    compound.put("LongItemStorage", itemStorage.serializeNBT(registries));
  }

  @Override
  protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    super.loadAdditional(compound, registries);
    if (compound.contains("LongItemStorage")) {
      itemStorage.deserializeNBT(registries,(CompoundTag) compound.get("LongItemStorage"));
    }
  }

  @Override
  @Nonnull
  public LongItemStorage getStorage() {
    return itemStorage;
  }

  @Override
  public void dataUpdated() {
    setChanged();
  }
}
