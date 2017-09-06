package com.cjm721.overloaded.config.compressed;

public class CompressedEntry {
    public String baseRegistryName;
    public String compressedPathRegistryName;
    public int meta;
    public String texturePath;
    public int depth;
    public float hardnessMultiplier;
    public boolean recipeEnabled;

    // Required as used by GSON
    public CompressedEntry() {
    }

    public CompressedEntry(String baseRegistryName, String compressedPathRegistryName, int meta, String texturePath, int depth, float hardnessMultiplier, boolean recipeEnabled) {
        this.baseRegistryName = baseRegistryName;
        this.compressedPathRegistryName = compressedPathRegistryName;
        this.meta = meta;
        this.texturePath = texturePath;
        this.depth = depth;
        this.hardnessMultiplier = hardnessMultiplier;
        this.recipeEnabled = recipeEnabled;
    }
}
