package com.cjm721.ibhstd.common.block.compressed;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJ on 4/2/2017.
 */
public class CompressedBlockHandler {

    public static Map<Integer, Block> CreateCompressedBlocks(Block toCompress, int depth) {
        Map<Integer, Block> compressedBlocks = new HashMap<>();


        Material material = toCompress.getDefaultState().getMaterial();
        String registryName = toCompress.getRegistryName().getResourcePath();
        String unlocalizedName = toCompress.getUnlocalizedName();

        float hardness = toCompress.getDefaultState().getBlockHardness(null,null);
        String harvestTool = toCompress.getHarvestTool(toCompress.getDefaultState());
        int harvestLevel = toCompress.getHarvestLevel(toCompress.getDefaultState());


        compressedBlocks.put(0, toCompress);
        Block previousLevel = toCompress;
        for(int i = 1; i <= depth; i++) {
            String compRegistryName = String.format("compressed%d%s", i, registryName);
            String compUnlocalizedName = String.format("%dxCompressed:%s", i, unlocalizedName);
            hardness *= 9;
            if(hardness < 0) {
                hardness = Float.MAX_VALUE;
            }
            CompressedBlock block = new CompressedBlock(previousLevel, material,compRegistryName , compUnlocalizedName, hardness, harvestTool, harvestLevel);
            previousLevel = block;
            compressedBlocks.put(i, block);
        }
        return compressedBlocks;
    }

}
