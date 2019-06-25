package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.storage.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperSender;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class TileHyperEnergySender
    extends AbstractTileHyperSender<LongEnergyStack, IHyperHandlerEnergy> {

  public TileHyperEnergySender() {
    super(ModTiles.hyperEnergySender, HYPER_ENERGY_HANDLER);
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
