package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class RailGunConfig {

    @Config.Comment("Minimum energy used per shot. [Default: 1,000,000]")
    public int minEngery = 10000000;
    @Config.Comment("Maximum energy used per shot. [Default: 2,000,000,000]")
    public int maxEnergy = 2000000000;
    @Config.Comment("Energy change step (via scroll wheel). [Default: 10,000,000]")
    public int stepEnergy = 10000000;
    @Config.Comment("Max range to shoot. [Default: 128]")
    public int maxRange = 128;
    @Config.Comment("Damage per RF(FE) spent. [Default: 0.00001")
    public float damagePerRF = 0.00001F;
    @Config.Comment("Distance knockback per RF(FE) spent. [Default: 0.000001")
    public float knockbackPerRF = 0.000001F;
}
