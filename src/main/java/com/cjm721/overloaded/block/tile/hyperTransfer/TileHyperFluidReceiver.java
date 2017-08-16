package com.cjm721.overloaded.block.tile.hyperTransfer;

import com.cjm721.overloaded.block.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.storage.LongFluidStack;
import com.cjm721.overloaded.storage.fluid.IHyperHandlerFluid;
import com.cjm721.overloaded.util.CapabilityHyperFluid;

public class TileHyperFluidReceiver extends AbstractTileHyperReceiver<LongFluidStack, IHyperHandlerFluid> {

    public TileHyperFluidReceiver() {
        super(CapabilityHyperFluid.HYPER_FLUID_HANDLER);
    }
}
