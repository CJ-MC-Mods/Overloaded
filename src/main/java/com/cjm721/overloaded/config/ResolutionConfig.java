package com.cjm721.overloaded.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class ResolutionConfig implements ConfigSectionHandler {
  public int blockResolution;
  private ModConfigSpec.IntValue blockResolutionSpec;

  public int itemResolution;
  private ModConfigSpec.IntValue itemResolutionSpec;

  public int multiArmorResolution;
  private ModConfigSpec.IntValue multiArmorResolutionSpec;

  public boolean multiArmorFancyModel;
  private ModConfigSpec.BooleanValue multiArmorFancyModelSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ModConfigSpec.Builder builder) {
    if (type != ModConfig.Type.CLIENT) {
      return;
    }

    builder.push("resolution");

    blockResolutionSpec =
        builder
            .comment("Resolution for Blocks. [Default: 256]")
            .defineInRange("blockResolution", 256, 1, Integer.MAX_VALUE);

    itemResolutionSpec =
        builder
            .comment("Resolution for Items. [Default: 256]")
            .defineInRange("itemResolution", 256, 1, Integer.MAX_VALUE);

    multiArmorResolutionSpec =
        builder
            .comment("Resolution for Multi-Armor. [Default: 256]")
            .defineInRange("multiArmorResolution", 256, 1, Integer.MAX_VALUE);

    multiArmorFancyModelSpec =
        builder
            .comment("To use the fancy armor model or not. [Default: false]")
            .define("multiArmorFancyModel", false);
    builder.pop();
  }

  @Override
  public void update(ModConfig.Type type) {
    if (type != ModConfig.Type.CLIENT) {
      return;
    }
    blockResolution = blockResolutionSpec.get();
    itemResolution = itemResolutionSpec.get();
    multiArmorResolution = multiArmorResolutionSpec.get();
    multiArmorFancyModel = multiArmorFancyModelSpec.get();
  }
}
