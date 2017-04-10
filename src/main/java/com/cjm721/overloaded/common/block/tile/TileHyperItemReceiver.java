package com.cjm721.overloaded.common.block.tile;

import com.cjm721.overloaded.common.block.tile.base.AbstractTileHyperReceiver;
import com.cjm721.overloaded.common.storage.IHyperHandler;
import com.cjm721.overloaded.common.storage.LongItemStack;
import com.cjm721.overloaded.common.storage.item.IHyperHandlerItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import static com.cjm721.overloaded.common.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;


/**
 * Created by CJ on 4/8/2017.
 */
public class TileHyperItemReceiver extends AbstractTileHyperReceiver<LongItemStack, Capability<IHyperHandlerItem>> {

    public TileHyperItemReceiver() {
        super(HYPER_ITEM_HANDLER);
    }


    public LongItemStack receive(LongItemStack stack) {
        for(EnumFacing side: EnumFacing.values()) {
            TileEntity te = this.getWorld().getTileEntity(this.getPos().add(side.getDirectionVec()));

            if(te == null || !te.hasCapability(HYPER_ITEM_HANDLER, side.getOpposite()))
                continue;

            IHyperHandlerItem temp = te.getCapability(HYPER_ITEM_HANDLER, side.getOpposite());
            stack = temp.give(stack, true);

            if(stack.getAmount() == 0L)
                return stack;
        }
        return stack;
    }
}
