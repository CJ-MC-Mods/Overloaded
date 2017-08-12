package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class CompressedConfig {

    @Config.Comment({"Put compressed blocks here in the form of <modid>:<blockname>:<compression amount>:<hardness multiplier per level>:<recipe enabled>"})
    @Config.RequiresMcRestart
    public String[] compressedBlocks = new String[] {
            "minecraft:cobblestone:16:9:true",
            "minecraft:sand:4:9:true",
            "minecraft:stone:8:9:true",
            "minecraft:obsidian:8:9:true",
            "minecraft:netherrack:4:9:true",
            "minecraft:dirt:8:9:true",
            "minecraft:gravel:8:9:true"
    };

    @Config.RequiresMcRestart
    @Config.Comment("Maximum Texture Resolution of a single block, WARNING setting this to high can break your game's textures / cause you to crash. Higher number means more memory usage [Default: 512]")
    public int maxTextureWidth = 512;

    @Config.Comment("Show the hardness of compressed blocks when in item form. [Default: true")
    public boolean showHardness = true;
}
