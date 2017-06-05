package com.cjm721.overloaded.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class IntEnergyWrapper implements ICapabilitySerializable<NBTTagCompound> {

    private EnergyStorage storage;

    public IntEnergyWrapper(ItemStack stack, @Nullable NBTTagCompound tagCompound) {
        storage = new EnergyStorage(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,0);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ENERGY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == ENERGY) {
            return (T) storage;
        }
        return null;
    }

    @Override
    @Nonnull
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("IntEnergyStorage", storage == null ? 0 :storage.getEnergyStored());
        return tagCompound;
    }

    @Override
    public void deserializeNBT(@Nullable NBTTagCompound nbt) {
        int energy = 0;
        if(nbt != null && nbt.hasKey("IntEnergyStorage")) {
            energy = nbt.getInteger("IntEnergyStorage");
        }
        storage = new EnergyStorage(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, energy);
    }
}
