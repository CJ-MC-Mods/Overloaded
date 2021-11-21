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
  private static final DirectionProperty FACING = HorizontalBlock.FACING;

  public BlockInstantFurnace() {
    super(ModBlock.getDefaultProperties());
    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    setRegistryName("instant_furnace");
  }

  @Override
  protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
    p_206840_1_.add(FACING);
    super.createBlockStateDefinition(p_206840_1_);
  }

  @Override
  @Nonnull
  public ActionResultType use(
      BlockState state,
      World worldIn,
      BlockPos pos,
      PlayerEntity player,
      Hand handIn,
      BlockRayTraceResult hit) {
    player.openMenu(state.getMenuProvider(worldIn, pos));
    return ActionResultType.SUCCESS;
  }

  @Override
  @Nonnull
  public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
    return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite());
  }

  @Nonnull
  @Override
  public BlockRenderType getRenderShape(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Nullable
  @Override
  public TileEntity newBlockEntity(IBlockReader worldIn) {
    return new TileInstantFurnace();
  }

  @Override
  public void onRemove(BlockState oldState, World world, BlockPos pos, BlockState newState, boolean isMoving) {
    if(oldState.getBlock() != newState.getBlock()) {
      TileEntity te = world.getBlockEntity(pos);

      if (te instanceof TileInstantFurnace) {
        InventoryHelper.dropContents(world, pos, ((TileInstantFurnace) te));
      }
    }

    super.onRemove(oldState, world, pos, newState, isMoving);
  }

  @Override
  @Nonnull
  public BlockState rotate(@Nonnull BlockState state, Rotation p_185499_2_) {
    return state.setValue(FACING, p_185499_2_.rotate(state.getValue(FACING)));
  }

  @Override
  @Nonnull
  public BlockState mirror(@Nonnull BlockState state, Mirror p_185471_2_) {
    return state.rotate(p_185471_2_.getRotation(state.getValue(FACING)));
  }
}
