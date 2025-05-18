package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlockTile;
import com.cjm721.overloaded.tile.functional.TileTeamLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockTeamLoader extends ModBlockTile {

    public BlockTeamLoader() {
        super(getDefaultProperties());
    }

    @Override
    public void registerModel() { }

    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileTeamLoader(pos,state);
    }
}
