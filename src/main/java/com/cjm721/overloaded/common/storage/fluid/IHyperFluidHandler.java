package com.cjm721.overloaded.common.storage.fluid;

import com.cjm721.overloaded.common.storage.INBTConvertable;
import com.cjm721.overloaded.common.storage.LongFluidStack;
import com.cjm721.overloaded.common.storage.LongItemStack;

/**
 * Created by CJ on 4/8/2017.
 */
public interface IHyperFluidHandler extends INBTConvertable {

    LongFluidStack status();

    LongFluidStack take(long aLong, boolean doAction);

    LongFluidStack give(LongFluidStack itemStack, boolean doAction);
}
