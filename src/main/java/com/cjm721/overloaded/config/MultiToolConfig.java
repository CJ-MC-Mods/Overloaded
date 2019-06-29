package com.cjm721.overloaded.config;

import com.cjm721.overloaded.config.syncer.SyncToClient;
import net.minecraftforge.common.ForgeConfigSpec;

public class MultiToolConfig implements ConfigSectionHandler {

  @SyncToClient public int reach;
  public ForgeConfigSpec.IntValue reachSpec;

  public int placeBaseCost;
  public ForgeConfigSpec.IntValue placeBaseCostSpec;

  public int costPerMeterAway;
  public ForgeConfigSpec.IntValue costPerMeterAwaySpec;

  public int breakBaseCost;
  public ForgeConfigSpec.IntValue breakBaseCostSpec;

  public int breakCostMultiplier;
  public ForgeConfigSpec.IntValue breakCostMultiplierSpec;

  public int assistMode;
  public ForgeConfigSpec.IntValue assistModeSpec;

  @Override
  public void appendToBuilder(ForgeConfigSpec.Builder builder) {
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
  public void update() {
    reach = reachSpec.get();
    placeBaseCost = placeBaseCostSpec.get();
    costPerMeterAway = costPerMeterAwaySpec.get();
    breakBaseCost = breakBaseCostSpec.get();
    breakCostMultiplier = breakCostMultiplierSpec.get();
    assistMode = assistModeSpec.get();
  }
}
