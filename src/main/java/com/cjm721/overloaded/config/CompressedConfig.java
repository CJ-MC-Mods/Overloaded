package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class CompressedConfig {

    @Config.Comment({"Put compressed blocks here in the form of <modid>:<blockname>:<compression amount>:<recipe enabled>"})
    @Config.RequiresMcRestart
    public String[] compressedBlocks = new String[] {
            "minecraft:cobblestone:8:true",
            "minecraft:sand:8:true",
            "minecraft:stone:8:true",
            "minecraft:obsidian:8:true",
            "minecraft:netherrack:8:true",
            "minecraft:dirt:8:true",
            "minecraft:gravel:8:true"
    };

    @Config.RequiresMcRestart
    @Config.Comment("Maximum Texture Resolution of a single block, WARNING setting this to high can break your game's textures / cause you to crash. Higher number means more memory usage [Default: 512]")
    public int maxTextureWidth = 512;
}
