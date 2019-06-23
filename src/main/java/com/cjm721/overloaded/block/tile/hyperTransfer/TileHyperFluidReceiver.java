package com.cjm721.overloaded.block.tile.hyperTransfer;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.block.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.storage.LongFluidStack;
import com.cjm721.overloaded.storage.fluid.IHyperHandlerFluid;
import com.cjm721.overloaded.util.CapabilityHyperFluid;
import net.minecraft.tileentity.TileEntityType;

public class TileHyperFluidReceiver
    extends AbstractTileHyperReceiver<LongFluidStack, IHyperHandlerFluid> {

  public TileHyperFluidReceiver() {
    super(
        TileEntityType.Builder.create(TileHyperFluidReceiver::new, ModBlocks.hyperFluidReceiver)
            .build(null),
        CapabilityHyperFluid.HYPER_FLUID_HANDLER);
  }
}
