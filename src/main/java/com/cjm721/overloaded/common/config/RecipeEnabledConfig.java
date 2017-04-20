package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by CJ on 4/20/2017.
 */
public enum RecipeEnabledConfig implements IConfig {
    I;

    private static String category = "Recipe Enabled";

    public static boolean multiTool;
    public static boolean infiniteWaterSource;

    @Override
    public void init(Configuration config) {
        config.addCustomCategoryComment(category, "Enabled Recipes");

        multiTool = config.get(category,"multiTool", true).getBoolean();
        infiniteWaterSource = config.get(category,"infiniteWaterSource", true).getBoolean();
    }
}
