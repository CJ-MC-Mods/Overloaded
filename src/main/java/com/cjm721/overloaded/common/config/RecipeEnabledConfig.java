package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nonnull;

/**
 * Created by CJ on 4/20/2017.
 */
public enum RecipeEnabledConfig implements IConfig {
    I;

    private static String category = "Recipe Enabled";

    public static boolean multiTool;
    public static boolean infiniteWaterSource;
    public static boolean compressedBlocks;

    public static boolean energyCore;
    public static boolean fluidCore;
    public static boolean itemCore;

    public static boolean infinityBarrel;
    public static boolean infinityCapacitor;
    public static boolean infinityTank;
    public static boolean netherStarBlock;
    public static boolean linkingCard;
    public static boolean energyExtractor;
    public static boolean hyperEnergyNodes;
    public static boolean hyperFluidNodes;
    public static boolean hyperItemNodes;
    public static boolean playerInterface;

    @Override
    public void init(@Nonnull Configuration config) {
        config.addCustomCategoryComment(category, "Enabled Recipes");

        multiTool = config.get(category,"multiTool", true).getBoolean();
        infiniteWaterSource = config.get(category,"infiniteWaterSource", true).getBoolean();
        compressedBlocks = config.get(category, "compressedBlocks", true, "If set to false will override any settings in the Compressed Settings Section").getBoolean();

        energyCore = config.get(category,"energyCore", true).getBoolean();
        fluidCore = config.get(category, "fluidCore", true).getBoolean();
        itemCore = config.get(category, "itemCore", true).getBoolean();

        infinityBarrel = config.get(category, "infinityBarrel", true).getBoolean();
        infinityCapacitor = config.get(category, "infinityCapacitor", true).getBoolean();
        infinityTank = config.get(category, "infinityTank", true).getBoolean();

        netherStarBlock = config.get(category, "netherStarBlock", true).getBoolean();
        linkingCard = config.get(category, "linkingCard", true).getBoolean();
        energyExtractor = config.get(category, "energyExtractor", true).getBoolean();
        hyperEnergyNodes = config.get(category, "hyperEnergyNodes", true).getBoolean();
        hyperFluidNodes = config.get(category, "hyperFluidNodes", true).getBoolean();
        hyperItemNodes = config.get(category, "hyperItemNodes", true).getBoolean();
        playerInterface = config.get(category, "playerInterface", true).getBoolean();
    }
}
