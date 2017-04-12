package com.cjm721.overloaded.common.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public final class FluidUtil {
    public static boolean fluidsAreEqual(@Nullable FluidStack a, @Nullable FluidStack b) {
        if(a == b)
            return true;
        if(a == null || b == null)
            return false;

        if(!a.isFluidEqual(b))
            return false;
        NBTTagCompound compoundAlpha = a.tag;
        NBTTagCompound compoundBeta =  b.tag;
        if(compoundAlpha == null)
            return compoundBeta == null;
        return  compoundAlpha.equals(compoundBeta);
    }
}
