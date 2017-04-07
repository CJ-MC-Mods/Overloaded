package com.cjm721.ibhstd.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

/**
 * Created by CJ on 4/7/2017.
 */
public final class ItemUtil {
    public static boolean itemsAreEqual(@Nullable ItemStack a, @Nullable ItemStack b) {
        if(a == b)
            return true;
        if(a == null || b == null)
            return false;

        if(!a.isItemEqual(b))
            return false;
        NBTTagCompound compoundAlpha = a.getTagCompound();
        NBTTagCompound compoundBeta =  b.getTagCompound();
        if(compoundAlpha == null)
            return compoundBeta == null;
        return  compoundAlpha.equals(compoundBeta);
    }
}
