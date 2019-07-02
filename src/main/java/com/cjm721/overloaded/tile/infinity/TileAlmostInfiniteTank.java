package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.fluid.LongFluidStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.capabilities.CapabilityHyperFluid.HYPER_FLUID_HANDLER;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class TileAlmostInfiniteTank extends AbstractTileHyperStorage<LongFluidStorage> implements IDataUpdate {

  @Nonnull private final LongFluidStorage fluidStorage;
  @Nonnull private final LazyOptional<?> capability;

  public TileAlmostInfiniteTank() {
    super(ModTiles.almostInfiniteTank);
    fluidStorage = new LongFluidStorage(this);
    capability = LazyOptional.of(() -> fluidStorage);
  }

  @Override
  @Nonnull
  public CompoundNBT write(@Nonnull CompoundNBT compound) {
    compound = super.write(compound);
    compound.put("LongFluidStorage", fluidStorage.serializeNBT());
    return compound;
  }

  @Override
  public void read(@Nonnull CompoundNBT compound) {
    super.read(compound);

    if(compound.contains("LongFluidStorage")) {
      fluidStorage.deserializeNBT((CompoundNBT) compound.get("LongFluidStorage"));
    }
  }

  @Nonnull
  @Override
  public LongFluidStorage getStorage() {
    return fluidStorage;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == FLUID_HANDLER_CAPABILITY || cap == HYPER_FLUID_HANDLER) {
      return capability.cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }

  @Override
  public void onChunkUnloaded() {
    capability.invalidate();
  }
}
