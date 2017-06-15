package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

import static com.cjm721.overloaded.Overloaded.MODID;

@Config(modid = MODID)
public class OverloadedConfig {

    @Config.Name("Compressed Blocks")
    public static CompressedConfig compressedConfig = new CompressedConfig();
    @Config.Name("Multi-Tool")
    public static MultiToolConfig multiToolConfig = new MultiToolConfig();
    @Config.Name("Multi-Armor")
    public static MultiArmorConfig multiArmorConfig = new MultiArmorConfig();
    @Config.Name("Recipes")
    public static RecipeEnabledConfig recipeEnabledConfig = new RecipeEnabledConfig();
    @Config.Name("Development")
    public static DevelopmentConfig developmentConfig = new DevelopmentConfig();

}
