package com.cjm721.overloaded.util;

import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;

public final class FluidUtil {
    public static boolean fluidsAreEqual(@Nullable FluidStack a, @Nullable FluidStack b) {
        return FluidStack.isSameFluidSameComponents(a, b);
//        if (a == b)
//            return true;
//        if (a == null || b == null)
//            return false;
//
//        if (!a.isFluidEqual(b))
//            return false;
//        CompoundTag compoundAlpha = a..getTag();
//        CompoundTag compoundBeta = b.getTag();
//        if (compoundAlpha == null)
//            return compoundBeta == null;
//        return compoundAlpha.equals(compoundBeta);
    }
}
