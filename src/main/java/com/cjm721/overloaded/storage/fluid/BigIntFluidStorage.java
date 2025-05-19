package com.cjm721.overloaded.storage.fluid;

import com.cjm721.overloaded.storage.stacks.bigint.BigIntFluidStack;
import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static com.cjm721.overloaded.util.FluidUtil.fluidsAreEqual;

public class BigIntFluidStorage implements IHyperHandlerFluid, INBTSerializable<CompoundTag> {

  @Nonnull private final IDataUpdate dataUpdate;
  @Nonnull private BigIntFluidStack storedFluid;

  public BigIntFluidStorage(@Nonnull IDataUpdate dataUpdate) {
    this.dataUpdate = dataUpdate;
    storedFluid = new BigIntFluidStack(null, BigInteger.ZERO);
  }

  @Nonnull
  public BigIntFluidStack bigStatus() {
    return storedFluid;
  }

  @Override
  public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compound) {

    FluidStack fluidStack =
        compound.contains("Fluid")
            ? FluidStack.parse(provider, compound.get("Fluid")).orElse(null)
            : null;
    BigInteger amount =
        compound.contains("Count")
            ? new BigInteger(compound.getByteArray("Count"))
            : BigInteger.ZERO;

    this.storedFluid = new BigIntFluidStack(fluidStack, amount);
  }

  @Override
  public CompoundTag serializeNBT(HolderLookup.Provider provider) {
    CompoundTag compound = new CompoundTag();
    if (storedFluid.fluidStack != null) {
      Tag tag = storedFluid.fluidStack.save(provider);
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
