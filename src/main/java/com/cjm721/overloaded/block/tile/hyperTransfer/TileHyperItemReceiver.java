package com.cjm721.overloaded.block.tile.hyperTransfer;

import com.cjm721.overloaded.block.tile.hyperTransfer.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.storage.LongItemStack;
import com.cjm721.overloaded.storage.item.IHyperHandlerItem;

import static com.cjm721.overloaded.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

public class TileHyperItemReceiver extends AbstractTileHyperReceiver<LongItemStack, IHyperHandlerItem> {

    public TileHyperItemReceiver() {
        super(HYPER_ITEM_HANDLER);
    }
}
