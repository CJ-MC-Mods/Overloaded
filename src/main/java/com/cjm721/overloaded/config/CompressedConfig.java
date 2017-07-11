package com.cjm721.overloaded.config;

import net.minecraftforge.common.config.Config;

public class CompressedConfig {

    @Config.Comment({"Put compressed blocks here in the form of <modid>:<blockname>:<compression amount>:<recipe enabled>"})
    @Config.RequiresMcRestart
    public String[] compressedBlocks = new String[] {
    };

    @Config.RequiresMcRestart
    @Config.Comment("Maximum Texture Resolution of a single block, WARNING setting this to high can break your game's textures / cause you to crash. Higher number means more memory usage [Default: 512]")
    public int maxTextureWidth = 512;
}
