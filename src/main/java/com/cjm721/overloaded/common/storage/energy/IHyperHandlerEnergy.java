package com.cjm721.overloaded.common.storage.energy;

import com.cjm721.overloaded.common.storage.IHyperHandler;
import com.cjm721.overloaded.common.storage.INBTConvertable;
import com.cjm721.overloaded.common.storage.LongEnergyStack;

/**
 * Created by CJ on 4/8/2017.
 */
public interface IHyperEnergyHandler extends IHyperHandler<LongEnergyStack>, INBTConvertable {

    LongEnergyStack status();

    LongEnergyStack give(LongEnergyStack stack, boolean doAction);

    LongEnergyStack take(LongEnergyStack stack, boolean doAction);
}
