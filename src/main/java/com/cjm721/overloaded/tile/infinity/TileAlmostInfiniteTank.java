package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.fluid.LongFluidStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.capabilities.CapabilityHyperFluid.HYPER_FLUID_HANDLER;
import static net.neoforged.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class TileAlmostInfiniteTank extends AbstractTileHyperStorage<LongFluidStorage> implements IDataUpdate {

  @Nonnull private final LongFluidStorage fluidStorage;
  @Nonnull private final LazyOptional<?> capability;

  public TileAlmostInfiniteTank(BlockPos pos, BlockState state) {
    super(ModTiles.almostInfiniteTank pos,state);
    fluidStorage = new LongFluidStorage(this);
    capability = LazyOptional.of(() -> fluidStorage);
  }

  @Override
  @Nonnull
  public CompoundNBT save(@Nonnull CompoundNBT compound) {
    compound = super.save(compound);
    compound.put("LongFluidStorage", fluidStorage.serializeNBT());
    return compound;
  }

  @Override
  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
    super.load(state, compound);

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
    setChanged();
  }

  @Override
  public void onChunkUnloaded() {
    capability.invalidate();
  }
}
