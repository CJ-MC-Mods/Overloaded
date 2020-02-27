package com.cjm721.overloaded.storage.stacks.intint;

import com.cjm721.overloaded.storage.IHyperType;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class LongFluidStack implements IHyperType<Long> {

  public static final LongFluidStack EMPTY_STACK = new LongFluidStack(null, 0L);

  public long amount;
  public final FluidStack fluidStack;

  public LongFluidStack(@Nullable FluidStack fluidStack, long amount) {
    this.fluidStack = fluidStack;
    this.amount = amount;
  }

  @Override
  public Long getAmount() {
    return amount;
  }
}
