package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;
import com.cjm721.overloaded.storage.fluid.IHyperHandlerFluid;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.capabilities.CapabilityHyperFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileHyperFluidReceiver
    extends AbstractTileHyperReceiver<LongFluidStack, IHyperHandlerFluid> {

  public TileHyperFluidReceiver(BlockPos pos, BlockState state) {
    super(ModTiles.hyperFluidReceiver.get(), CapabilityHyperFluid.HYPER_FLUID_HANDLER, pos,state);
  }
}
