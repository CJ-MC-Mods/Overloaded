package com.cjm721.overloaded.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class ResolutionConfig implements ConfigSectionHandler {
  public int blockResolution;
  private ForgeConfigSpec.IntValue blockResolutionSpec;

  public int itemResolution;
  private ForgeConfigSpec.IntValue itemResolutionSpec;

  public int multiArmorResolution;
  private ForgeConfigSpec.IntValue multiArmorResolutionSpec;

  public boolean multiArmorFancyModel;
  private ForgeConfigSpec.BooleanValue multiArmorFancyModelSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ForgeConfigSpec.Builder builder) {
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
        builder.comment("To use the fancy armor model or not. [Default: false]")
        .define("multiArmorFancyModel", false);
    builder.pop();
  }

  @Override
  public void update() {
    blockResolution = blockResolutionSpec.get();
    itemResolution = itemResolutionSpec.get();
    multiArmorResolution = multiArmorResolutionSpec.get();
    multiArmorFancyModel = multiArmorFancyModelSpec.get();
  }
}
