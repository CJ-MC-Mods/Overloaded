package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;
import com.cjm721.overloaded.storage.fluid.IHyperHandlerFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.capabilities.CapabilityHyperFluid.BLOCK_HYPER_FLUID_HANDLER;

public class TileHyperFluidSender
    extends AbstractTileHyperSender<LongFluidStack, IHyperHandlerFluid> {

  public TileHyperFluidSender(BlockPos pos, BlockState state) {
    super(ModTiles.hyperFluidSender.get(), BLOCK_HYPER_FLUID_HANDLER, pos,state);
  }

  @Override
  @Nonnull
  protected LongFluidStack generate(long amount) {
    return new LongFluidStack(null, amount);
  }

  @Override
  protected boolean isCorrectPartnerType(BlockEntity te) {
    return te instanceof TileHyperFluidReceiver;
  }
}
