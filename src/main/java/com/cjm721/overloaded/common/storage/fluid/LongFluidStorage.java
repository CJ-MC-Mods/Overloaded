package com.cjm721.overloaded.common.storage.fluid;

import com.cjm721.overloaded.common.storage.INBTConvertable;
import com.cjm721.overloaded.common.storage.LongFluidStack;
import com.cjm721.overloaded.common.storage.LongItemStack;
import com.cjm721.overloaded.common.util.NumberUtil;
import com.cjm721.overloaded.magic.fluid.IHyperFluidHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.common.util.FluidUtil.fluidsAreEqual;
import static com.cjm721.overloaded.common.util.NumberUtil.addToMax;

/**
 * Created by CJ on 4/8/2017.
 */
public class LongFluidStorage implements IFluidHandler, IHyperFluidHandler, INBTConvertable {

    LongFluidStack storedFluid;

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
        LongFluidStack fluidStack = give(new LongFluidStack(resource, resource.amount), doFill);

        if(fluidStack == null || fluidStack.amount == 0L) {
            return 0;
        }

        return (int) fluidStack.amount;
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
        if(storedFluid == null || !fluidsAreEqual(storedFluid.fluidStack, resource))
            return null;

        LongFluidStack result = take(resource.amount, doDrain);

        if(result.amount == 0L) {
            return null;
        }

        FluidStack toReturn = resource.copy();
        toReturn.amount = (int) result.amount;

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
        LongFluidStack result = take(maxDrain, doDrain);

        if(result.amount == 0L) {
            return null;
        }

        FluidStack toReturn = result.fluidStack.copy();
        toReturn.amount = (int) result.amount;

        return toReturn;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        FluidStack fluidStack = compound.hasKey("Fluid") ? FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid")) : null;
        long amount = compound.hasKey("Count") ? compound.getLong("Count") : 0L;

        this.storedFluid = new LongFluidStack(fluidStack, amount);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if(storedFluid != null) {
            NBTTagCompound tag = new NBTTagCompound();
            storedFluid.fluidStack.writeToNBT(tag);
            compound.setTag("Fluid", tag);
            compound.setLong("Count", storedFluid.amount);
        }

        return compound;
    }

    public LongFluidStack getFluidStack() {
        return storedFluid;
    }

    @Override
    public LongFluidStack status() {
        return storedFluid;
    }

    @Override
    @Nonnull
    public LongFluidStack take(long aLong, boolean doAction) {
        if (storedFluid == null)
            return LongFluidStack.EMPTY_STACK;

        LongFluidStack toReturn = new LongFluidStack(storedFluid.fluidStack,Math.min(storedFluid.amount, aLong));

        if (doAction) {
            storedFluid.amount -= toReturn.amount;
            if (storedFluid.amount == 0)
                storedFluid = null;
        }

        return toReturn;
    }

    @Override
    @Nonnull
    public LongFluidStack give(LongFluidStack fluidStack, boolean doAction) {
        if(storedFluid == null) {
            if(doAction){
                storedFluid = fluidStack;
            }
            return fluidStack;
        }

        if(fluidsAreEqual(storedFluid.fluidStack, fluidStack.fluidStack)) {
            NumberUtil.AddReturn<Long> value = addToMax(storedFluid.amount, fluidStack.amount);
            if(doAction) storedFluid.amount = value.result;

            return new LongFluidStack(storedFluid.fluidStack, fluidStack.amount - value.overflow);
        }

        return LongFluidStack.EMPTY_STACK;
    }
}
