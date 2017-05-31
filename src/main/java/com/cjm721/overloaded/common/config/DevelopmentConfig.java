package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nonnull;

public class DevelopmentConfig {

    @Config.RequiresMcRestart
    @Config.Comment({"Stuff not deemed to be safe yet. IE World Corrupting / Crashing. [Default: false]"})
    public boolean wipStuff = false;
}
