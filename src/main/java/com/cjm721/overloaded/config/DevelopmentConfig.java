package com.cjm721.overloaded.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class DevelopmentConfig implements ConfigSectionHandler {

  public boolean wipStuff;
  public ModConfigSpec.BooleanValue wipStuffSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ModConfigSpec.Builder builder) {
    if (type != ModConfig.Type.COMMON) {
      return;
    }

    builder.push("development");

    wipStuffSpec =
        builder
            .comment(
                "Stuff not deemed to be safe yet. IE World Corrupting / Crashing. [Default: false]")
            .define("wipStuff", false);

    builder.pop();
  }

  @Override
  public void update() {
    wipStuff = wipStuffSpec.get();
  }
}
