package com.cjm721.ibhstd.common.block;

import com.cjm721.ibhstd.common.block.compressed.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.Map;

/**
 * Created by CJ on 4/2/2017.
 */
public final class ModBlocks {
    public static Map<Integer,CompressedBlock> compressedCobbleStone;


    public static void init() {
        compressedCobbleStone = CompressedBlockHandler.CreateCompressedBlocks(Blocks.COBBLESTONE, 8);
    }

    public static void addRecipes() {
        for(CompressedBlock b: compressedCobbleStone.values()){
            b.registerRecipe();
        }
    }
}
