package com.cjm721.overloaded.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ProductionConfig implements ConfigSectionHandler {

  public int energyPerCookTime;
  private ForgeConfigSpec.IntValue energyPerCookTimeSpec;

  @Override
  public void appendToBuilder(ForgeConfigSpec.Builder builder) {
    builder.push("production");

    energyPerCookTimeSpec =
        builder
            .comment("Energy per cook time to use to smelt items. [Default: 10]")
            .defineInRange("energyPerCookTime",10,0,Integer.MAX_VALUE);

    builder.pop();
  }

  @Override
  public void update() {
    energyPerCookTime = energyPerCookTimeSpec.get();
  }
}
