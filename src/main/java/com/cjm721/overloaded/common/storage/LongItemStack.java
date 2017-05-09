package com.cjm721.overloaded.common.storage;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class LongItemStack implements IHyperType {

    public static final LongItemStack EMPTY_STACK = new LongItemStack(ItemStack.EMPTY, 0L);

    private long amount;
    private ItemStack itemStack;

    public LongItemStack(@Nonnull ItemStack itemStack, long amount) {
        this.itemStack = itemStack;
        this.amount = amount;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Nonnull
    public ItemStack getItemStack() {return itemStack; }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void removeAmount(long amount) {
        this.amount -= amount;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
