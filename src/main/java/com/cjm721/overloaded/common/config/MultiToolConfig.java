package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nonnull;

public enum MultiToolConfig implements IConfig {
    I;

    public static int reach;

    public static long placeBaseCost;
    public static long costPerMeterAway;
    public static long breakBaseCost;
    public static long breakCostMultiplier;

    private static final String category = "multiTool";

    @Override
    public void init(@Nonnull Configuration config) {
        config.addCustomCategoryComment(category, "Multi Tool Settings");

        reach = config.get(category, "reach", 128).getInt();

        placeBaseCost = Math.round(config.get(category, "placeBaseCost", 100).getDouble());
        costPerMeterAway = Math.round(config.get(category, "costPerMeterAway", 10).getDouble());
        breakBaseCost = Math.round(config.get(category, "breakBaseCost", 100).getDouble());
        breakCostMultiplier = Math.round(config.get(category, "breakCostMultiplier", 1).getDouble());
    }
}
