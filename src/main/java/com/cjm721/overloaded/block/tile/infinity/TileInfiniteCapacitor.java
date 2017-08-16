package com.cjm721.overloaded.block.tile.infinity;

import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileInfiniteCapacitor extends TileEntity implements IDataUpdate {

    @Nonnull
    private LongEnergyStorage energyStorage;

    public TileInfiniteCapacitor() {
        energyStorage = new LongEnergyStorage(this);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagCompound energy = energyStorage.serializeNBT();
        compound.setTag("Energy", energy);
        return compound;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        energyStorage.deserializeNBT(compound.getCompoundTag("Energy"));
    }

    public LongEnergyStorage getStorage() {
        return energyStorage;
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == ENERGY || capability == HYPER_ENERGY_HANDLER) {
            return (T) energyStorage;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == ENERGY || capability == HYPER_ENERGY_HANDLER) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public void dataUpdated() {
        markDirty();
    }
}
