package com.cjm721.overloaded.block.tile.hyperTransfer;

import com.cjm721.overloaded.block.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.storage.LongItemStack;
import com.cjm721.overloaded.storage.item.IHyperHandlerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

/**
 * {@link TileEntity That is able to receive items from a remote source}
 */
public class TileHyperItemSender extends AbstractTileHyperSender<LongItemStack, IHyperHandlerItem> {

    public TileHyperItemSender() {
        super(HYPER_ITEM_HANDLER);
    }

    @Override
    @Nonnull
    protected LongItemStack generate(long amount) {
        return new LongItemStack(ItemStack.EMPTY, amount);
    }

    @Override
    protected boolean isCorrectPartnerType(TileEntity te) {
        return te instanceof TileHyperItemReceiver;
    }
}
