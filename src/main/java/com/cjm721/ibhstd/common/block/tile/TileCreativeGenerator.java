package com.cjm721.ibhstd.common.block.tile;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

/**
 * Created by CJ on 4/5/2017.
 */
public class TileCreativeGenerator extends TileEntity implements IEnergyProvider, ITickable {

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return maxExtract;
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void update() {
        // TODO : Cache nearby blocks and update on block place / break / update events
        Arrays.stream(EnumFacing.values()).forEach(side -> {
            TileEntity te = this.getWorld().getTileEntity(this.getPos().add(side.getDirectionVec()));

            if(te != null && te instanceof IEnergyReceiver) {
                ((IEnergyReceiver)te).receiveEnergy(EnumFacing.UP, Integer.MAX_VALUE, false);
            }
        });
    }
}
