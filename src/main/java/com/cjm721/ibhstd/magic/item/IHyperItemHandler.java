package com.cjm721.ibhstd.magic.item;

import com.cjm721.ibhstd.api.hyper.ITypeInterface;
import net.minecraft.item.ItemStack;

/**
 * Created by CJ on 4/8/2017.
 */
public interface IHyperItemHandler {

    long status();

    long give(ItemStack itemStack, long aLong, boolean doAction);

    long take(ItemStack itemStack, long aLong, boolean doAction);
}
