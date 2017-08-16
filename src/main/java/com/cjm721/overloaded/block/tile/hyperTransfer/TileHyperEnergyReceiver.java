package com.cjm721.overloaded.block.tile.hyperTransfer;

import com.cjm721.overloaded.block.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.storage.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class TileHyperEnergyReceiver extends AbstractTileHyperReceiver<LongEnergyStack, IHyperHandlerEnergy> {

    public TileHyperEnergyReceiver() {
        super(HYPER_ENERGY_HANDLER);
    }
}
