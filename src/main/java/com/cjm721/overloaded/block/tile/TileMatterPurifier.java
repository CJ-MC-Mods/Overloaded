package com.cjm721.overloaded.block.tile;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.proxy.CommonProxy;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileMatterPurifier extends TileEntity implements ITickable, IItemHandler {

    private final FluidTank fluidStorage;
    private EnergyStorage energyStorage;
    private ItemStack stack;

    public TileMatterPurifier() {
        fluidStorage = new FluidTank(Integer.MAX_VALUE);
        energyStorage = new EnergyStorage(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        stack = ItemStack.EMPTY;
    }

    @Override
    public void update() {
        if (stack.isEmpty())
            return;

        float hardness = ((ItemBlock) stack.getItem()).getBlock().getBlockHardness(null, null, null);
        if (hardness <= 0)
            return;

        float ecFloat = OverloadedConfig.purifierConfig.energyPerOperation + OverloadedConfig.purifierConfig.energyPerHardness * hardness;
        int energyCost = Math.round(ecFloat);

        if (energyStorage.extractEnergy(energyCost, true) != energyCost) {
            return;
        }

        int createdFluid = Math.round(hardness);
        FluidStack fluidStack = new FluidStack(CommonProxy.pureMatter, createdFluid);
        int storedFluid = fluidStorage.fill(fluidStack, false);

        if (storedFluid != createdFluid) {
            return;
        }

        stack.shrink(1);
        if (stack.getCount() == 0)
            stack = ItemStack.EMPTY;

        fluidStorage.fill(fluidStack, true);
        energyStorage.extractEnergy(energyCost, false);
        markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fluidStorage.readFromNBT(compound.getCompoundTag("Fluid"));
        energyStorage = new EnergyStorage(compound.getInteger("Energy"), Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound fluid = new NBTTagCompound();

        fluidStorage.writeToNBT(fluid);

        compound.setTag("Fluid", fluid);
        compound.setInteger("Energy", energyStorage.getEnergyStored());

        return super.writeToNBT(compound);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == FLUID_HANDLER_CAPABILITY)
            return (T) fluidStorage;
        if (capability == ENERGY)
            return (T) energyStorage;
        if (capability == ITEM_HANDLER_CAPABILITY)
            return (T) this;

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == FLUID_HANDLER_CAPABILITY)
            return true;
        if (capability == ENERGY)
            return true;
        if (capability == ITEM_HANDLER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    /**
     * Returns the number of slots available
     *
     * @return The number of slots available
     **/
    @Override
    public int getSlots() {
        return 1;
    }

    /**
     * Returns the ItemStack in a given slot.
     * <p>
     * The result's stack size may be greater than the itemstacks max size.
     * <p>
     * If the result is null, then the slot is empty.
     * If the result is not null but the stack size is zero, then it represents
     * an empty slot that will only accept* a specific itemstack.
     * <p>
     * <p/>
     * IMPORTANT: This ItemStack MUST NOT be modified. This method is not for
     * altering an inventories contents. Any implementers who are able to detect
     * modification through this method should throw an exception.
     * <p/>
     * SERIOUSLY: DO NOT MODIFY THE RETURNED ITEMSTACK
     *
     * @param slot Slot to query
     * @return ItemStack in given slot. May be null.
     **/
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return stack;
    }

    /**
     * Inserts an ItemStack into the given slot and return the remainder.
     * The ItemStack should not be modified in this function!
     * Note: This behaviour is subtly different from IFluidHandlers.fill()
     *
     * @param slot     Slot to insert into.
     * @param stack    ItemStack to insert.
     * @param simulate If true, the insertion is only simulated
     * @return The remaining ItemStack that was not inserted (if the entire stack is accepted, then return ItemStack.EMPTY).
     * May be the same as the input ItemStack if unchanged, otherwise a new ItemStack.
     **/
    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (this.stack.isEmpty()) {
            if (!simulate)
                this.stack = stack;

            return ItemStack.EMPTY;
        }

        if (ItemHandlerHelper.canItemStacksStack(this.stack, stack)) {
            int maxSize = this.stack.getMaxStackSize();
            int toTake = Math.min(maxSize - this.stack.getCount(), stack.getCount());

            if (!simulate) {
                this.stack.setCount(this.stack.getCount() + toTake);
            }

            ItemStack toReturn = stack.copy();
            toReturn.setCount(stack.getCount() - toTake);
            return toReturn;
        }

        return stack;
    }

    /**
     * Extracts an ItemStack from the given slot. The returned value must be null
     * if nothing is extracted, otherwise it's stack size must not be greater than amount or the
     * itemstacks getMaxStackSize().
     *
     * @param slot     Slot to extract from.
     * @param amount   Amount to extract (may be greater than the current stacks max limit)
     * @param simulate If true, the extraction is only simulated
     * @return ItemStack extracted from the slot, must be ItemStack.EMPTY, if nothing can be extracted
     **/
    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    /**
     * Retrieves the maximum stack size allowed to exist in the given slot.
     *
     * @param slot Slot to query.
     * @return The maximum stack size allowed in the slot.
     */
    @Override
    public int getSlotLimit(int slot) {
        return this.stack.getMaxStackSize();
    }
}
