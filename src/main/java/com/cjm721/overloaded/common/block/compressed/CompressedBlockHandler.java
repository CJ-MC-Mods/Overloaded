package com.cjm721.overloaded.common.block.compressed;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.HashMap;
import java.util.Map;

public class CompressedBlockHandler {

    public static Map<Integer, Block> CreateCompressedBlocks(Block toCompress, int depth) {
        Map<Integer, Block> compressedBlocks = new HashMap<>();

        Material material = toCompress.getDefaultState().getMaterial();
        String registryName = toCompress.getRegistryName().getResourcePath();
        String unlocalizedName = toCompress.getUnlocalizedName();

        float baseHardness = toCompress.getDefaultState().getBlockHardness(null,null);
        String harvestTool = toCompress.getHarvestTool(toCompress.getDefaultState());
        int harvestLevel = toCompress.getHarvestLevel(toCompress.getDefaultState());


        compressedBlocks.put(0, toCompress);
        Block previousLevel = toCompress;
        float currentHardness = baseHardness;
        for(int i = 1; i <= depth; i++) {
            String compRegistryName = String.format("compressed%d%s", i, registryName);
            String compUnlocalizedName = String.format("%dxCompressed:%s", i, unlocalizedName);
            currentHardness *= 9;
            if(currentHardness < 0) {
                currentHardness = Float.MAX_VALUE;
            }
            BlockCompressed block = new BlockCompressed(toCompress, previousLevel, i, material,compRegistryName , compUnlocalizedName, currentHardness, harvestTool, harvestLevel);
            previousLevel = block;
            compressedBlocks.put(i, block);
        }
        return compressedBlocks;
    }

}
