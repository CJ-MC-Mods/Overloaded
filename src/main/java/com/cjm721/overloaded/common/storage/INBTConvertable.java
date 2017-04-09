package com.cjm721.ibhstd.common.storage;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by CJ on 4/8/2017.
 */
public interface INBTConvertable {
    void readFromNBT(NBTTagCompound compound);
    NBTTagCompound writeToNBT(NBTTagCompound compound);
}
