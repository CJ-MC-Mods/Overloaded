package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.tile.functional.TileTeamLoader;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockTeamLoader extends ModBlock {

    public BlockTeamLoader() {
        super(getDefaultProperties());
        setRegistryName("team_loader");
    }

    @Override
    public void registerModel() { }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileTeamLoader();
    }
}
