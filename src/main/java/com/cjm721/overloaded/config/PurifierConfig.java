package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class PurifierConfig {

    @Config.Comment("Energy Per Operation Base. [Default: 10000]")
    public int energyPerOperation = 10000;
    @Config.Comment("Energy Per Hardness of Block. [Default: 1000]")
    public int energyPerHardness = 1000;
}
