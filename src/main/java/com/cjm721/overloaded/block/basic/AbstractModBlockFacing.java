package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class AbstractModBlockFacing extends ModBlock {
  public static final DirectionProperty FACING = BlockStateProperties.FACING;

  AbstractModBlockFacing(@Nonnull Properties materialIn) {
    super(materialIn);

    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection());
  }

  @Override
  public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
    return state.setValue(FACING,  direction.rotate(state.getValue(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, Mirror mirrorIn) {
    return state.setValue(FACING, mirrorIn.mirror(state.getValue(FACING)));
  }

  @Override
  protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
  {
    builder.add(FACING);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
  }
}
