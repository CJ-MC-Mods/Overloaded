package com.cjm721.overloaded.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;

public abstract class AbstractTileEntityFaceable extends TileEntity {


    private EnumFacing front;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.front = EnumFacing.byIndex(compound.getInteger("Front"));
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Front", this.front.getIndex());

        return super.writeToNBT(compound);
    }

    public AbstractTileEntityFaceable setFacing(EnumFacing front) {
        this.front = front;
        return this;
    }

    EnumFacing getFacing() {
        return front;
    }
}
