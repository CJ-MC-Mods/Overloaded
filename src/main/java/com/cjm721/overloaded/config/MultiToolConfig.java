package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class MultiToolConfig {

    @Config.Comment({"Max range Multi-Tool can edit blocks [Default: 128]"})
    public int reach = 128;

    @Config.Comment({"Cost that is added on to every place [Default: 100]"})
    public int placeBaseCost = 100;

    @Config.Comment({"Cost per meter away [Default: 10]"})
    public int costPerMeterAway = 10;

    @Config.Comment({"Cost that is added on to every block break [Default: 100]"})
    public int breakBaseCost = 100;

    @Config.Comment({"Multiples the Hardness Cost by this. [Default: 1]"})
    public int breakCostMultiplier = 1;

    @Config.Comment({"0 - None, 1 Block Place Preview, 2 Block Break Preview, 3 Place/Break Preview. (2/3 WIP)[Default: 1]"})
    public int assistMode = 1;
}
