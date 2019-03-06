package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class SpecialConfig {
    @Config.RequiresMcRestart
    @Config.Comment("Fix for rending while noClip is active. May cause issues with some other mods. [Default: true]")
    public boolean noClipRenderFix = true;

    @Config.Comment("Do not change without reading https://github.com/CJ-MC-Mods/Overloaded/wiki/Minecraft-Inventory-Mechanic-Bugs#slots-for-infinity-barrel. Reduces performance for compatibility. [Default:: false]")
    public boolean infinityBarrelAdditionalSlot = false;
}
