package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.item.LongItemStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.CapabilityHyperItem;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileAlmostInfiniteBarrel extends AbstractTileHyperStorage implements IDataUpdate {

  @Nonnull private final LongItemStorage itemStorage;

  public TileAlmostInfiniteBarrel() {
    super(ModTiles.almostInfiniteBarrel);
    itemStorage = new LongItemStorage(this);
  }

  @Override
  @Nonnull
  public CompoundNBT write(@Nonnull CompoundNBT compound) {
    super.write(compound);
    return itemStorage.write(compound);
  }

  @Override
  public void read(CompoundNBT compound) {
    super.read(compound);

    itemStorage.read(compound);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
        || cap == CapabilityHyperItem.HYPER_ITEM_HANDLER) {
      return LazyOptional.of(() -> itemStorage).cast();
    }

    return super.getCapability(cap, side);
  }

  public LongItemStorage getStorage() {
    return itemStorage;
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }
}
