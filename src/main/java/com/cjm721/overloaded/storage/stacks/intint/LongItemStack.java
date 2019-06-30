package com.cjm721.overloaded.storage.stacks.intint;

import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class LongItemStack implements IHyperType<Long> {

  public static final LongItemStack EMPTY_STACK = new LongItemStack(ItemStack.EMPTY, 0L);

  private long amount;
  @Nonnull private ItemStack itemStack;

  public LongItemStack(@Nonnull ItemStack itemStack, long amount) {
    this.itemStack = itemStack;
    this.amount = amount;
  }

  @Override
  public Long getAmount() {
    return amount;
  }

  @Nonnull
  public ItemStack getItemStack() {
    return itemStack;
  }

  public void setAmount(@Nonnegative long amount) {
    this.amount = amount;
  }

  public void removeAmount(@Nonnegative long amount) {
    this.amount -= amount;
  }

  public void setItemStack(@Nonnull ItemStack itemStack) {
    this.itemStack = itemStack;
  }
}
