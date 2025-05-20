package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.ModBlockContainer;
import com.cjm721.overloaded.tile.functional.TileInstantFurnace;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockInstantFurnace extends ModBlockContainer {
  private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

  public BlockInstantFurnace(Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
  }

  public static final MapCodec<BlockInstantFurnace> CODEC = simpleCodec(BlockInstantFurnace::new);

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }

  @Override
  protected void createBlockStateDefinition(
      StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
    builder.add(FACING);
    super.createBlockStateDefinition(builder);
  }

  @Override
  protected InteractionResult useWithoutItem(
      BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
    if (!level.isClientSide) {
      player.openMenu(state.getMenuProvider(level, pos));
      return InteractionResult.CONSUME;
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  @Nonnull
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    return this.defaultBlockState()
        .setValue(FACING, context.getHorizontalDirection().getOpposite());
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileInstantFurnace(pos, state);
  }

  @Override
  public void onRemove(
      BlockState oldState, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
    if (oldState.is(newState.getBlock())) {
      BlockEntity te = world.getBlockEntity(pos);

      if (te instanceof TileInstantFurnace) {
        if (world instanceof ServerLevel) {
          Containers.dropContents(world, pos, ((TileInstantFurnace) te));
        }
        super.onRemove(oldState, world, pos, newState, isMoving);
        world.invalidateCapabilities(pos);
      } else {
        super.onRemove(oldState, world, pos, newState, isMoving);
      }
    }
  }

  @Override
  protected BlockState rotate(BlockState state, Rotation rot) {
    return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
  }

  @Override
  @Nonnull
  public BlockState mirror(@Nonnull BlockState state, Mirror mirror) {
    return state.rotate(mirror.getRotation(state.getValue(FACING)));
  }

  @Override
  protected RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }
}
