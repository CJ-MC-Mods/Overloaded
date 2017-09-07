package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class SpecialConfig {
    @Config.RequiresMcRestart
    @Config.Comment("Fix for rending while noClip is active. May cause issues with some other mods. [Default: true]")
    public boolean noClipRenderFix = true;
}
