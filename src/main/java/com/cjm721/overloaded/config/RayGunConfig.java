package com.cjm721.overloaded.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class RayGunConfig implements ConfigSectionHandler {
  public int energyPerShot;
  private ModConfigSpec.IntValue energyPerShotSpec;

  public int maxRange;
  private ModConfigSpec.IntValue maxRangeSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ModConfigSpec.Builder builder) {
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
  public void update(ModConfig.Type type) {
    if (type != ModConfig.Type.SERVER) {
      return;
    }

    energyPerShot = energyPerShotSpec.get();
    maxRange = maxRangeSpec.get();
  }
}
