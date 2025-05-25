package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.functional.TileEnergyExtractor;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public abstract class AbstractBlockHyperSender extends AbstractBlockHyperNode {

  protected AbstractBlockHyperSender(@Nonnull Properties materialIn) {
    super(materialIn);
  }

  @Override
  protected ItemInteractionResult useItemOn(
      ItemStack stack,
      BlockState state,
      Level world,
      BlockPos pos,
      Player player,
      InteractionHand hand,
      BlockHitResult hitResult) {
    if (hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).isEmpty()) {
      if (!world.isClientSide) {
        String message =
            ((AbstractTileHyperSender) world.getBlockEntity(pos)).getRightClickMessage();
        player.displayClientMessage(Component.literal(message), false);
      }
      return ItemInteractionResult.SUCCESS;
    }

    return super.useItemOn(stack, state, world, pos, player, hand, hitResult);
  }

  @Override
  public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
      Level level, BlockState state, BlockEntityType<T> blockEntityType) {
    return state.getBlock() instanceof AbstractBlockHyperSender
        ? AbstractTileHyperSender::tick
        : null;
  }
}
