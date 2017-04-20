package com.cjm721.overloaded.client.render.block.compressed;

import net.minecraft.util.ResourceLocation;

public class CompressedBlockAssets {

    public String getBlockState(ResourceLocation location) {
        return String.format("{ \"variants\": { \"normal\": { \"model\": \"block/cube_all\"  \"textures\": { \"all\": \"%1$s\" }} }}", location.toString());
    }
}
