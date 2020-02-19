package com.cjm721.overloaded.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public interface ConfigSectionHandler {
  void appendToBuilder(ModConfig.Type type, ForgeConfigSpec.Builder builder);
  void update();
}
