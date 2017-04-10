package com.cjm721.overloaded.common.block.tile;

import com.cjm721.overloaded.common.storage.LongItemStack;
import com.cjm721.overloaded.common.storage.item.IHyperItemHandler;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.util.Set;

import static com.cjm721.overloaded.common.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

/**
 * {@link TileEntity That is able to receive items from a remote source}
 */
public class TileHyperItemSender extends TileEntity implements ITickable {

    private int delayTicks;

    private BlockPos partnerBlockPos;
    private int partnerWorldID;

    @Override
    @MethodsReturnNonnullByDefault
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if(partnerBlockPos != null) {
            compound.setInteger("X", partnerBlockPos.getX());
            compound.setInteger("Y", partnerBlockPos.getY());
            compound.setInteger("Z", partnerBlockPos.getZ());
            compound.setInteger("WORLD", partnerWorldID);
        }

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey("X")) {
            int x = compound.getInteger("X");
            int y = compound.getInteger("Y");
            int z = compound.getInteger("Z");

            partnerBlockPos = new BlockPos(x, y, z);
            partnerWorldID = compound.getInteger("WORLD");
        }
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if(delayTicks % 20 == 0) {
            if(partnerBlockPos == null)
                return;

            TileHyperItemReceiver partner = findPartner();
            if(partner != null) {
                moveItems(partner);
            }
        }
        delayTicks++;
    }

    private TileHyperItemReceiver findPartner() {
        WorldServer world = DimensionManager.getWorld(partnerWorldID);
        if(world != null && world.isBlockLoaded(partnerBlockPos)) {
            TileEntity partnerTE = world.getTileEntity(partnerBlockPos);

            if(partnerTE == null || !(partnerTE instanceof TileHyperItemReceiver)) {
                this.partnerBlockPos = null;
                return null;
            } else {
                return (TileHyperItemReceiver) partnerTE;
            }
        }
        return null;
    }

    private void moveItems(TileHyperItemReceiver partner) {
        for(EnumFacing side: EnumFacing.values()) {
            TileEntity te = this.getWorld().getTileEntity(this.getPos().add(side.getDirectionVec()));

            if(te == null || !te.hasCapability(HYPER_ITEM_HANDLER, side.getOpposite()))
                continue;

            IHyperItemHandler handler = te.getCapability(HYPER_ITEM_HANDLER, side.getOpposite());
            LongItemStack itemStack = handler.take(Long.MAX_VALUE, false);
            if(itemStack != null && itemStack.amount > 0) {
                long leftOvers = partner.receiveItems(itemStack);
                if(leftOvers != itemStack.amount)
                    handler.take(itemStack.amount - leftOvers, true);
            }
        }
    }

    public void setPartnerInfo(int partnerWorldId, BlockPos partnerPos) {
        this.partnerWorldID = partnerWorldId;
        this.partnerBlockPos = partnerPos;
    }

    public String getRightClickMessage() {
        if(partnerBlockPos != null) {
            return String.format("Bound to Receiver at %d:%d,%d,%d", partnerWorldID, partnerBlockPos.getX(),partnerBlockPos.getY(),partnerBlockPos.getZ());
        }
        return "Not bound to anything";
    }
}
