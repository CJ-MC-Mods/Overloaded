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

public abstract class AbstractModBlockFacing extends ModBlock {
  public static final DirectionProperty FACING = BlockStateProperties.FACING;

  AbstractModBlockFacing(@Nonnull Properties materialIn) {
    super(materialIn);

    this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    return this.getDefaultState().with(FACING, context.getNearestLookingDirection());
  }

  @Override
  public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
    return state.with(FACING,  direction.rotate(state.get(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, Mirror mirrorIn) {
    return state.with(FACING, mirrorIn.mirror(state.get(FACING)));
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
  {
    builder.add(FACING);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
  }
}
