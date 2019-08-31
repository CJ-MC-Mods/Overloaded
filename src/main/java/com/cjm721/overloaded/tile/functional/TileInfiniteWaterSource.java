package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.fluid.Fluid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class TileInfiniteWaterSource extends TileEntity implements IFluidHandler {

  @ObjectHolder("minecraft:water")
  private static Fluid WATER;

  public TileInfiniteWaterSource() {
    super(ModTiles.infiniteWaterSource);
  }

  @Nonnull
  @Override
  public FluidStack drain(int maxDrain, FluidAction fluidAction) {
    return new FluidStack(WATER, maxDrain);
  }

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
    return tank == 0 && stack.getFluid().isEquivalentTo(WATER);
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
