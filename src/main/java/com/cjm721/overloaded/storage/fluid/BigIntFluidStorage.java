package com.cjm721.overloaded.storage.fluid;

import com.cjm721.overloaded.storage.stacks.bigint.BigIntFluidStack;
import com.cjm721.overloaded.storage.INBTConvertible;
import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static com.cjm721.overloaded.util.FluidUtil.fluidsAreEqual;

public class BigIntFluidStorage implements IHyperHandlerFluid, INBTConvertible {

  @Nonnull private final IDataUpdate dataUpdate;
  @Nonnull private BigIntFluidStack storedFluid;

  public BigIntFluidStorage(@Nonnull IDataUpdate dataUpdate) {
    this.dataUpdate = dataUpdate;
    storedFluid = new BigIntFluidStack(null, BigInteger.ZERO);
  }

  @Override
  public void read(CompoundNBT compound) {
    FluidStack fluidStack =
        compound.contains("Fluid")
            ? FluidStack.loadFluidStackFromNBT((CompoundNBT) compound.get("Fluid"))
            : null;
    BigInteger amount =
        compound.contains("Count")
            ? new BigInteger(compound.getByteArray("Count"))
            : BigInteger.ZERO;

    this.storedFluid = new BigIntFluidStack(fluidStack, amount);
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    if (storedFluid.fluidStack != null) {
      CompoundNBT tag = new CompoundNBT();
      storedFluid.fluidStack.writeToNBT(tag);
      compound.put("Fluid", tag);
      compound.putByteArray("Count", storedFluid.amount.toByteArray());
    }

    return compound;
  }

  @Override
  @Nonnull
  public LongFluidStack status() {
    return new LongFluidStack(
        storedFluid.fluidStack,
        storedFluid.amount.min(BigInteger.valueOf(Long.MAX_VALUE)).longValueExact());
  }

  @Override
  @Nonnull
  public LongFluidStack take(@Nonnull LongFluidStack stack, boolean doAction) {
    if (storedFluid.fluidStack == null) return LongFluidStack.EMPTY_STACK;

    if (stack.fluidStack != null && !fluidsAreEqual(storedFluid.fluidStack, stack.fluidStack)) {
      return LongFluidStack.EMPTY_STACK;
    }

    BigInteger takingAmount = BigInteger.valueOf(stack.amount);

    LongFluidStack toReturn =
        new LongFluidStack(
            storedFluid.fluidStack, storedFluid.amount.min(takingAmount).longValueExact());

    if (doAction) {
      storedFluid.amount = storedFluid.amount.subtract(takingAmount);
      if (storedFluid.amount.equals(BigInteger.ZERO)) storedFluid.fluidStack = null;
      dataUpdate.dataUpdated();
    }

    return toReturn;
  }

  @Override
  @Nonnull
  public LongFluidStack give(@Nonnull LongFluidStack fluidStack, boolean doAction) {
    if (storedFluid.fluidStack == null) {
      if (doAction) {
        storedFluid.fluidStack = fluidStack.fluidStack;
        dataUpdate.dataUpdated();
      }
      return LongFluidStack.EMPTY_STACK;
    }

    if (fluidsAreEqual(storedFluid.fluidStack, fluidStack.fluidStack)) {
      if (doAction) {
        storedFluid.amount = storedFluid.amount.add(BigInteger.valueOf(fluidStack.amount));
        dataUpdate.dataUpdated();
      }

      return new LongFluidStack(storedFluid.fluidStack, 0);
    }

    return fluidStack;
  }
}
