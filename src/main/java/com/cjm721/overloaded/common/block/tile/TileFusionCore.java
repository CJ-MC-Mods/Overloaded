package com.cjm721.overloaded.common.block.tile;

import net.minecraft.tileentity.TileEntity;

public class TileFusionCore extends TileEntity {
    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }
}
