package com.cjm721.overloaded.storage.fluid;

import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;
import com.cjm721.overloaded.util.IDataUpdate;
import com.cjm721.overloaded.util.NumberUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.util.FluidUtil.fluidsAreEqual;
import static com.cjm721.overloaded.util.NumberUtil.addToMax;

public class LongFluidStorage
    implements IFluidHandler, IHyperHandlerFluid, INBTSerializable<CompoundTag> {

  @Nonnull private final IDataUpdate dataUpdate;
  @Nonnull private LongFluidStack storedFluid;

  public LongFluidStorage(@Nonnull IDataUpdate dataUpdate) {
    this.dataUpdate = dataUpdate;
    storedFluid = new LongFluidStack(null, 0);
  }

  @Override
  public int fill(FluidStack resource, FluidAction fluidAction) {
    if (resource.getFluid() == Fluids.EMPTY) {
      return resource.getAmount();
    }

    LongFluidStack fluidStack = give(new LongFluidStack(resource.copy(), resource.getAmount()), fluidAction.execute());

    return (int) (resource.getAmount() - fluidStack.amount);
  }

  @Override
  @Nonnull
  public FluidStack drain(@Nonnull FluidStack resource, FluidAction fluidAction) {
    LongFluidStack result = take(new LongFluidStack(resource, resource.getAmount()), fluidAction.execute());

    if (result.amount == 0L) {
      return FluidStack.EMPTY;
    }

    FluidStack toReturn = resource.copy();
    toReturn.setAmount((int) result.amount);

    return toReturn;
  }

  @Override
  @Nonnull
  public FluidStack drain(int maxDrain, FluidAction fluidAction) {
    LongFluidStack result = take(new LongFluidStack(null, maxDrain), fluidAction.execute());

    if (result.amount == 0L) {
      return FluidStack.EMPTY;
    }

    FluidStack toReturn = result.fluidStack.copy();
    toReturn.setAmount((int) result.amount);

    return toReturn;
  }

  @Override
  public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compound) {
    FluidStack fluidStack =
        compound.contains("Fluid")
            ? FluidStack.parse(provider, compound.get("Fluid")).orElse(null)
            : null;
    long amount = compound.contains("Count") ? compound.getLong("Count") : 0L;

    this.storedFluid = new LongFluidStack(fluidStack, amount);
  }

  @Override
  @Nonnull
  public CompoundTag serializeNBT(HolderLookup.Provider provider) {
    CompoundTag compound = new CompoundTag();
    if (storedFluid.fluidStack != null) {
      Tag tag = storedFluid.fluidStack.save(provider);
      compound.put("Fluid", tag);
      compound.putLong("Count", storedFluid.amount);
    }

    return compound;
  }

  public LongFluidStack getFluidStack() {
    return storedFluid;
  }

  @Override
  @Nonnull
  public LongFluidStack status() {
    return storedFluid;
  }

  @Override
  @Nonnull
  public LongFluidStack take(@Nonnull LongFluidStack stack, boolean doAction) {
    if (storedFluid.fluidStack == null || storedFluid.fluidStack.isEmpty() || storedFluid.fluidStack.getFluid() == Fluids.EMPTY) return LongFluidStack.EMPTY_STACK;

    if (stack.fluidStack != null && !fluidsAreEqual(storedFluid.fluidStack, stack.fluidStack)) {
      return LongFluidStack.EMPTY_STACK;
    }

    LongFluidStack toReturn =
        new LongFluidStack(storedFluid.fluidStack, Math.min(storedFluid.amount, stack.amount));

    if (doAction) {
      storedFluid.amount -= toReturn.amount;
      if (storedFluid.amount == 0) storedFluid = LongFluidStack.EMPTY_STACK;
      dataUpdate.dataUpdated();
    }

    return toReturn;
  }

  @Override
  @Nonnull
  public LongFluidStack give(@Nonnull LongFluidStack fluidStack, boolean doAction) {
    if (storedFluid.fluidStack == null) {
      if (doAction) {
        storedFluid = fluidStack;
        dataUpdate.dataUpdated();
      }
      return LongFluidStack.EMPTY_STACK;
    }

    if (fluidsAreEqual(storedFluid.fluidStack, fluidStack.fluidStack)) {
      NumberUtil.AddReturn<Long> value = addToMax(storedFluid.amount, fluidStack.amount);
      if (doAction) {
        storedFluid.amount = value.result;
        storedFluid.fluidStack.setAmount((int) Math.min(storedFluid.amount, Integer.MAX_VALUE));
        dataUpdate.dataUpdated();
      }

      return new LongFluidStack(storedFluid.fluidStack, value.overflow);
    }

    return fluidStack;
  }

  @Override
  public int getTanks() {
    return 1;
  }

  @Nonnull
  @Override
  public FluidStack getFluidInTank(int i) {
    return storedFluid.fluidStack == null ? FluidStack.EMPTY : storedFluid.fluidStack;
  }

  @Override
  public int getTankCapacity(int i) {
    return Integer.MAX_VALUE;
  }

  @Override
  public boolean isFluidValid(int i, @Nonnull FluidStack fluidStack) {
    return (storedFluid.fluidStack == null ? FluidStack.EMPTY : storedFluid.fluidStack).isFluidEqual(fluidStack);
  }
}
