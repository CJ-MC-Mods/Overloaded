package com.cjm721.overloaded.common.storage.item;

import com.cjm721.overloaded.common.storage.INBTConvertable;
import com.cjm721.overloaded.common.storage.LongItemStack;

/**
 * Created by CJ on 4/8/2017.
 */
public interface IHyperItemHandler extends INBTConvertable {

    LongItemStack status();

    LongItemStack take(long aLong, boolean doAction);

    LongItemStack give(LongItemStack itemStack, boolean doAction);
}
