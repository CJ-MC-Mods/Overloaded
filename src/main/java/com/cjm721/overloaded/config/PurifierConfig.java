package com.cjm721.overloaded.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PurifierConfig implements ConfigSectionHandler {

  public int energyPerOperation;
  private ForgeConfigSpec.IntValue energyPerOperationSpec;

  public int energyPerHardness;
  private ForgeConfigSpec.IntValue energyPerHardnessSpec;

  @Override
  public void appendToBuilder(ForgeConfigSpec.Builder builder) {
    builder.push("purifier");

    energyPerOperationSpec =
        builder
            .comment("Energy Per Operation Base. [Default: 10000]")
            .defineInRange("energyPerOperation", 10000, 0, Integer.MAX_VALUE);

    energyPerHardnessSpec =
        builder
            .comment("Energy Per Hardness of Block. [Default: 1000]")
            .defineInRange("energyPerHardness", 1000, 0, Integer.MAX_VALUE);

    builder.pop();
  }

  @Override
  public void update() {
    energyPerOperation = energyPerOperationSpec.get();
    energyPerHardness = energyPerHardnessSpec.get();
  }
}
