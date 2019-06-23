package com.cjm721.overloaded.storage;

import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class LongFluidStack implements IHyperType {

  public static final LongFluidStack EMPTY_STACK = new LongFluidStack(null, 0L);

  public long amount;
  public FluidStack fluidStack;

  public LongFluidStack(@Nullable FluidStack fluidStack, long amount) {
    this.fluidStack = fluidStack;
    this.amount = amount;
  }

  @Override
  public long getAmount() {
    return amount;
  }
}
