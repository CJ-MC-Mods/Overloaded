package com.cjm721.overloaded.common.block.tile;

import com.cjm721.overloaded.common.block.tile.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.common.block.tile.base.AbstractTileHyperSender;
import com.cjm721.overloaded.common.storage.IHyperHandler;
import com.cjm721.overloaded.common.storage.LongItemStack;
import com.cjm721.overloaded.common.storage.item.IHyperHandlerItem;
import com.cjm721.overloaded.common.util.CapabilityHyperItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

import static com.cjm721.overloaded.common.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

/**
 * {@link TileEntity That is able to receive items from a remote source}
 */
public class TileHyperItemSender extends AbstractTileHyperSender<LongItemStack,IHyperHandlerItem, Capability<IHyperHandlerItem>> {

    public TileHyperItemSender() {
        super(HYPER_ITEM_HANDLER);
    }


    @Override
    protected LongItemStack generate(long amount) {
        return new LongItemStack(null,amount);
    }
}
