package com.cjm721.ibhstd.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Created by CJ on 4/5/2017.
 */
public class TileInfiniteBarrel extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return false;
    }
}
