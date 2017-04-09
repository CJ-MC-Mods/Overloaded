package com.cjm721.ibhstd.common.storage;

import net.minecraft.item.ItemStack;

/**
 * Created by CJ on 4/8/2017.
 */
public class LongItemStack {

    public static final LongItemStack EMPTY_STACK = new LongItemStack(null, 0L);

    public long amount;
    public ItemStack itemStack;

    public LongItemStack(ItemStack itemStack, long amount) {
        this.itemStack = itemStack;
        this.amount = amount;
    }

}
