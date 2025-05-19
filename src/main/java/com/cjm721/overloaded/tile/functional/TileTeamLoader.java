package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileTeamLoader extends BlockEntity {
  public TileTeamLoader(BlockPos pos, BlockState blockState) {
    super(ModTiles.teamLoader.get(), pos, blockState);
  }
}
