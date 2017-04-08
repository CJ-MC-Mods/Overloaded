package com.cjm721.ibhstd.common.block.tile;

import com.cjm721.ibhstd.common.storage.energy.LongEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;


/**
 * Created by CJ on 4/8/2017.
 */
public class TileInfiniteCapacitor extends TileEntity {

    LongEnergyStorage energyStorage;

    public TileInfiniteCapacitor() {
        energyStorage = new LongEnergyStorage();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
    }

    public LongEnergyStorage getStorage() {
        return energyStorage;
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
