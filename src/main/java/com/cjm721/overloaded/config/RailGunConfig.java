package com.cjm721.overloaded.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class RailGunConfig implements ConfigSectionHandler {

  public int minEnergy;
  private ForgeConfigSpec.IntValue minEngerySpec;

  public int maxEnergy;
  private ForgeConfigSpec.IntValue maxEnergySpec;

  public int stepEnergy;
  private ForgeConfigSpec.IntValue stepEnergySpec;

  public int maxRange;
  private ForgeConfigSpec.IntValue maxRangeSpec;

  public double damagePerRF;
  private ForgeConfigSpec.DoubleValue damagePerRFSpec;

  public double knockbackPerRF;
  private ForgeConfigSpec.DoubleValue knockbackPerRFSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ForgeConfigSpec.Builder builder) {
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
  public void update() {
    minEnergy = minEngerySpec.get();
    maxEnergy = maxEnergySpec.get();
    stepEnergy = stepEnergySpec.get();
    maxRange = maxRangeSpec.get();
    damagePerRF = damagePerRFSpec.get();
    knockbackPerRF = knockbackPerRFSpec.get();
  }
}
