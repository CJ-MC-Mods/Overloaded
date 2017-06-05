package com.cjm721.overloaded.util.itemwrapper;

import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class IntEnergyWrapper implements ICapabilityProvider, IEnergyStorage {

    private final ItemStack stack;

    public IntEnergyWrapper(ItemStack stack) {
        this.stack = stack;

        NBTTagCompound tagCompound = this.stack.getTagCompound();
        if(tagCompound == null) {
            tagCompound = new NBTTagCompound();
        }

        if(!tagCompound.hasKey("IntEnergyStorage")) {
            NBTTagCompound storageTag = new NBTTagCompound();
            LongEnergyStorage storage = new LongEnergyStorage();

            storage.writeToNBT(storageTag);
            tagCompound.setTag("IntEnergyStorage", storageTag);
            this.stack.setTagCompound(tagCompound);
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ENERGY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(hasCapability(capability,facing)) {
            return (T) this;
        }
        return null;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        EnergyStorage storage = getStorage();
        try{
            return storage.receiveEnergy(maxReceive,simulate);
        }
        finally {
            this.setStorage(storage);
        }
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        EnergyStorage storage = getStorage();
        try{
            return storage.extractEnergy(maxExtract,simulate);
        }
        finally {
            this.setStorage(storage);
        }
    }

    @Override
    public int getEnergyStored() {
        return getStorage().getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return getStorage().getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return getStorage().canExtract();
    }

    @Override
    public boolean canReceive() {
        return getStorage().canReceive();
    }

    @Nonnull
    private EnergyStorage getStorage() {
        int energy = stack.getTagCompound().getInteger("IntEnergyStorage");

        return new EnergyStorage(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,energy);
    }

    private void setStorage(@Nonnull EnergyStorage storage) {
        stack.getTagCompound().setInteger("IntEnergyStorage", storage.getEnergyStored());
    }
}
