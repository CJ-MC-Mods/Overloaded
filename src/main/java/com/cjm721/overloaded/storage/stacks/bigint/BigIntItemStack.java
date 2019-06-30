package com.cjm721.overloaded.storage.stacks.bigint;

import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.math.BigInteger;

public class BigIntItemStack implements IHyperType<BigInteger> {
  public BigInteger amount;
  public ItemStack itemStack;

  public BigIntItemStack(@Nonnull ItemStack itemStack, BigInteger amount) {
    this.itemStack = itemStack;
    this.amount = amount;
  }

  @Override
  public BigInteger getAmount() {
    return amount;
  }
}
