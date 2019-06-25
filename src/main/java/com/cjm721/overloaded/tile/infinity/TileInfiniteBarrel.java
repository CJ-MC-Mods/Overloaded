package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.item.LongItemStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.CapabilityHyperItem;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class TileInfiniteBarrel extends TileEntity implements IDataUpdate {

  @Nonnull private final LongItemStorage itemStorage;

  public TileInfiniteBarrel() {
    super(ModTiles.infiniteBarrel);
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
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(
          cap, LazyOptional.of(() -> itemStorage));
    }
    if (cap == CapabilityHyperItem.HYPER_ITEM_HANDLER) {
      return CapabilityHyperItem.HYPER_ITEM_HANDLER.orEmpty(
          cap, LazyOptional.of(() -> itemStorage));
    }

    return super.getCapability(cap);
  }

  public LongItemStorage getStorage() {
    return itemStorage;
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }
}
