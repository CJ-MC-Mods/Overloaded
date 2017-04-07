package com.cjm721.ibhstd.common.block.basic;

import com.cjm721.ibhstd.common.block.ModBlocks;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by CJ on 4/7/2017.
 */
public class BlockGrill extends ModBlocks implements ITileEntityProvider {
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
