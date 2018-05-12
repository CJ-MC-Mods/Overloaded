package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class ResolutionConfig {
    @Config.Comment("Resolution for Blocks. [Default: 256]")
    public int blockResolution = 256;
    @Config.Comment("Resolution for Multi-Armor. [Default: 256]")
    public int multiArmorResolution = 256;
}
