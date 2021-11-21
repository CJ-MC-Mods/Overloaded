package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.item.BigIntItemStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.capabilities.CapabilityHyperItem.HYPER_ITEM_HANDLER;

public class TileTrueInfiniteBarrel extends AbstractTileHyperStorage<BigIntItemStorage>
    implements IDataUpdate {

  @Nonnull private final BigIntItemStorage itemStorage;
  @Nonnull private final LazyOptional<?> capability;

  public TileTrueInfiniteBarrel() {
    super(ModTiles.trueInfiniteBarrel);
    itemStorage = new BigIntItemStorage(this);
    capability = LazyOptional.of(() -> itemStorage);
  }

  @Override
  @Nonnull
  public CompoundNBT save(@Nonnull CompoundNBT compound) {
    compound = super.save(compound);
    compound.put("BigIntItemStorage", itemStorage.serializeNBT());
    return compound;
  }

  @Override
  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
    super.load(state, compound);
    if (compound.contains("BigIntItemStorage")) {
      itemStorage.deserializeNBT((CompoundNBT) compound.get("BigIntItemStorage"));
    }
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == HYPER_ITEM_HANDLER) {
      return capability.cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void dataUpdated() {
    setChanged();
  }

  @Nonnull
  @Override
  public BigIntItemStorage getStorage() {
    return itemStorage;
  }

  @Override
  public void onChunkUnloaded() {
    capability.invalidate();
  }
}
