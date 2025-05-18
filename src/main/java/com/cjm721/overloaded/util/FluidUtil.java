package com.cjm721.overloaded.util;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.fluids.FluidStack;

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
