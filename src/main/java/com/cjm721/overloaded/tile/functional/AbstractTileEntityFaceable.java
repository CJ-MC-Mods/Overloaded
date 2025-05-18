package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.block.basic.AbstractModBlockFacing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

abstract class AbstractTileEntityFaceable extends BlockEntity {

  AbstractTileEntityFaceable(BlockEntityType<?> te, BlockPos pos, BlockState blockState) {
    super(te, pos, blockState);
  }

  Direction getFacing() {
    return this.getBlockState().getValue(AbstractModBlockFacing.FACING);
  }
}
