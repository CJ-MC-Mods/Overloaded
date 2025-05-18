package com.cjm721.overloaded.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class MultiArmorConfig implements ConfigSectionHandler {

  public int baseCost;
  private ModConfigSpec.IntValue baseCostSpec;

  public double damageMultiplier;
  private ModConfigSpec.DoubleValue damageMultiplierSpec;

  public double absoluteDamageMultiplier;
  private ModConfigSpec.DoubleValue absoluteDamageMultiplierSpec;

  public double unblockableMultiplier;
  private ModConfigSpec.DoubleValue unblockableMultiplierSpec;

  public int maxFoodLevel;
  private ModConfigSpec.IntValue maxFoodLevelSpec;

  public double maxSaturationLevel;
  private ModConfigSpec.DoubleValue maxSaturationLevelSpec;

  public int costPerFood;
  private ModConfigSpec.IntValue costPerFoodSpec;

  public double costPerSaturation;
  private ModConfigSpec.DoubleValue costPerSaturationSpec;

  public int removeEffect;
  private ModConfigSpec.IntValue removeEffectSpec;

  public int costPerHealth;
  private ModConfigSpec.IntValue costPerHealthSpec;

  public int extinguishCost;
  private ModConfigSpec.IntValue extinguishCostSpec;

  public int costPerAir;
  private ModConfigSpec.IntValue costPerAirSpec;

  public int noClipEnergyPerTick;
  private ModConfigSpec.IntValue noClipEnergyPerTickSpec;

  public double maxFlightSpeed;
  private ModConfigSpec.DoubleValue maxFlightSpeedSpec;

  public int energyPerTickFlying;
  private ModConfigSpec.IntValue energyPerTickFlyingSpec;

  public double energyMultiplierPerFlightSpeed;
  private ModConfigSpec.DoubleValue energyMultiplierPerFlightSpeedSpec;

  public double maxGroundSpeed;
  private ModConfigSpec.DoubleValue maxGroundSpeedSpec;

  public double energyPerBlockWalked;
  private ModConfigSpec.DoubleValue energyPerBlockWalkedSpec;

  public double energyMultiplierPerGroundSpeed;
  private ModConfigSpec.DoubleValue energyMultiplierPerGroundSpeedSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ModConfigSpec.Builder builder) {
    if (type != ModConfig.Type.SERVER) {
      return;
    }

    builder.push("multi-armor");

    baseCostSpec =
        builder
            .comment("Base cost of any defensive action. [Default: 10]")
            .defineInRange("baseCost", 10, 0, Integer.MAX_VALUE);

    damageMultiplierSpec =
        builder
            .comment("How much energy is used base per damage. [Default: 100]")
            .defineInRange("damageMultiplier", 100, 0, Float.MAX_VALUE);
    damageMultiplierSpec =
        builder
            .comment("How much energy is used base per damage. [Default: 100]")
            .defineInRange("damageMultiplier", 100, 0, Float.MAX_VALUE);

    absoluteDamageMultiplierSpec =
        builder
            .comment("Energy Multiplier for Absolute Damage. [Default: 10]")
            .defineInRange("absoluteDamageMultiplier", 10, 0, Float.MAX_VALUE);

    unblockableMultiplierSpec =
        builder
            .comment("Energy Multiplier for Unblockable Damage. [Default: 10]")
            .defineInRange("unblockableMultiplier", 10, 0, Float.MAX_VALUE);

    maxFoodLevelSpec =
        builder
            .comment("Max level to feed up too. [Default By Vanilla MC: 20]")
            .defineInRange("maxFoodLevel", 20, 0, Integer.MAX_VALUE);

    maxSaturationLevelSpec =
        builder
            .comment("Max saturation level to add too. [Default By Vanilla MC: 5")
            .defineInRange("maxSaturationLevel", 5, 0, Float.MAX_VALUE);

    costPerFoodSpec =
        builder
            .comment("Energy Cost per food amount to fill. [Default: 1000]")
            .defineInRange("costPerFood", 1000, 0, Integer.MAX_VALUE);

    costPerSaturationSpec =
        builder
            .comment("Energy Cost per Saturation amount to fill. [Default: 4000]")
            .defineInRange("costPerSaturation", 4000, 0, Float.MAX_VALUE);

    removeEffectSpec =
        builder
            .comment("Energy Cost to remove a potion effect. [Default: 10000]")
            .defineInRange("removeEffect", 10000, 0, Integer.MAX_VALUE);

    costPerHealthSpec =
        builder
            .comment("Energy Cost per health amount to heal. [Default: 5000]")
            .defineInRange("costPerHealth", 5000, 0, Integer.MAX_VALUE);

    extinguishCostSpec =
        builder
            .comment("Energy to Extinguish the player. [Default: 1000]")
            .defineInRange("extinguishCost", 1000, 0, Integer.MAX_VALUE);

    costPerAirSpec =
        builder
            .comment("Energy per Air Tick. [Default: 10]")
            .defineInRange("costPerAir", 10, 0, Integer.MAX_VALUE);

    noClipEnergyPerTickSpec =
        builder
            .comment("Energy Per Tick to use No Clip. [Default: 100000]")
            .defineInRange("noClipEnergyPerTick", 100000, 0, Integer.MAX_VALUE);

    maxFlightSpeedSpec =
        builder
            .comment("Max flight speed. [Default: 5]")
            .defineInRange("maxFlightSpeed", 5, 0, Float.MAX_VALUE);

    energyPerTickFlyingSpec =
        builder
            .comment("Energy used per tick while flying. [Default:10]")
            .defineInRange("energyPerTickFlying", 10, 0, Integer.MAX_VALUE);

    energyMultiplierPerFlightSpeedSpec =
        builder
            .comment("Energy use multiple per flight speed. [Default: 10]")
            .defineInRange("energyMultiplierPerFlightSpeed", 10, 0, Float.MAX_VALUE);

    maxGroundSpeedSpec =
        builder
            .comment("Max ground speed. [Default: 10]")
            .defineInRange("maxGroundSpeed", 10D, 0D, Float.MAX_VALUE);

    energyPerBlockWalkedSpec =
        builder
            .comment("Energy per block walked. [Default: 10]")
            .defineInRange("energyPerBlockWalked", 10D, 0, Float.MAX_VALUE);

    energyMultiplierPerGroundSpeedSpec =
        builder
            .comment("Energy use multiplier per ground speed. [Default: 5]")
            .defineInRange("energyMultiplierPerGroundSpeed", 5D, 0, Float.MAX_VALUE);

    builder.pop();
  }

  @Override
  public void update() {
    baseCost = baseCostSpec.get();
    damageMultiplier = damageMultiplierSpec.get();
    absoluteDamageMultiplier = absoluteDamageMultiplierSpec.get();
    unblockableMultiplier = unblockableMultiplierSpec.get();
    maxFoodLevel = maxFoodLevelSpec.get();
    maxSaturationLevel = maxSaturationLevelSpec.get();
    costPerFood = costPerFoodSpec.get();
    costPerSaturation = costPerSaturationSpec.get();
    removeEffect = removeEffectSpec.get();
    costPerHealth = costPerHealthSpec.get();
    extinguishCost = extinguishCostSpec.get();
    costPerAir = costPerAirSpec.get();
    noClipEnergyPerTick = noClipEnergyPerTickSpec.get();
    maxFlightSpeed = maxFlightSpeedSpec.get();
    energyPerTickFlying = energyPerTickFlyingSpec.get();
    energyMultiplierPerFlightSpeed = energyMultiplierPerFlightSpeedSpec.get();
    maxGroundSpeed = maxGroundSpeedSpec.get();
    energyPerBlockWalked = energyPerBlockWalkedSpec.get();
    energyMultiplierPerGroundSpeed = energyMultiplierPerGroundSpeedSpec.get();
  }
}
