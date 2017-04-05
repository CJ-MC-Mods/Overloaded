package com.cjm721.ibhstd.common.block;

import com.cjm721.ibhstd.common.block.basic.BlockCreativeGenerator;
import com.cjm721.ibhstd.common.block.basic.BlockExampleTileEntity;
import com.cjm721.ibhstd.common.block.compressed.BlockCompressed;
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
    public static Map<Integer,Block> compressedFurnace;

    public static ModBlock exampleTileEntity;
    public static ModBlock basicGenerator;


    public static void init() {
        compressedCobbleStone = CompressedBlockHandler.CreateCompressedBlocks(Blocks.COBBLESTONE, 8);
        compressedSand = CompressedBlockHandler.CreateCompressedBlocks(Blocks.SAND, 8);
        compressedStone = CompressedBlockHandler.CreateCompressedBlocks(Blocks.STONE, 8);
        compressedFurnace = CompressedBlockHandler.CreateCompressedBlocks(Blocks.FURNACE, 8);

        exampleTileEntity = new BlockExampleTileEntity();
        basicGenerator = new BlockCreativeGenerator();
    }

    public static void addRecipes() {
        for(Block b: compressedCobbleStone.values()){
            if(b instanceof ModBlock) {
                ((ModBlock)b).registerRecipe();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        registerModels(compressedCobbleStone.values());
        registerModels(compressedSand.values());
        registerModels(compressedStone.values());
        registerModels(compressedFurnace.values());
    }

    @SideOnly(Side.CLIENT)
    private static void registerModels(Collection<Block> blockList) {
        for(Block b: blockList){
            if(b instanceof BlockCompressed) {
                ((BlockCompressed)b).registerModel();
            }
        }
    }
}
