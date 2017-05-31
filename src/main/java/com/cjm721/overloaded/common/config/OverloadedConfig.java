package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Config;

import static com.cjm721.overloaded.Overloaded.MODID;

@Config(modid = MODID)
public class OverloadedConfig {

    public static CompressedConfig compressedConfig = new CompressedConfig();
    public static MultiToolConfig multiToolConfig = new MultiToolConfig();
    public static RecipeEnabledConfig recipeEnabledConfig = new RecipeEnabledConfig();
    public static DevelopmentConfig developmentConfig = new DevelopmentConfig();

}
