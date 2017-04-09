package com.cjm721.ibhstd.common.block.tile.bases;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Created by CJ on 4/8/2017.
 */
public abstract class AbstractTileHyperItemNode extends TileEntity {

    protected BlockPos partnerBlockPos;
    protected int partnerWorldID;

    protected boolean partnerLoaded;
    protected AbstractTileHyperItemNode partner;


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


    @Override
    public void onChunkUnload() {
        if(partnerLoaded)
            partner.partnerUnloaded();
    }

    protected void partnerUnloaded() {
        this.partnerLoaded = false;
        this.partner = null;
    }

    protected AbstractTileHyperItemNode getPartner() {
        return partner;
    }

    public void setPartnetInfo(int partnerWorldId, BlockPos partnerPos) {
        this.partnerWorldID = partnerWorldId;
        this.partnerBlockPos = partnerPos;
    }
}
