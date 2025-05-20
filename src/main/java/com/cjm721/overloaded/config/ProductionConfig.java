package com.cjm721.overloaded.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class ProductionConfig implements ConfigSectionHandler {

  public int energyPerCookTime;
  private ModConfigSpec.IntValue energyPerCookTimeSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ModConfigSpec.Builder builder) {
    if (type != ModConfig.Type.SERVER) {
      return;
    }

    builder.push("production");

    energyPerCookTimeSpec =
        builder
            .comment("Energy per cook time to use to smelt items. [Default: 10]")
            .defineInRange("energyPerCookTime", 10, 0, Integer.MAX_VALUE);

    builder.pop();
  }

  @Override
  public void update(ModConfig.Type type) {
    if (type != ModConfig.Type.SERVER) {
      return;
    }
    energyPerCookTime = energyPerCookTimeSpec.get();
  }
}
