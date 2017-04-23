package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public enum CompressedConfig implements IConfig {
    I;

    private static final String category = "compressedBlocks";

    public static String[] compressedBlocks;
    public static int maxTextureWidth;


    @Override
    public void init(@Nonnull Configuration config) {
        config.addCustomCategoryComment(category, "Register Compressed Blocks and their settings here");

        compressedBlocks = config.get(category, "toCompress", new String[] {
                "minecraft:cobblestone:16:true",
                "minecraft:sand:16:true",
                "minecraft:stone:16:true",
                "minecraft:obsidian:16:true",
                "minecraft:netherrack:16:true",
                "minecraft:dirt:16:true",
                "minecraft:gravel:16:true"
        }, "Put compressed blocks here in the form of <modid>:<blockname>:<compression amount>:<recipe enabled>", Pattern.compile(".+:.+:\\d+:.+")).getStringList();

        maxTextureWidth = config.get(category, "maxTextureWidth", 512, "Maximum Texture Resolution of a single block, WARNING setting this to high can break your game's textures / cause you to crash. Higher number also means more memory usage").getInt();


    }
}
