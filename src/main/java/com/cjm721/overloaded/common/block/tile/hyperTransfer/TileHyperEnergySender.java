package com.cjm721.overloaded.common.block.tile.hyperTransfer;

import com.cjm721.overloaded.common.block.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.common.storage.LongEnergyStack;
import com.cjm721.overloaded.common.storage.energy.IHyperHandlerEnergy;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.common.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

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
