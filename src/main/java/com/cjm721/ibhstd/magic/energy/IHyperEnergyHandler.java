package com.cjm721.ibhstd.magic.energy;

import com.cjm721.ibhstd.api.hyper.ITypeInterface;

/**
 * Created by CJ on 4/8/2017.
 */
public interface IHyperEnergyHandler {

    long status();

    long give(Long aLong, boolean doAction);

    long take(EnergyType energyType, Long aLong, boolean doAction);
}
