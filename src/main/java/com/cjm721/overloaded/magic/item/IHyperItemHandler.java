package com.cjm721.ibhstd.magic.item;

import com.cjm721.ibhstd.common.storage.INBTConvertable;
import com.cjm721.ibhstd.common.storage.LongItemStack;
import net.minecraft.item.ItemStack;

/**
 * Created by CJ on 4/8/2017.
 */
public interface IHyperItemHandler extends INBTConvertable {

    LongItemStack status();

    LongItemStack give(LongItemStack itemStack, boolean doAction);

    LongItemStack take(long aLong, boolean doAction);
}
