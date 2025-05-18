package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.fml.network.NetworkRegistry;

import javax.annotation.Nonnull;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class AbstractBlockHyperReceiver extends AbstractBlockHyperNode {

  protected AbstractBlockHyperReceiver(@Nonnull Properties materialIn) {
    super(materialIn);
  }


  @Override
  protected InteractionResult useItemOn(ItemStack heldItem, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {
    if (heldItem.getItem().equals(ModItems.linkingCard)) {
      CompoundNBT tag = heldItem.getTag();
      if (tag == null) {
        tag = new CompoundNBT();
      }


      ResourceLocation worldId = world.dimension().location();
      writeNodeData(tag, worldId, pos);
      heldItem.setTag(tag);

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

  private void writeNodeData(@Nonnull CompoundNBT tag, ResourceLocation worldId, @Nonnull BlockPos pos) {
    tag.putInt("X", pos.getX());
    tag.putInt("Y", pos.getY());
    tag.putInt("Z", pos.getZ());
    tag.putString("WORLD", worldId.toString());
    tag.putString("TYPE", getType());
  }
}
