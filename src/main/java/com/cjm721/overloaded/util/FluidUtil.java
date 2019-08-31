package com.cjm721.overloaded.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public final class FluidUtil {
    public static boolean fluidsAreEqual(@Nullable FluidStack a, @Nullable FluidStack b) {
        if (a == b)
            return true;
        if (a == null || b == null)
            return false;

        if (!a.isFluidEqual(b))
            return false;
        CompoundNBT compoundAlpha = a.getTag();
        CompoundNBT compoundBeta = b.getTag();
        if (compoundAlpha == null)
            return compoundBeta == null;
        return compoundAlpha.equals(compoundBeta);
    }
}
