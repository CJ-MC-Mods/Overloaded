package com.cjm721.ibhstd.common.block.tile;

import com.cjm721.ibhstd.common.block.tile.bases.AbstractTileHyperItemNode;
import com.cjm721.ibhstd.common.storage.INBTConvertable;
import com.cjm721.ibhstd.common.storage.LongItemStack;
import com.cjm721.ibhstd.common.storage.item.LongItemStorage;
import com.cjm721.ibhstd.magic.item.IHyperItemHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import static com.cjm721.ibhstd.common.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

/**
 * Created by CJ on 4/8/2017.
 */
public class TileHyperItemSender extends AbstractTileHyperItemNode implements ITickable {

    private int delayTicks;

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if(delayTicks % 20 == 0) {
            if(partnerBlockPos == null)
                return;

            if(partnerLoaded) {
                moveItems();
            } else if (delayTicks % 200 == 0) {
                checkForPartner();
            }
        }
        delayTicks++;
    }

    private void checkForPartner() {
        WorldServer world = DimensionManager.getWorld(partnerWorldID);
        if(world != null && world.isBlockLoaded(partnerBlockPos)) {
            TileEntity partner = world.getTileEntity(partnerBlockPos);

            if(partner == null) {
                this.partnerBlockPos = null;
            } else {
                partnerLoaded = true;
                this.partner = (AbstractTileHyperItemNode)partner;
            }
        }
    }

    private void moveItems() {
        for(EnumFacing side: EnumFacing.values()) {
            TileEntity te = this.getWorld().getTileEntity(this.getPos().add(side.getDirectionVec()));

            if(te == null || !te.hasCapability(HYPER_ITEM_HANDLER, side.getOpposite()))
                continue;

            IHyperItemHandler handler = te.getCapability(HYPER_ITEM_HANDLER, side.getOpposite());
            LongItemStack itemStack = handler.take(Long.MAX_VALUE, false);
            if(itemStack != null && itemStack.amount > 0) {
                long leftOvers = ((TileHyperItemReceiver)this.getPartner()).receiveItems(itemStack);
                if(leftOvers != Long.MAX_VALUE)
                    handler.take(Long.MAX_VALUE - leftOvers, true);

            }
        }
    }
}
