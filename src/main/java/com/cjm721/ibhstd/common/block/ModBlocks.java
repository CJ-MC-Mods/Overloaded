package com.cjm721.ibhstd.common.block;

import com.cjm721.ibhstd.common.block.compressed.CompressedBlock;
import com.cjm721.ibhstd.common.block.compressed.CompressedBlockHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Map;

/**
 * Created by CJ on 4/2/2017.
 */
public final class ModBlocks {
    public static Map<Integer,Block> compressedCobbleStone;
    public static Map<Integer,Block> compressedSand;
    public static Map<Integer,Block> compressedStone;


    public static void init() {
        compressedCobbleStone = CompressedBlockHandler.CreateCompressedBlocks(Blocks.COBBLESTONE, 8);
        compressedSand = CompressedBlockHandler.CreateCompressedBlocks(Blocks.SAND, 8);
        compressedStone = CompressedBlockHandler.CreateCompressedBlocks(Blocks.STONE, 8);
    }

    public static void addRecipes() {
        for(Block b: compressedCobbleStone.values()){
            if(b instanceof CompressedBlock) {
                ((CompressedBlock)b).registerRecipe();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        registerModels(compressedCobbleStone.values());
        registerModels(compressedSand.values());
        registerModels(compressedStone.values());
    }

    private static void registerModels(Collection<Block> blockList) {
        for(Block b: blockList){
            if(b instanceof CompressedBlock) {
                ((CompressedBlock)b).registerModel();
            }
        }
    }
}
