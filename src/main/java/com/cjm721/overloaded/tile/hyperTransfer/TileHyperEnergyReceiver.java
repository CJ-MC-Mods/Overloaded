package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.cjm721.overloaded.capabilities.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class TileHyperEnergyReceiver
    extends AbstractTileHyperReceiver<LongEnergyStack, IHyperHandlerEnergy> {

  public TileHyperEnergyReceiver(BlockPos pos, BlockState state) {
    super(ModTiles.hyperEnergyReceiver.get(), HYPER_ENERGY_HANDLER, pos,state);
  }
}
