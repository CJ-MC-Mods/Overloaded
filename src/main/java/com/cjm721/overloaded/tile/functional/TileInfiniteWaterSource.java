package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

public class TileInfiniteWaterSource extends BlockEntity implements IFluidHandler {

  public TileInfiniteWaterSource(BlockPos pos, BlockState blockState) {

    super(ModTiles.infiniteWaterSource.get(), pos, blockState);
  }

  @Nonnull
  @Override
  public FluidStack drain(int maxDrain, FluidAction fluidAction) {
    return new FluidStack(Fluids.WATER, maxDrain);
  }

  @Override
  public int getTanks() {
    return 1;
  }

  @Nonnull
  @Override
  public FluidStack getFluidInTank(int tank) {
    if (tank != 0) {
      return FluidStack.EMPTY;
    }
    return new FluidStack(Fluids.WATER, Integer.MAX_VALUE);
  }

  @Override
  public int getTankCapacity(int tank) {
    return Integer.MAX_VALUE;
  }

  @Override
  public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
    return tank == 0 && stack.getFluid().isSame(Fluids.WATER);
  }

  @Override
  public int fill(FluidStack resource, FluidAction action) {
    return 0;
  }

  @Nonnull
  @Override
  public FluidStack drain(FluidStack resource, FluidAction action) {
    if (!isFluidValid(0, resource)) {
      return FluidStack.EMPTY;
    }
    return new FluidStack(Fluids.WATER, resource.getAmount());
  }
}
