package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.ModBlockContainer;
import com.cjm721.overloaded.tile.functional.TileInstantFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockInstantFurnace extends ModBlockContainer {
  private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

  public BlockInstantFurnace() {
    super(ModBlock.getDefaultProperties());
    this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    setRegistryName("instant_furnace");
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
    p_206840_1_.add(FACING);
    super.fillStateContainer(p_206840_1_);
  }

  @Override
  @Nonnull
  public ActionResultType onBlockActivated(
      BlockState state,
      World worldIn,
      BlockPos pos,
      PlayerEntity player,
      Hand handIn,
      BlockRayTraceResult hit) {
    player.openContainer(state.getContainer(worldIn, pos));
    return ActionResultType.SUCCESS;
  }

  @Override
  @Nonnull
  public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
    return this.getDefaultState().with(FACING, p_196258_1_.getPlacementHorizontalFacing().getOpposite());
  }

  @Nonnull
  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(IBlockReader worldIn) {
    return new TileInstantFurnace();
  }

  @Override
  public void onReplaced(BlockState oldState, World world, BlockPos pos, BlockState newState, boolean isMoving) {
    if(oldState.getBlock() != newState.getBlock()) {
      TileEntity te = world.getTileEntity(pos);

      if (te instanceof TileInstantFurnace) {
        InventoryHelper.dropInventoryItems(world, pos, ((TileInstantFurnace) te));
      }
    }

    super.onReplaced(oldState, world, pos, newState, isMoving);
  }

  @Override
  @Nonnull
  public BlockState rotate(@Nonnull BlockState state, Rotation p_185499_2_) {
    return state.with(FACING, p_185499_2_.rotate(state.get(FACING)));
  }

  @Override
  @Nonnull
  public BlockState mirror(@Nonnull BlockState state, Mirror p_185471_2_) {
    return state.rotate(p_185471_2_.toRotation(state.get(FACING)));
  }
}
