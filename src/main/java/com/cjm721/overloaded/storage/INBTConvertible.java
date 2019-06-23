package com.cjm721.overloaded.storage;

import net.minecraft.nbt.CompoundNBT;

public interface INBTConvertible {
  void read(CompoundNBT compound);

  CompoundNBT write(CompoundNBT compound);
}
