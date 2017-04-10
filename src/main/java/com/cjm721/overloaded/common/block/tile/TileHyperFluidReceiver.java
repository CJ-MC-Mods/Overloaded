package com.cjm721.overloaded.common.block.tile;

import com.cjm721.overloaded.common.block.tile.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.common.storage.LongFluidStack;
import com.cjm721.overloaded.common.storage.fluid.IHyperHandlerFluid;
import com.cjm721.overloaded.common.util.CapabilityHyperFluid;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by CJ on 4/10/2017.
 */
public class TileHyperFluidReceiver extends AbstractTileHyperReceiver<LongFluidStack,IHyperHandlerFluid> {

    public TileHyperFluidReceiver() {
        super(CapabilityHyperFluid.HYPER_FLUID_HANDLER);
    }
}
