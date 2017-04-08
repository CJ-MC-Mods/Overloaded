package com.cjm721.ibhstd.magic.energy;

import com.cjm721.ibhstd.api.hyper.ITypeInterface;

/**
 * Created by CJ on 4/8/2017.
 */
public class HyperEnergyHandler implements ITypeInterface<EnergyType, Long> {

    @Override
    public Long status() {
        return null;
    }

    /**
     * @param energyType
     * @param aLong
     * @param doAction
     * @return
     */
    @Override
    public Long give(EnergyType energyType, Long aLong, boolean doAction) {
        return null;
    }

    /**
     * @param energyType
     * @param aLong
     * @param doAction
     * @return
     */
    @Override
    public Long take(EnergyType energyType, Long aLong, boolean doAction) {
        return null;
    }
}
