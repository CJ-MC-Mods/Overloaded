package com.cjm721.ibhstd.common.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;

import static com.cjm721.ibhstd.common.util.NumberUtil.AddReturn;
import static com.cjm721.ibhstd.common.util.NumberUtil.addToMax;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;


/**
 * Created by CJ on 4/8/2017.
 */
public class TileInfiniteCapacitor extends TileEntity implements IEnergyStorage {

    long storedAmount;

    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive Maximum amount of energy to be inserted.
     * @param simulate   If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        AddReturn<Long> result = addToMax(storedAmount, maxReceive);

        if(!simulate)
            storedAmount = result.result;

        return maxReceive - result.overflow.intValue();
    }

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract Maximum amount of energy to be extracted.
     * @param simulate   If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted from the storage.
     */
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        long result = Math.max(storedAmount - maxExtract, 0);
        try {
            if(storedAmount > maxExtract) {
                return maxExtract;
            }
            return (int) storedAmount;
        } finally {
            if(!simulate)
                storedAmount = result;
        }
    }

    /**
     * Returns the amount of energy currently stored.
     */
    @Override
    public int getEnergyStored() {
        return (int) Math.min(storedAmount, Integer.MAX_VALUE);
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    @Override
    public int getMaxEnergyStored() {
        return Integer.MAX_VALUE;
    }

    /**
     * Returns if this storage can have energy extracted.
     * If this is false, then any calls to extractEnergy will return 0.
     */
    @Override
    public boolean canExtract() {
        return true;
    }

    /**
     * Used to determine if this storage can receive energy.
     * If this is false, then any calls to receiveEnergy will return 0.
     */
    @Override
    public boolean canReceive() {
        return true;
    }

    public long getStoredAmount() {
        return storedAmount;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == ENERGY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }
}
