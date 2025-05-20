package com.cjm721.overloaded.config;

import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public interface ConfigSectionHandler {
  void appendToBuilder(ModConfig.Type type, ModConfigSpec.Builder builder);

  void update(ModConfig.Type type);
}
