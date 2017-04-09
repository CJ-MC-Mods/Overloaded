package com.cjm721.ibhstd.common.block.tile;

import com.cjm721.ibhstd.common.storage.LongItemStack;
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
public class TileHyperItemSender extends TileEntity implements ITickable {

    private int delayTicks;

    protected BlockPos partnerBlockPos;
    protected int partnerWorldID;

    protected boolean partnerLoaded;
    protected TileHyperItemReceiver partner;


    @Override
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
            TileEntity partnerTE = world.getTileEntity(partnerBlockPos);

            if(partnerTE == null || !(partnerTE instanceof TileHyperItemReceiver)) {
                this.partnerBlockPos = null;
                partner = null;
            } else {
                partnerLoaded = true;
                partner = (TileHyperItemReceiver) partnerTE;
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
                long leftOvers = partner.receiveItems(itemStack);
                if(leftOvers != itemStack.amount)
                    handler.take(itemStack.amount - leftOvers, true);

            }
        }
    }

    public void setPartnetInfo(int partnerWorldId, BlockPos partnerPos) {
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
