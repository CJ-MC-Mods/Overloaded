package com.cjm721.overloaded.config;

import net.minecraftforge.common.ForgeConfigSpec;

public interface ConfigSectionHandler {
  void appendToBuilder(ForgeConfigSpec.Builder builder);
  void update();
}
