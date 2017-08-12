package com.cjm721.overloaded.block.compressed;

import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.crash.CrashReport;
import net.minecraft.init.Blocks;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class CompressedBlockHandler {

    public static Map<Integer, Block> CreateCompressedBlocks(@Nonnull Block toCompress, int depth, boolean recipeEnabled) {
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
            String compRegistryName = String.format("compressed%s%d", registryName, i);
            String compUnlocalizedName = String.format("%dxCompressed:%s", i, unlocalizedName);
            currentHardness *= 9;
            if(currentHardness < 0) {
                currentHardness = Float.MAX_VALUE;
            }
//            BlockCompressed block = new BlockCompressed(toCompress, previousLevel, i, material,compRegistryName , compUnlocalizedName, currentHardness, harvestTool, harvestLevel, recipeEnabled);
//            previousLevel = block;
//            compressedBlocks.put(i, block);
        }
        return compressedBlocks;
    }

    public static void initFromConfig() {
        IForgeRegistry<Block> registry = GameRegistry.findRegistry(Block.class);

//        for(String setting: OverloadedConfig.compressedConfig.compressedBlocks) {
//            if(setting.isEmpty())
//                continue;
//            String[] split = setting.split(":");
//
//            if(split.length < 4) {
//                if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
//                    throwClientSideError(setting);
//                } else {
//                    throw new ReportedException(CrashReport.makeCrashReport(new RuntimeException("Compressed Blocks Config is invalid. Looking at compressed block: " + setting), "Invalid Compressed Block Config"));
//                }
//            }
//
//            String domain = split[0];
//            String blockName = split[1];
//            int depth = Integer.parseInt(split[2]);
//            boolean recipeEnabled = Boolean.parseBoolean(split[3]);
//
//            Block block = registry.getValue(new ResourceLocation(domain,blockName));
//
//            if(block == Blocks.AIR)
//                continue;
//
//            CompressedBlockHandler.CreateCompressedBlocks(block, depth, recipeEnabled);
//        }
    }

    @SideOnly(Side.CLIENT)
    private static void throwClientSideError(String setting) {
        throw new CustomModLoadingErrorDisplayException() {
            @Override
            public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {
            }

            @SideOnly(Side.CLIENT)
            @Override
            public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime) {
                errorScreen.drawString(fontRenderer, "Compressed Blocks Config is invalid. Looking at compressed block: " + setting, errorScreen.width/2, errorScreen.width/2, 0);
            }
        };
    }
}
