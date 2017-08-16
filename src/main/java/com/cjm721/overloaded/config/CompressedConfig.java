package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class CompressedConfig {

    @Config.Comment({"WARNING: These must match the server config if you wish to play MP. Put compressed blocks here in the form of <modid>:<blockname>:<compression amount>:<hardness multiplier per level>:<recipe enabled>."})
    @Config.RequiresMcRestart
    public String[] compressedBlocks = new String[]{
            "minecraft:cobblestone:16:9:true",
            "minecraft:sand:16:9:true",
            "minecraft:stone:16:9:true",
            "minecraft:obsidian:16:9:true",
            "minecraft:netherrack:16:9:true",
            "minecraft:dirt:16:9:true",
            "minecraft:gravel:16:9:true"
    };

    @Config.RequiresMcRestart
    @Config.Comment("Maximum Texture Resolution of a single block, WARNING setting this to high can break your game's textures / cause you to crash. Higher number means more memory usage [Default: 256]")
    public int maxTextureWidth = 256;

    @Config.Comment("Show the hardness of compressed blocks when in item form. [Default: true")
    public boolean showHardness = true;
}
