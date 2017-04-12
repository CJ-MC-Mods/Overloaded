package com.cjm721.overloaded.common.block.tile.hyperTransfer;

import com.cjm721.overloaded.common.block.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.common.storage.LongEnergyStack;
import com.cjm721.overloaded.common.storage.energy.IHyperHandlerEnergy;

import static com.cjm721.overloaded.common.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

/**
 * Created by CJ on 4/12/2017.
 */
public class TileHyperEnergyReceiver extends AbstractTileHyperReceiver<LongEnergyStack,IHyperHandlerEnergy> {

    public TileHyperEnergyReceiver() {
        super(HYPER_ENERGY_HANDLER);
    }
}
