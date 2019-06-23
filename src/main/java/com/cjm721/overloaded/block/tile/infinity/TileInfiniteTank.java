package com.cjm721.overloaded.block.tile.infinity;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.storage.fluid.LongFluidStorage;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperFluid.HYPER_FLUID_HANDLER;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class TileInfiniteTank extends TileEntity implements IDataUpdate {

  private final LongFluidStorage fluidStorage;

  public TileInfiniteTank() {
    super(TileEntityType.Builder.create(TileInfiniteTank::new, ModBlocks.infiniteTank).build(null));
    fluidStorage = new LongFluidStorage(this);
  }

  @Override
  @Nonnull
  public CompoundNBT write(@Nonnull CompoundNBT compound) {
    super.write(compound);

    return fluidStorage.write(compound);
  }

  @Override
  public void read(@Nonnull CompoundNBT compound) {
    super.read(compound);

    fluidStorage.read(compound);
  }

  @Nonnull
  public LongFluidStorage getStorage() {
    return fluidStorage;
  }

  @Override
  @Nullable
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
    if (capability == FLUID_HANDLER_CAPABILITY || capability == HYPER_FLUID_HANDLER) {
      return LazyOptional.of(() -> fluidStorage).cast();
    }
    return super.getCapability(capability, facing);
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }
}
