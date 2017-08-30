package com.cjm721.overloaded.block.compressed;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.config.compressed.CompressedEntry;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CompressedBlockHandler {

    public static BlockCompressed CreateCompressedBlock(CompressedEntry entry) {
        return new BlockCompressed(entry.compressedPathRegistryName, entry.compressedPathRegistryName, entry);
    }

    public static List<BlockCompressed> initFromConfig() throws IOException {
        List<BlockCompressed> compressedBlocks = new LinkedList<>();

        for (CompressedEntry entry: OverloadedConfig.compressedConfig.getCompressedEntries()) {
            BlockCompressed compressedBlock = CompressedBlockHandler.CreateCompressedBlock(entry);
            compressedBlocks.add(compressedBlock);
        }
        return compressedBlocks;
    }
}
