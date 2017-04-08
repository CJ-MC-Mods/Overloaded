package com.cjm721.ibhstd.common.block.tile;

import com.cjm721.ibhstd.common.util.NumberUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

import static com.cjm721.ibhstd.common.util.FluidUtil.fluidsAreEqual;
import static com.cjm721.ibhstd.common.util.NumberUtil.addToMax;
import static com.cjm721.ibhstd.common.util.NumberUtil.AddReturn;

/**
 * Created by CJ on 4/8/2017.
 */
public class TileInfiniteTank extends TileEntity implements IFluidHandler {

    long storedAmount;
    FluidStack storedFluid;

    /**
     * Returns an array of objects which represent the internal tanks.
     * These objects cannot be used to manipulate the internal tanks.
     *
     * @return Properties for the relevant internal tanks.
     */
    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[0];
    }

    /**
     * Fills fluid into internal tanks, distribution is left entirely to the IFluidHandler.
     *
     * @param resource FluidStack representing the Fluid and maximum amount of fluid to be filled.
     * @param doFill   If false, fill will only be simulated.
     * @return Amount of resource that was (or would have been, if simulated) filled.
     */
    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(storedFluid == null) {
            storedFluid = resource;
            if(doFill) storedAmount = resource.amount;
            return resource.amount;
        }

        if(fluidsAreEqual(storedFluid, resource)) {
            AddReturn<Long> value = addToMax(storedAmount, resource.amount);
            if(doFill) storedAmount = value.result;

            return resource.amount - value.overflow.intValue();
        }

        return 0;
    }

    /**
     * Drains fluid out of internal tanks, distribution is left entirely to the IFluidHandler.
     *
     * @param resource FluidStack representing the Fluid and maximum amount of fluid to be drained.
     * @param doDrain  If false, drain will only be simulated.
     * @return FluidStack representing the Fluid and amount that was (or would have been, if
     * simulated) drained.
     */
    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if(!fluidsAreEqual(storedFluid, resource))
            return resource;

        FluidStack toReturn = storedFluid.copy();
        toReturn.amount = (int) Math.min(storedAmount, resource.amount);

        if(doDrain) {
            storedAmount -= toReturn.amount;
            if(storedAmount == 0)
                storedFluid = null;
        }

        return toReturn;
    }

    /**
     * Drains fluid out of internal tanks, distribution is left entirely to the IFluidHandler.
     * <p/>
     * This method is not Fluid-sensitive.
     *
     * @param maxDrain Maximum amount of fluid to drain.
     * @param doDrain  If false, drain will only be simulated.
     * @return FluidStack representing the Fluid and amount that was (or would have been, if
     * simulated) drained.
     */
    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if(storedFluid == null)
            return null;

        FluidStack toReturn = storedFluid.copy();
        toReturn.amount = (int) Math.min(storedAmount, maxDrain);

        if(doDrain) {
            storedAmount -= toReturn.amount;
            if(storedAmount == 0)
                storedFluid = null;
        }

        return toReturn;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(storedFluid != null) {
            NBTTagCompound tag = new NBTTagCompound();
            storedFluid.writeToNBT(tag);
            compound.setTag("Fluid", tag);
        }

        compound.setLong("Count", storedAmount);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        storedFluid = compound.hasKey("Fluid") ? FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid")) : null;
        storedAmount = compound.hasKey("Count") ? compound.getLong("Count") : 0L;
    }

    public FluidStack getStoredFluid() {
        return storedFluid;
    }

    public long getStoredAmount() {
        return storedAmount;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }
}
