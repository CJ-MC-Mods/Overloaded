package com.cjm721.ibhstd.common.block.tile.bases;

import com.cjm721.ibhstd.common.block.ModBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

/**
 * Created by CJ on 4/8/2017.
 */
public abstract class AbstractTileHyperItemNode extends TileEntity implements ITickable {

    private BlockPos receiverPos;
    private int worldID;

    private boolean partnerLoaded;
    private AbstractTileHyperItemNode partner;

    private int delayTicks;

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if(delayTicks > 20) {
            delayTicks = 0;

        } else {
            delayTicks++;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if(receiverPos != null) {
            compound.setInteger("X", receiverPos.getX());
            compound.setInteger("Y", receiverPos.getY());
            compound.setInteger("Z", receiverPos.getZ());
            compound.setInteger("WORLD", worldID);
        }

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if(compound.hasKey("X")) {
            int x = compound.getInteger("X");
            int y = compound.getInteger("Y");
            int z = compound.getInteger("Z");

            receiverPos = new BlockPos(x,y,z);
            worldID = compound.getInteger("WORLD");
        }
    }


    @Override
    public void onChunkUnload() {
        if(partnerLoaded)
            partner.partnerUnloaded();
    }

    protected void partnerUnloaded() {
        this.partnerLoaded = false;
        this.partner = null;
    }



}
