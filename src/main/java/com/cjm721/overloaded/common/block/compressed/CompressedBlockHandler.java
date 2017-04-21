package com.cjm721.overloaded.common.block.compressed;

import com.cjm721.overloaded.common.config.CompressedConfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class CompressedBlockHandler {

    public static Map<Integer, Block> CreateCompressedBlocks(@Nonnull Block toCompress, int depth) {
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

    public static void initFromConfig() {
        IForgeRegistry<Block> registry = GameRegistry.findRegistry(Block.class);

        for(String setting: CompressedConfig.compressedBlocks) {
            String[] split = setting.split(":");

            String domain = split[0];
            String blockName = split[1];
            int depth = Integer.parseInt(split[2]);

            Block block = registry.getValue(new ResourceLocation(domain,blockName));

            if(block == Blocks.AIR)
                continue;

            CompressedBlockHandler.CreateCompressedBlocks(block, depth);
        }

//        compressedCobbleStone = CompressedBlockHandler.CreateCompressedBlocks(Blocks.COBBLESTONE, 8);
//        compressedSand = CompressedBlockHandler.CreateCompressedBlocks(Blocks.SAND, 8);
//        compressedStone = CompressedBlockHandler.CreateCompressedBlocks(Blocks.STONE, 8);
//        compressedFurnace = CompressedBlockHandler.CreateCompressedBlocks(Blocks.FURNACE, 8);

    }
}
