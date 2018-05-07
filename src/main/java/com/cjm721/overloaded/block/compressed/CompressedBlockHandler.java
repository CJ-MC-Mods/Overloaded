package com.cjm721.overloaded.block.compressed;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.config.compressed.CompressedEntry;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CompressedBlockHandler {

    private static BlockCompressed CreateCompressedBlock(CompressedEntry entry) {
        return new BlockCompressed(entry.compressedPathRegistryName, entry.compressedPathRegistryName, entry);
    }

    public static List<BlockCompressed> initFromConfig() throws IOException {
        List<BlockCompressed> compressedBlocks = new LinkedList<>();

        for (CompressedEntry entry : OverloadedConfig.compressedConfig.getCompressedEntries()) {
            BlockCompressed compressedBlock = CompressedBlockHandler.CreateCompressedBlock(entry);
            compressedBlocks.add(compressedBlock);
        }
        return compressedBlocks;
    }
}
