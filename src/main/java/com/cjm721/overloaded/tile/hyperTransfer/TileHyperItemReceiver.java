package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.storage.LongItemStack;
import com.cjm721.overloaded.storage.item.IHyperHandlerItem;
import net.minecraft.tileentity.TileEntityType;

import static com.cjm721.overloaded.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

public class TileHyperItemReceiver
    extends AbstractTileHyperReceiver<LongItemStack, IHyperHandlerItem> {

  public TileHyperItemReceiver() {
    super(ModTiles.hyperItemReceiver, HYPER_ITEM_HANDLER);
  }
}
