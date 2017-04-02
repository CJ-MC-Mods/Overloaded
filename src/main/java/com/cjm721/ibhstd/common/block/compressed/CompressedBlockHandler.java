package com.cjm721.ibhstd.common.block.compressed;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJ on 4/2/2017.
 */
public class CompressedBlockHandler {

    public static Map<Integer, CompressedBlock> CreateCompressedBlocks(Block toCompress, int depth) {
        Map<Integer, CompressedBlock> compressedBlocks = new HashMap<Integer, CompressedBlock>();


        Material material = toCompress.getDefaultState().getMaterial();
        String registryName = toCompress.getRegistryName().getResourceDomain();
        String unlocalizedName = toCompress.getUnlocalizedName();

        float hardness = toCompress.getDefaultState().getBlockHardness(null,null);
        String harvestTool = toCompress.getHarvestTool(toCompress.getDefaultState());
        int harvestLevel = toCompress.getHarvestLevel(toCompress.getDefaultState());


        Block previousLevel = toCompress;
        for(int i = 0; i < depth; i++) {
            String compRegistryName = String.format("compressed%d%s", i+1, registryName);
            String compUnlocalizedName = String.format("%dx Compressed %s", i+1, unlocalizedName);
            CompressedBlock block = new CompressedBlock(previousLevel, material,compRegistryName , compUnlocalizedName, hardness, harvestTool, harvestLevel);
            previousLevel = block;
            compressedBlocks.put(i, block);
        }
        return compressedBlocks;
    }

}
