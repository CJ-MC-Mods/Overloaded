package com.cjm721.overloaded.storage.builder;

import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICapabilityProviderSerializable extends ICapabilityProvider, INBTSerializable {
  String nbtKey();
}
