package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public enum CompressedConfig implements IConfig {
    I;

    private static final String category = "compressedBlocks";

    public static String[] compressedBlocks;

    @Override
    public void init(@Nonnull Configuration config) {
        config.addCustomCategoryComment(category, "Register Compressed Blocks Here");

        compressedBlocks = config.get(category, "toCompress", new String[] {
                "minecraft:cobblestone:8",
                "minecraft:sand:8",
                "minecraft:stone:8"
        }, "Put compressed blocks here in the form of <modid>:<blockname>:<compression amount>", Pattern.compile(".+:.+:\\d+")).getStringList();
    }
}
