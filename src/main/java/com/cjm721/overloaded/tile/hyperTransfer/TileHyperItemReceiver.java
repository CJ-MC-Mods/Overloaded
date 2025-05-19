package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.storage.stacks.intint.LongItemStack;
import com.cjm721.overloaded.storage.item.IHyperHandlerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.cjm721.overloaded.capabilities.CapabilityHyperItem.BLOCK_HYPER_ITEM_HANDLER;

public class TileHyperItemReceiver
    extends AbstractTileHyperReceiver<LongItemStack, IHyperHandlerItem> {

  public TileHyperItemReceiver(BlockPos pos, BlockState state) {
    super(ModTiles.hyperItemReceiver.get(), BLOCK_HYPER_ITEM_HANDLER, pos,state);
  }
}
