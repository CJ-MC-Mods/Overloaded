package com.cjm721.overloaded.common.block.tile;

import com.cjm721.overloaded.common.block.tile.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.common.block.tile.base.AbstractTileHyperSender;
import com.cjm721.overloaded.common.storage.LongItemStack;
import com.cjm721.overloaded.common.storage.item.IHyperHandlerItem;
import net.minecraft.tileentity.TileEntity;

import static com.cjm721.overloaded.common.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

/**
 * {@link TileEntity That is able to receive items from a remote source}
 */
public class TileHyperItemSender extends AbstractTileHyperSender<LongItemStack,IHyperHandlerItem> {

    public TileHyperItemSender() {
        super(HYPER_ITEM_HANDLER);
    }

    @Override
    protected LongItemStack generate(long amount) {
        return new LongItemStack(null,amount);
    }

    @Override
    protected boolean isCorrectPartnerType(TileEntity te) {
        return te instanceof TileHyperItemReceiver;
    }
}
