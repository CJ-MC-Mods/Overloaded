package com.cjm721.overloaded.common.block.tile;

import com.cjm721.overloaded.common.block.tile.base.AbstractTileHyperSender;
import com.cjm721.overloaded.common.storage.LongFluidStack;
import com.cjm721.overloaded.common.storage.LongItemStack;
import com.cjm721.overloaded.common.storage.fluid.IHyperHandlerFluid;
import com.cjm721.overloaded.common.storage.item.IHyperHandlerItem;
import com.cjm721.overloaded.common.util.CapabilityHyperFluid;
import net.minecraftforge.common.capabilities.Capability;

import static com.cjm721.overloaded.common.util.CapabilityHyperFluid.HYPER_FLUID_HANDLER;

/**
 * Created by CJ on 4/10/2017.
 */
public class TileHyperFluidSender extends AbstractTileHyperSender<LongFluidStack,IHyperHandlerFluid> {

    public TileHyperFluidSender() {
        super(HYPER_FLUID_HANDLER);
    }

    @Override
    protected LongFluidStack generate(long amount) {
        return new LongFluidStack(null,amount);
    }
}
