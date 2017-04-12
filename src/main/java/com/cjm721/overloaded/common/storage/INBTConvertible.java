package com.cjm721.overloaded.common.storage;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTConvertible {
    void readFromNBT(NBTTagCompound compound);
    NBTTagCompound writeToNBT(NBTTagCompound compound);
}