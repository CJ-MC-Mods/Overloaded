package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.item.BigIntItemStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

public class TileTrueInfiniteBarrel extends AbstractTileHyperStorage implements IDataUpdate {

  private final BigIntItemStorage itemStorage;

  public TileTrueInfiniteBarrel() {
    super(ModTiles.trueInfiniteBarrel);
    itemStorage = new BigIntItemStorage(this);
  }

  @Override
  @Nonnull
  public CompoundNBT write(CompoundNBT compound) {
    return itemStorage.write(super.write(compound));
  }

  @Override
  public void read(CompoundNBT compound) {
    super.read(compound);

    itemStorage.read(compound);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == HYPER_ITEM_HANDLER) {
      return LazyOptional.of(() -> itemStorage).cast();
    }
    return this.getCapability(cap, side);
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }
}
