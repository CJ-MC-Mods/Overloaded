package com.cjm721.overloaded.block.tile.hyperTransfer;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.block.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.storage.LongItemStack;
import com.cjm721.overloaded.storage.item.IHyperHandlerItem;
import net.minecraft.tileentity.TileEntityType;

import static com.cjm721.overloaded.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

public class TileHyperItemReceiver
    extends AbstractTileHyperReceiver<LongItemStack, IHyperHandlerItem> {

  public TileHyperItemReceiver() {
    super(
        TileEntityType.Builder.create(TileHyperItemReceiver::new, ModBlocks.hyperItemReceiver)
            .build(null),
        HYPER_ITEM_HANDLER);
  }
}
