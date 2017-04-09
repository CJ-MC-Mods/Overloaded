package com.cjm721.overloaded.common.storage.fluid;

import com.cjm721.overloaded.common.storage.INBTConvertable;
import com.cjm721.overloaded.common.util.NumberUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.common.util.FluidUtil.fluidsAreEqual;
import static com.cjm721.overloaded.common.util.NumberUtil.addToMax;

/**
 * Created by CJ on 4/8/2017.
 */
public class LongFluidStorage implements IFluidHandler, INBTConvertable {

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
            NumberUtil.AddReturn<Long> value = addToMax(storedAmount, resource.amount);
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
        if (storedFluid == null)
            return null;

        FluidStack toReturn = storedFluid.copy();
        toReturn.amount = (int) Math.min(storedAmount, maxDrain);

        if (doDrain) {
            storedAmount -= toReturn.amount;
            if (storedAmount == 0)
                storedFluid = null;
        }

        return toReturn;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storedFluid = compound.hasKey("Fluid") ? FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid")) : null;
        storedAmount = compound.hasKey("Count") ? compound.getLong("Count") : 0L;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if(storedFluid != null) {
            NBTTagCompound tag = new NBTTagCompound();
            storedFluid.writeToNBT(tag);
            compound.setTag("Fluid", tag);
        }

        compound.setLong("Count", storedAmount);

        return compound;
    }

    public FluidStack getStoredFluid() {
        return storedFluid;
    }

    public long getStoredAmount() {
        return storedAmount;
    }
}
