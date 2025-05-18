package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;

public abstract class AbstractBlockHyperReceiver extends AbstractBlockHyperNode {

  protected AbstractBlockHyperReceiver(@Nonnull Properties materialIn) {
    super(materialIn);
  }


  @Override
  protected InteractionResult useItemOn(ItemStack heldItem, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {
    if (heldItem.getItem().equals(ModItems.linkingCard)) {
      CompoundTag tag = null;//heldItem.save();
      if (tag == null) {
        tag = new CompoundTag();
      }


      ResourceLocation worldId = world.dimension().location();
      writeNodeData(tag, worldId, pos);
//      heldItem.setTag(tag);

      if (world.isClientSide) {
        player.displayClientMessage(
            Component.literal(
                String.format("Recorded: World: %s Position: %s", worldId, pos.toShortString())),
            false);
      }

      return InteractionResult.CONSUME;
    } else {
      return super.useItemOn(heldItem,state, world, pos, player, hand, rayTraceResult);
    }
  }

  private void writeNodeData(@Nonnull CompoundTag tag, ResourceLocation worldId, @Nonnull BlockPos pos) {
    tag.putInt("X", pos.getX());
    tag.putInt("Y", pos.getY());
    tag.putInt("Z", pos.getZ());
    tag.putString("WORLD", worldId.toString());
    tag.putString("TYPE", getType());
  }
}
