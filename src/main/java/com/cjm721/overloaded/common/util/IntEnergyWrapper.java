package com.cjm721.overloaded.common.util;

import com.cjm721.overloaded.common.storage.energy.LongEnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class IntEnergyWrapper implements ICapabilityProvider {

    private final ItemStack stack;

    public IntEnergyWrapper(ItemStack stack) {
        this.stack = stack;


        NBTTagCompound tagCompound = this.stack.getTagCompound();
        if(tagCompound == null) {
            tagCompound = new NBTTagCompound();
        }

        if(!tagCompound.hasKey("IntEnergyStorage")) {
            tagCompound.setInteger("IntEnergyStorage", 0);
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
        if(capability == ENERGY) {
            EnergyStorage storage = getStorage();
            try {
                return (T) storage;
            } finally {
                setStorage(storage);
            }
        }
        return null;
    }

    @Nonnull
    private EnergyStorage getStorage() {
        int energy = stack.getTagCompound().getInteger("IntEnergyStorage");

        EnergyStorage storage = new EnergyStorage(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE, energy);

        return storage;
    }

    private void setStorage(@Nonnull EnergyStorage storage) {
        stack.getTagCompound().setInteger("IntEnergyStorage", storage.getEnergyStored());
    }
}
