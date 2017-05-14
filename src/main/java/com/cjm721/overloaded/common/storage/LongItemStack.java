package com.cjm721.overloaded.common.storage;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LongItemStack implements IHyperType {

    public static final LongItemStack EMPTY_STACK = new LongItemStack(null, 0L);

    private long amount;
    private ItemStack itemStack;

    public LongItemStack(@Nullable ItemStack itemStack, long amount) {
        this.itemStack = itemStack;
        this.amount = amount;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Nullable
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
