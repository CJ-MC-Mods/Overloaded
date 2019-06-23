package com.cjm721.overloaded.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ResolutionConfig implements ConfigSectionHandler {
  public int blockResolution;
  private ForgeConfigSpec.IntValue blockResolutionSpec;

  public int itemResolution;
  private ForgeConfigSpec.IntValue itemResolutionSpec;

  public int multiArmorResolution;
  private ForgeConfigSpec.IntValue multiArmorResolutionSpec;

  @Override
  public void appendToBuilder(ForgeConfigSpec.Builder builder) {
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

    builder.pop();
  }

  @Override
  public void update() {
    blockResolution = blockResolutionSpec.get();
    itemResolution = itemResolutionSpec.get();
    multiArmorResolution = multiArmorResolutionSpec.get();
  }
}
