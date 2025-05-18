package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.fluid.Fluid;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.util.LazyOptional;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.neoforged.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;

public class TileInfiniteWaterSource extends BlockEntity implements IFluidHandler {

  @ObjectHolder("minecraft:water")
  private static Fluid WATER;

  public TileInfiniteWaterSource(BlockPos pos, BlockState blockState) {
    
    super(ModTiles.infiniteWaterSource, pos, blockState);
  }

  @Nonnull
  @Override
  public FluidStack drain(int maxDrain, FluidAction fluidAction) {
    return new FluidStack(WATER, maxDrain);
  }

  get


  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
    if (capability == FLUID_HANDLER_CAPABILITY) {
      return LazyOptional.of(() -> this).cast();
    }
    return super.getCapability(capability, facing);
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
    return new FluidStack(WATER, Integer.MAX_VALUE);
  }

  @Override
  public int getTankCapacity(int tank) {
    return Integer.MAX_VALUE;
  }

  @Override
  public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
    return tank == 0 && stack.getFluid().isSame(WATER);
  }

  @Override
  public int fill(FluidStack resource, FluidAction action) {
    return 0;
  }

  @Nonnull
  @Override
  public FluidStack drain(FluidStack resource, FluidAction action) {
    if(!isFluidValid(0, resource)) {
      return FluidStack.EMPTY;
    }
    return new FluidStack(WATER, resource.getAmount());
  }
}
