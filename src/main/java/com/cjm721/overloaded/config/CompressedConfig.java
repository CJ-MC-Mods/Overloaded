package com.cjm721.overloaded.config;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.compressed.CompressedEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.common.config.Config;

import java.io.*;

public class CompressedConfig {

    @Config.RequiresMcRestart
    @Config.Comment("Maximum Texture Resolution of a single block, WARNING setting this to high can break your game's textures / cause you to crash. Higher number means more memory usage [Default: 256]")
    public int maxTextureWidth = 256;

    @Config.Comment("Show the hardness of compressed blocks when in item form. [Default: true")
    public boolean showHardness = true;

    private static final CompressedEntry[] defaults = new CompressedEntry[]{
            new CompressedEntry("minecraft:cobblestone", "compressed_cobblestone", 0, "minecraft:textures/blocks/cobblestone.png", 16, 9.0f, true),
            new CompressedEntry("minecraft:sand", "compressed_sand", 0, "minecraft:textures/blocks/sand.png", 16, 9.0f, true),
            new CompressedEntry("minecraft:stone", "compressed_stone", 0, "minecraft:textures/blocks/stone.png", 16, 9.0f, true),
            new CompressedEntry("minecraft:obsidian", "compressed_obsidian", 0, "minecraft:textures/blocks/obsidian.png", 16, 9.0f, true),
            new CompressedEntry("minecraft:netherrack", "compressed_netherrack", 0, "minecraft:textures/blocks/cobblestone.png", 16, 9.0f, true),
            new CompressedEntry("minecraft:dirt", "compressed_dirt", 0, "minecraft:textures/blocks/dirt.png", 16, 9.0f, true),
            new CompressedEntry("minecraft:gravel", "compressed_gravel", 0, "minecraft:textures/blocks/gravel.png", 16, 9.0f, true)
    };

    public CompressedEntry[] getCompressedEntries() throws IOException {

        File file = new File(Overloaded.configFolder, "compressed.json");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (!file.exists()) {
            String json = gson.toJson(defaults, defaults.getClass());
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.flush();
            writer.close();
            return defaults;
        }

        try {
            return gson.fromJson(new FileReader(file), CompressedEntry[].class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Impossible Exception, file existed moments ago", e);
        }
    }
}
