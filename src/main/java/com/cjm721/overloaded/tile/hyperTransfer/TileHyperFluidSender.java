package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;
import com.cjm721.overloaded.storage.fluid.IHyperHandlerFluid;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.capabilities.CapabilityHyperFluid.HYPER_FLUID_HANDLER;

public class TileHyperFluidSender
    extends AbstractTileHyperSender<LongFluidStack, IHyperHandlerFluid> {

  public TileHyperFluidSender() {
    super(ModTiles.hyperFluidSender, HYPER_FLUID_HANDLER);
  }

  @Override
  @Nonnull
  protected LongFluidStack generate(long amount) {
    return new LongFluidStack(null, amount);
  }

  @Override
  protected boolean isCorrectPartnerType(TileEntity te) {
    return te instanceof TileHyperFluidReceiver;
  }
}
