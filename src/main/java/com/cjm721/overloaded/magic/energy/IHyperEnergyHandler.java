package com.cjm721.ibhstd.magic.energy;

import com.cjm721.ibhstd.api.hyper.ITypeInterface;
import com.cjm721.ibhstd.common.storage.INBTConvertable;

/**
 * Created by CJ on 4/8/2017.
 */
public interface IHyperEnergyHandler extends INBTConvertable {

    long status();

    long give(long aLong, boolean doAction);

    long take(long aLong, boolean doAction);
}
