package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;
import com.cjm721.overloaded.storage.fluid.IHyperHandlerFluid;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.capabilities.CapabilityHyperFluid;

public class TileHyperFluidReceiver
    extends AbstractTileHyperReceiver<LongFluidStack, IHyperHandlerFluid> {

  public TileHyperFluidReceiver() {
    super(ModTiles.hyperFluidReceiver, CapabilityHyperFluid.HYPER_FLUID_HANDLER);
  }
}
