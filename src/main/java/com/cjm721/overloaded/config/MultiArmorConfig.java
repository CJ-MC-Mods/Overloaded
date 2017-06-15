package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class MultiArmorConfig {

    @Config.Comment("Base cost of any defensive action. [Default: 10]")
    public int baseCost = 10;
    @Config.Comment("How much energy is used base per damage. [Default: 100]")
    public float damageMultiplier = 100;
    @Config.Comment("Energy Multiplier for Absolute Damage. [Default: 10]")
    public float absoluteDamageMultiplier = 10;
    @Config.Comment("Energy Multiplier for Unblockable Damage. [Default: 10]")
    public float unblockableMultiplier = 10;

    @Config.Comment("Energy used per tick while flying. [Default:10]")
    public int energyPerTickFlying = 10;
    @Config.Comment("Max level to feed up too. [Default: 20]")
    public int maxFoodLevel = 20;
    @Config.Comment("Energy Cost per food amount to fill. [Default: 1000]")
    public int costPerFood = 1000;
    @Config.Comment("Energy Cost to remove a potion effect. [Default: 10000]")
    public int removeEffect = 10000;
    @Config.Comment("Energy Cost per health amount to heal. [Default: 5000]")
    public int costPerHealth = 5000;
}
