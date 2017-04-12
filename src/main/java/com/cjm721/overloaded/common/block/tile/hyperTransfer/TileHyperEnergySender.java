package com.cjm721.overloaded.common.block.tile.hyperTransfer;

import com.cjm721.overloaded.common.block.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.common.storage.LongEnergyStack;
import com.cjm721.overloaded.common.storage.LongFluidStack;
import com.cjm721.overloaded.common.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.common.storage.fluid.IHyperHandlerFluid;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.common.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

/**
 * Created by CJ on 4/12/2017.
 */
public class TileHyperEnergySender extends AbstractTileHyperSender<LongEnergyStack,IHyperHandlerEnergy> {

    public TileHyperEnergySender() {
        super(HYPER_ENERGY_HANDLER);
    }

    @Nonnull
    @Override
    protected LongEnergyStack generate(long amount) {
        return new LongEnergyStack(amount);
    }

    @Override
    protected boolean isCorrectPartnerType(TileEntity te) {
        return te instanceof TileHyperEnergyReceiver;
    }
}
