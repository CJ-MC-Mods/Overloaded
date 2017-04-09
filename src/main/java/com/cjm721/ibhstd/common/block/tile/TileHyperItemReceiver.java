package com.cjm721.ibhstd.common.block.tile;

import com.cjm721.ibhstd.common.block.tile.bases.AbstractTileHyperItemNode;
import com.cjm721.ibhstd.common.storage.LongItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import static com.cjm721.ibhstd.common.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;


/**
 * Created by CJ on 4/8/2017.
 */
public class TileHyperItemReceiver extends AbstractTileHyperItemNode {

    public long receiveItems(LongItemStack itemStack) {
        for(EnumFacing side: EnumFacing.values()) {
            TileEntity te = this.getWorld().getTileEntity(this.getPos().add(side.getDirectionVec()));

            if(te == null || !te.hasCapability(HYPER_ITEM_HANDLER, side.getOpposite()))
                continue;

            itemStack = te.getCapability(HYPER_ITEM_HANDLER, side.getOpposite()).give(itemStack, true);

            if(itemStack == null || itemStack.amount == 0L)
                return 0L;
        }
        return itemStack.amount;
    }
}
