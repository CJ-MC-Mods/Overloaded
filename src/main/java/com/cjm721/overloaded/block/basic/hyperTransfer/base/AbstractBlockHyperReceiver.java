package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class AbstractBlockHyperReceiver extends AbstractBlockHyperNode {

  protected AbstractBlockHyperReceiver(@Nonnull Properties materialIn) {
    super(materialIn);
  }

  @Override
  public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player) {
    ItemStack heldItem = player.getActiveItemStack();
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
    } else {
      super.onBlockClicked(state, world, pos, player);
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
