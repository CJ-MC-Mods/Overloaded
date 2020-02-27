package com.cjm721.overloaded.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class RayGunConfig implements ConfigSectionHandler {
  public int energyPerShot;
  private ForgeConfigSpec.IntValue energyPerShotSpec;

  public int maxRange;
  private ForgeConfigSpec.IntValue maxRangeSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ForgeConfigSpec.Builder builder) {
    if (type != ModConfig.Type.SERVER) {
      return;
    }

    builder.push("ray-gun");

    energyPerShotSpec =
        builder
            .comment("Energy used per shot. [Default: 100000]")
            .defineInRange("energyPerShot", 100000, 0, Integer.MAX_VALUE);

    maxRangeSpec =
        builder
            .comment("Max range to shoot. [Default: 128]")
            .defineInRange("maxRange", 128, 0, Integer.MAX_VALUE);

    builder.pop();
  }

  @Override
  public void update() {
    energyPerShot = energyPerShotSpec.get();
    maxRange = maxRangeSpec.get();
  }
}
