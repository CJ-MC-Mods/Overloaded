package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

import static com.cjm721.overloaded.Overloaded.MODID;

@Config(modid = MODID, category = "")
public class ForgeOverloadedConfigHolder {
    @Config.Name("general")
    public static OverloadedConfig overloadedConfig = new OverloadedConfig();
}
