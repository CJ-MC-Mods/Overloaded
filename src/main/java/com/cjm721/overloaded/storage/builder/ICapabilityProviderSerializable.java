package com.cjm721.overloaded.storage.builder;

import net.neoforged.common.capabilities.ICapabilityProvider;
import net.neoforged.common.util.INBTSerializable;

public interface ICapabilityProviderSerializable extends ICapabilityProvider, INBTSerializable {
  String nbtKey();
}
