package com.cjm721.overloaded.util.itemwrapper;

import com.cjm721.overloaded.storage.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class LongEnergyWrapper implements ICapabilityProvider, IEnergyStorage, IHyperHandlerEnergy {

    private final ItemStack stack;

    public LongEnergyWrapper(ItemStack stack) {
        this.stack = stack;

        NBTTagCompound tagCompound = this.stack.getTagCompound();
        if(tagCompound == null) {
            tagCompound = new NBTTagCompound();
        }

        if(!tagCompound.hasKey("EnergyStorage")) {
            NBTTagCompound storageTag = new NBTTagCompound();
            LongEnergyStorage storage = new LongEnergyStorage();

            storage.writeToNBT(storageTag);
            tagCompound.setTag("EnergyStorage", storageTag);
            this.stack.setTagCompound(tagCompound);
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ENERGY || capability == HYPER_ENERGY_HANDLER;
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
    public void readFromNBT(NBTTagCompound compound) { }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) { return compound; }

    @Nonnull
    @Override
    public LongEnergyStack status() {
        return getStorage().status();
    }

    @Nonnull
    @Override
    public LongEnergyStack take(@Nonnull LongEnergyStack stack, boolean doAction) {
        LongEnergyStorage storage = getStorage();
        try {
            return storage.take(stack,doAction);
        }
        finally {
            this.setStorage(storage);
        }
    }

    @Nonnull
    @Override
    public LongEnergyStack give(@Nonnull LongEnergyStack stack, boolean doAction) {
        LongEnergyStorage storage = getStorage();
        try {
            return storage.give(stack,doAction);
        }
        finally {
            this.setStorage(storage);
        }
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        LongEnergyStorage storage = getStorage();
        try {
            return storage.receiveEnergy(maxReceive,simulate);
        }
        finally {
            this.setStorage(storage);
        }
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        LongEnergyStorage storage = getStorage();
        try {
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
    private LongEnergyStorage getStorage() {
        NBTTagCompound compound = stack.getTagCompound().getCompoundTag("EnergyStorage");

        LongEnergyStorage storage = new LongEnergyStorage();
        storage.readFromNBT(compound);

        return storage;
    }

    private void setStorage(@Nonnull LongEnergyStorage storage) {
        NBTTagCompound compound = stack.getTagCompound().getCompoundTag("EnergyStorage");
        storage.writeToNBT(compound);
        stack.getTagCompound().setTag("EnergyStorage", compound);
    }
}
