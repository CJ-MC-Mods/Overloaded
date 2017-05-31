package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class DevelopmentConfig {

    @Config.RequiresMcRestart
    @Config.Comment({"Stuff not deemed to be safe yet. IE World Corrupting / Crashing. [Default: false]"})
    public boolean wipStuff = false;
}
