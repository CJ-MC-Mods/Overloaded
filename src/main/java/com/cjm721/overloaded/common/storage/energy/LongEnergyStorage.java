package com.cjm721.overloaded.common.storage.energy;

import com.cjm721.overloaded.common.util.NumberUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

import static com.cjm721.overloaded.common.util.NumberUtil.addToMax;

/**
 * Created by CJ on 4/8/2017.
 */
public class LongEnergyStorage implements IEnergyStorage, IHyperEnergyHandler {

    long storedAmount;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setLong("Count", storedAmount);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storedAmount = compound.hasKey("Count") ? compound.getLong("Count") : 0L;
    }

    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive Maximum amount of energy to be inserted.
     * @param simulate   If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        NumberUtil.AddReturn<Long> result = addToMax(storedAmount, maxReceive);

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
        return (int) (((double)storedAmount/(double)Long.MAX_VALUE) * Integer.MAX_VALUE);
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
    public long status() {
        return storedAmount;
    }

    @Override
    public long give(long aLong, boolean doAction) {
        NumberUtil.AddReturn<Long> longAddReturn = NumberUtil.addToMax(storedAmount, aLong);

        if(doAction)
            storedAmount = longAddReturn.result;

        return longAddReturn.overflow;
    }

    @Override
    public long take(long aLong, boolean doAction) {
        long newStoredAmount = Math.max(storedAmount - aLong, 0);

        if(doAction)
            storedAmount = newStoredAmount;

        return Math.min(storedAmount,aLong);
    }
}
