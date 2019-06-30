package com.cjm721.overloaded.storage.stacks.bigint;

import com.cjm721.overloaded.storage.IHyperType;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.math.BigInteger;

public class BigIntFluidStack implements IHyperType<BigInteger> {

  public BigInteger amount;
  public FluidStack fluidStack;

  public BigIntFluidStack(@Nullable FluidStack fluidStack, BigInteger amount) {
    this.fluidStack = fluidStack;
    this.amount = amount;
  }

  @Override
  public BigInteger getAmount() {
    return amount;
  }
}
