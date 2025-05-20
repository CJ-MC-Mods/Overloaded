package com.cjm721.overloaded.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class MultiToolConfig implements ConfigSectionHandler {

  public int reach;
  public ModConfigSpec.IntValue reachSpec;

  public int placeBaseCost;
  public ModConfigSpec.IntValue placeBaseCostSpec;

  public int costPerMeterAway;
  public ModConfigSpec.IntValue costPerMeterAwaySpec;

  public int breakBaseCost;
  public ModConfigSpec.IntValue breakBaseCostSpec;

  public int breakCostMultiplier;
  public ModConfigSpec.IntValue breakCostMultiplierSpec;

  public int assistMode;
  public ModConfigSpec.IntValue assistModeSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ModConfigSpec.Builder builder) {
    if (type != ModConfig.Type.SERVER) {
      return;
    }

    builder.push("multi-tool");

    reachSpec =
        builder
            .comment("Max range Multi-Tool can edit blocks [Default: 128]")
            .defineInRange("reach", 128, 0, Integer.MAX_VALUE);

    placeBaseCostSpec =
        builder
            .comment("Cost that is added on to every place [Default: 100]")
            .defineInRange("placeBaseCost", 100, 0, Integer.MAX_VALUE);

    costPerMeterAwaySpec =
        builder
            .comment("Cost per meter away [Default: 10]")
            .defineInRange("costPerMeterAway", 10, 0, Integer.MAX_VALUE);

    breakBaseCostSpec =
        builder
            .comment("Cost that is added on to every block break [Default: 100]")
            .defineInRange("breakBaseCost", 100, 0, Integer.MAX_VALUE);

    breakCostMultiplierSpec =
        builder
            .comment("Multiples the Hardness Cost by this. [Default: 1]")
            .defineInRange("breakCostMultiplier", 1, 0, Integer.MAX_VALUE);

    assistModeSpec =
        builder
            .comment(
                "0 - None, 1 Block Place Preview, 2 Block Break Preview, 3 Place/Break Preview. [Default: 1]")
            .defineInRange("assistMode", 3, 0, 3);

    builder.pop();
  }

  @Override
  public void update(ModConfig.Type type) {
    if (type != ModConfig.Type.SERVER) {
      return;
    }
    reach = reachSpec.get();
    placeBaseCost = placeBaseCostSpec.get();
    costPerMeterAway = costPerMeterAwaySpec.get();
    breakBaseCost = breakBaseCostSpec.get();
    breakCostMultiplier = breakCostMultiplierSpec.get();
    assistMode = assistModeSpec.get();
  }
}
