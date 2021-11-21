package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkRegistry;

import javax.annotation.Nonnull;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class AbstractBlockHyperReceiver extends AbstractBlockHyperNode {

  protected AbstractBlockHyperReceiver(@Nonnull Properties materialIn) {
    super(materialIn);
  }

  @Override
  @Nonnull
  public ActionResultType use(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult rayTraceResult) {
    ItemStack heldItem = player.getItemInHand(hand);
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
            new StringTextComponent(
                String.format("Recorded: World: %s Position: %s", worldId, pos.toShortString())),
            false);
      }

      return ActionResultType.CONSUME;
    } else {
      return super.use(state, world, pos, player, hand, rayTraceResult);
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
