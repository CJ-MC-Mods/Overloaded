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
import java.util.LinkedList;
import java.util.List;

public class CompressedBlockHandler {

    public static BlockCompressed CreateCompressedBlock(@Nonnull Block toCompress, int depth, float hardnessMultiplier, boolean recipeEnabled) {
        Material material = toCompress.getDefaultState().getMaterial();
        String registryName = toCompress.getRegistryName().getResourcePath();
        String unlocalizedName = toCompress.getUnlocalizedName();

        String harvestTool = toCompress.getHarvestTool(toCompress.getDefaultState());
        int harvestLevel = toCompress.getHarvestLevel(toCompress.getDefaultState());

        String compRegistryName = String.format("compressed_%s", registryName);
        String compUnlocalizedName = String.format("compressed.%s", unlocalizedName);


        return new BlockCompressed(toCompress, depth, material, compRegistryName, compUnlocalizedName, harvestTool, harvestLevel, hardnessMultiplier, recipeEnabled);
    }

    public static List<BlockCompressed> initFromConfig() {
        IForgeRegistry<Block> registry = GameRegistry.findRegistry(Block.class);

        List<BlockCompressed> compressedBlocks = new LinkedList<>();

        for (String setting : OverloadedConfig.compressedConfig.compressedBlocks) {
            if (setting.isEmpty())
                continue;
            String[] split = setting.split(":");

            if (split.length < 5) {
                if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
                    throwClientSideError(setting);
                } else {
                    throw new ReportedException(CrashReport.makeCrashReport(new RuntimeException("Compressed Blocks Config is invalid. Looking at compressed block: " + setting), "Invalid Compressed Block Config"));
                }
            }

            String domain = split[0];
            String blockName = split[1];
            int depth = Integer.parseInt(split[2]);
            float hardnessMultiplier = Float.parseFloat(split[3]);
            boolean recipeEnabled = Boolean.parseBoolean(split[4]);

            Block block = registry.getValue(new ResourceLocation(domain, blockName));

            if (block == null || block == Blocks.AIR)
                continue;

            BlockCompressed compressedBlock = CompressedBlockHandler.CreateCompressedBlock(block, depth, hardnessMultiplier, recipeEnabled);
            compressedBlocks.add(compressedBlock);
        }
        return compressedBlocks;
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
                errorScreen.drawString(fontRenderer, "Compressed Blocks Config is invalid. Looking at compressed block: " + setting, errorScreen.width / 2, errorScreen.width / 2, 0);
            }
        };
    }
}
