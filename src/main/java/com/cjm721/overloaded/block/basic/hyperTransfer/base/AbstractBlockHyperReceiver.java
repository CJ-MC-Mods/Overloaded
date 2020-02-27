package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class AbstractBlockHyperReceiver extends AbstractBlockHyperNode {

  protected AbstractBlockHyperReceiver(@Nonnull Properties materialIn) {
    super(materialIn);
  }

  @Override
  @Nonnull
  public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
    ItemStack heldItem = player.getHeldItem(hand);
    if (heldItem.getItem().equals(ModItems.linkingCard)) {
      CompoundNBT tag = heldItem.getTag();
      if (tag == null) {
        tag = new CompoundNBT();
      }

      int worldId = world.getDimension().getType().getId();
      writeNodeData(tag, worldId, pos);
      heldItem.setTag(tag);

      if (world.isRemote) {
        player.sendStatusMessage(
            new StringTextComponent(
                String.format("Recorded: World: %d Position: %s", worldId, pos.toString())),
            false);
      }

      return ActionResultType.CONSUME;
    } else {
      return super.onBlockActivated(state, world, pos, player, hand, rayTraceResult);
    }
  }

  private void writeNodeData(@Nonnull CompoundNBT tag, int worldId, @Nonnull BlockPos pos) {
    tag.putInt("X", pos.getX());
    tag.putInt("Y", pos.getY());
    tag.putInt("Z", pos.getZ());
    tag.putInt("WORLD", worldId);
    tag.putString("TYPE", getType());
  }
}
