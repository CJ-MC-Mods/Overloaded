package com.cjm721.overloaded.storage.item;

import com.cjm721.overloaded.storage.INBTConvertible;
import com.cjm721.overloaded.storage.stacks.bigint.BigIntItemStack;
import com.cjm721.overloaded.storage.stacks.intint.LongItemStack;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.math.BigInteger;

public class BigIntItemStorage implements IHyperHandlerItem, INBTConvertible {

  @Nonnull private final IDataUpdate dataUpdate;
  @Nonnull private BigIntItemStack storedItem;

  public BigIntItemStorage(@Nonnull IDataUpdate dataUpdate) {
    this.dataUpdate = dataUpdate;
    storedItem = new BigIntItemStack(ItemStack.EMPTY, BigInteger.ZERO);
  }

  @Override
  public void read(CompoundNBT compound) {
    ItemStack itemStack =
        compound.contains("Stack")
            ? ItemStack.read((CompoundNBT) compound.get("Stack"))
            : ItemStack.EMPTY;

    BigInteger amount =
        compound.contains("Count")
            ? new BigInteger(compound.getByteArray("Count"))
            : BigInteger.ZERO;

    this.storedItem = new BigIntItemStack(itemStack, amount);
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    if (storedItem.itemStack != ItemStack.EMPTY) {
      CompoundNBT tag = new CompoundNBT();
      storedItem.itemStack.write(tag);
      compound.put("Stack", tag);
      compound.putByteArray("Count", storedItem.amount.toByteArray());
    }
    return compound;
  }

  @Nonnull
  @Override
  public LongItemStack status() {
    return new LongItemStack(
        storedItem.itemStack,
        storedItem.amount.min(BigInteger.valueOf(Long.MAX_VALUE)).longValueExact());
  }

  @Nonnull
  @Override
  public LongItemStack take(@Nonnull LongItemStack stack, boolean doAction) {
    if (storedItem.itemStack.isEmpty()) return LongItemStack.EMPTY_STACK;

    long result = storedItem.amount.min(BigInteger.valueOf(stack.getAmount())).longValueExact();
    LongItemStack toReturn = new LongItemStack(storedItem.itemStack, result);
    if (doAction) {
      storedItem.amount = storedItem.amount.subtract(BigInteger.valueOf(result));

      if (storedItem.amount.equals(BigInteger.ZERO)) storedItem.itemStack = ItemStack.EMPTY;
      dataUpdate.dataUpdated();
    }

    return toReturn;
  }

  @Nonnull
  @Override
  public LongItemStack give(@Nonnull LongItemStack stack, boolean doAction) {
    if (storedItem.itemStack.isEmpty()) {
      if (doAction) {
        storedItem =
            new BigIntItemStack(stack.getItemStack(), BigInteger.valueOf(stack.getAmount()));
        dataUpdate.dataUpdated();
      }
      return LongItemStack.EMPTY_STACK;
    }

    if (ItemHandlerHelper.canItemStacksStack(storedItem.itemStack, stack.getItemStack())) {
      if (doAction) {
        storedItem.amount = storedItem.amount.add(BigInteger.valueOf(stack.getAmount()));
        dataUpdate.dataUpdated();
      }
      return LongItemStack.EMPTY_STACK;
    }

    return stack;
  }
}
