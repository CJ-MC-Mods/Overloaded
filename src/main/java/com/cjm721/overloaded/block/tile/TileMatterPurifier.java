package com.cjm721.overloaded.block.tile;

import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.storage.fluid.LongFluidStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static com.cjm721.overloaded.util.CapabilityHyperFluid.HYPER_FLUID_HANDLER;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class TileMatterPurifier extends TileEntity implements ITickable {

    private LongFluidStorage fluidStorage;
    private LongEnergyStorage energyStorage;
    private ItemStack input;


    public TileMatterPurifier() {
        fluidStorage = new LongFluidStorage();
        energyStorage = new LongEnergyStorage();
        input = ItemStack.EMPTY;
    }

    @Override
    public void update() {
        if(!input.isEmpty()) {

        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        fluidStorage.readFromNBT(compound.getCompoundTag("Fluid"));
        energyStorage.deserializeNBT(compound.getCompoundTag("Energy"));
        input = new ItemStack(compound.getCompoundTag("Item"));
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound fluid = new NBTTagCompound();
        NBTTagCompound energy = energyStorage.serializeNBT();;

        fluidStorage.writeToNBT(fluid);


        compound.setTag("Fluid", fluid);
        compound.setTag("Energy", energy);
        compound.setTag("Item", input.getTagCompound());

        return super.writeToNBT(compound);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == HYPER_FLUID_HANDLER || capability == FLUID_HANDLER_CAPABILITY)
            return (T) fluidStorage;
        if(capability == HYPER_ENERGY_HANDLER || capability == ENERGY)
            return (T) energyStorage;

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == HYPER_FLUID_HANDLER || capability == FLUID_HANDLER_CAPABILITY)
            return true;
        if(capability == HYPER_ENERGY_HANDLER || capability == ENERGY)
            return true;

        return super.hasCapability(capability, facing);
    }
}
