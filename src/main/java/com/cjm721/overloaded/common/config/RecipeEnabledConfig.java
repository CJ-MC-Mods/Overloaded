package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nonnull;

public class RecipeEnabledConfig {

    @Config.RequiresMcRestart
    public boolean multiTool = true;
    @Config.RequiresMcRestart
    public boolean infiniteWaterSource = true;
    @Config.RequiresMcRestart
    @Config.Comment({"If set to false will override any settings in the Compressed Settings Section"})
    public boolean compressedBlocks = true;
    @Config.RequiresMcRestart
    public boolean energyCore = true;
    @Config.RequiresMcRestart
    public boolean fluidCore = true;
    @Config.RequiresMcRestart
    public boolean itemCore = true;

    @Config.RequiresMcRestart
    public boolean infinityBarrel = true;
    @Config.RequiresMcRestart
    public boolean infinityCapacitor = true;
    @Config.RequiresMcRestart
    public boolean infinityTank = true;
    @Config.RequiresMcRestart
    public boolean netherStarBlock = true;
    @Config.RequiresMcRestart
    public boolean linkingCard = true;
    @Config.RequiresMcRestart
    public boolean energyExtractor = true;
    @Config.RequiresMcRestart
    public boolean hyperEnergyNodes = true;
    @Config.RequiresMcRestart
    public boolean hyperFluidNodes = true;
    @Config.RequiresMcRestart
    public boolean hyperItemNodes = true;
    @Config.RequiresMcRestart
    public boolean playerInterface = true;
    @Config.RequiresMcRestart
    public boolean itemInterface = true;
    @Config.RequiresMcRestart
    public boolean itemManipulator = true;
    @Config.RequiresMcRestart
    public boolean customHelmet = true;
    @Config.RequiresMcRestart
    public boolean customChestplate = true;
    @Config.RequiresMcRestart
    public boolean customLeggings = true;
    @Config.RequiresMcRestart
    public boolean customBoots = true;
}
