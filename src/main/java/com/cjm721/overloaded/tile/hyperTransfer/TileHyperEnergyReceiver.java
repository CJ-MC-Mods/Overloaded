package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperReceiver;

import static com.cjm721.overloaded.capabilities.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class TileHyperEnergyReceiver
    extends AbstractTileHyperReceiver<LongEnergyStack, IHyperHandlerEnergy> {

  public TileHyperEnergyReceiver() {
    super(ModTiles.hyperEnergyReceiver, HYPER_ENERGY_HANDLER);
  }
}
