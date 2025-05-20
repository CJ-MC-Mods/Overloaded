package com.cjm721.overloaded.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class RailGunConfig implements ConfigSectionHandler {

  public int minEnergy;
  private ModConfigSpec.IntValue minEngerySpec;

  public int maxEnergy;
  private ModConfigSpec.IntValue maxEnergySpec;

  public int stepEnergy;
  private ModConfigSpec.IntValue stepEnergySpec;

  public int maxRange;
  private ModConfigSpec.IntValue maxRangeSpec;

  public double damagePerRF;
  private ModConfigSpec.DoubleValue damagePerRFSpec;

  public double knockbackPerRF;
  private ModConfigSpec.DoubleValue knockbackPerRFSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ModConfigSpec.Builder builder) {
    if (type != ModConfig.Type.SERVER) {
      return;
    }

    builder.push("rail-gun");

    minEngerySpec =
        builder
            .comment("Minimum energy used per shot. [Default: 1,000,000]")
            .defineInRange("minEnergy", 10000000, 0, Integer.MAX_VALUE);

    maxEnergySpec =
        builder
            .comment("Maximum energy used per shot. [Default: 2,000,000,000]")
            .defineInRange("maxEnergy", 2000000000, 0, Integer.MAX_VALUE);

    stepEnergySpec =
        builder
            .comment("Energy change step (via scroll wheel). [Default: 10,000,000]")
            .defineInRange("stepEnergy", 10000000, 0, Integer.MAX_VALUE);
    maxRangeSpec =
        builder
            .comment("Max range to shoot. [Default: 128]")
            .defineInRange("maxRange", 128, 0, Integer.MAX_VALUE);

    damagePerRFSpec =
        builder
            .comment("Damage per RF(FE) spent. [Default: 0.00001")
            .defineInRange("damagePerRF", 0.00001F, 0, Float.MAX_VALUE);

    knockbackPerRFSpec =
        builder
            .comment("Distance knockback per RF(FE) spent. [Default: 0.000001")
            .defineInRange("knockbackPerRF", 0.000001F, 0, Float.MAX_VALUE);

    builder.pop();
  }

  @Override
  public void update(ModConfig.Type type) {
    if (type != ModConfig.Type.SERVER) {
      return;
    }
    minEnergy = minEngerySpec.get();
    maxEnergy = maxEnergySpec.get();
    stepEnergy = stepEnergySpec.get();
    maxRange = maxRangeSpec.get();
    damagePerRF = damagePerRFSpec.get();
    knockbackPerRF = knockbackPerRFSpec.get();
  }
}
