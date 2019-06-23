package com.cjm721.overloaded.block.tile.hyperTransfer;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.block.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.storage.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import net.minecraft.tileentity.TileEntityType;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class TileHyperEnergyReceiver
    extends AbstractTileHyperReceiver<LongEnergyStack, IHyperHandlerEnergy> {

  public TileHyperEnergyReceiver() {
    super(
        TileEntityType.Builder.create(TileHyperEnergyReceiver::new, ModBlocks.hyperEnergyReceiver)
            .build(null),
        HYPER_ENERGY_HANDLER);
  }
}
