package com.cjm721.overloaded.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CommandBlockBlock;
import net.minecraft.block.StructureBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerInteractionUtil {

  public static boolean tryHarvestBlock(ServerPlayerEntity player, World world, BlockPos pos) {
    int exp =
        net.minecraftforge.common.ForgeHooks.onBlockBreakEvent(
            world, player.interactionManager.getGameType(), player, pos);
    if (exp == -1) {
      return false;
    } else {
      BlockState iblockstate = world.getBlockState(pos);
      TileEntity tileentity = world.getTileEntity(pos);
      Block block = iblockstate.getBlock();

      if ((block instanceof CommandBlockBlock || block instanceof StructureBlock)
          && !player.canUseCommandBlock()) {
        world.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);
        return false;
      } else {
        world.playEvent(null, 2001, pos, Block.getStateId(iblockstate));
        boolean flag1;

        if (player.abilities.isCreativeMode) {
          flag1 = removeBlock(world, pos, player, false);
          player.connection.sendPacket(new SChangeBlockPacket(world, pos));
        } else {
          ItemStack itemstack1 = player.getHeldItemMainhand();
          ItemStack itemstack2 = itemstack1.isEmpty() ? ItemStack.EMPTY : itemstack1.copy();
          boolean flag = iblockstate.canHarvestBlock(world, pos, player);

          itemstack1.onBlockDestroyed(world, iblockstate, pos, player);

          flag1 = removeBlock(world, pos, player, flag);
          if (flag1 && flag) {
            iblockstate
                .getBlock()
                .harvestBlock(world, player, pos, iblockstate, tileentity, itemstack2);
          }
        }

        // Drop experience
        if (!player.isCreative() && flag1 && exp > 0) {
          iblockstate.getBlock().dropXpOnBlockBreak(world, player.getPosition(), exp);
        }
        return flag1;
      }
    }
  }

  private static boolean removeBlock(
      World world, BlockPos pos, PlayerEntity player, boolean canHarvest) {
    BlockState iblockstate = world.getBlockState(pos);
    boolean flag =
        iblockstate.getBlock().removedByPlayer(iblockstate, world, pos, player, canHarvest, null);

    if (flag) {
      iblockstate.getBlock().onPlayerDestroy(world, pos, iblockstate);
    }

    return flag;
  }

  @Nonnull
  public static BlockPlaceResult placeBlock(
      @Nonnull ItemStack searchStack,
      @Nonnull ServerPlayerEntity player,
      @Nonnull World worldIn,
      @Nonnull BlockPos newPosition,
      @Nonnull Direction facing,
      @Nonnull IEnergyStorage energy,
      float hitX,
      float hitY,
      float hitZ) {
    //    // Can we place a block at this Pos
    //    BlockItem itemBlock = ((BlockItem) searchStack.getItem());
    //    if (!worldIn.mayPlace(itemBlock.getBlock(), newPosition, false, facing, null)) {
    //      return BlockPlaceResult.FAIL_DENY;
    //    }
    //
    //    if (!ForgeEventFactory.onBlockPlace(
    //        player,
    //        new BlockSnapshot(worldIn, newPosition, worldIn.getBlockState(newPosition)),
    //        facing)) return BlockPlaceResult.FAIL_DENY;
    //
    //    long distance = Math.round(Math.sqrt(player.getPosition().distanceSq(newPosition)));
    //
    //    long cost =
    //        Overloaded.cachedConfig.multiToolConfig.placeBaseCost
    //            + Overloaded.cachedConfig.multiToolConfig.costPerMeterAway * distance;
    //    if (!player.abilities.isCreativeMode
    //        && (cost > Integer.MAX_VALUE || cost < 0 || energy.getEnergyStored() < cost))
    //      return BlockPlaceResult.FAIL_ENERGY;
    //
    //    IItemHandler inventory = player.getCapability(ITEM_HANDLER_CAPABILITY,
    // Direction.UP).orElse(null);
    //
    //    int foundStackSlot = findItemStackSlot(searchStack, inventory);
    //    if (foundStackSlot == -1) {
    //      return BlockPlaceResult.FAIL_PREREQUISITE;
    //    }
    //    ItemStack foundStack =
    //        inventory.extractItem(foundStackSlot, 1, player.abilities.isCreativeMode);
    //
    //    int i = itemBlock.getMetadata(foundStack.getMetadata());
    //    BlockState iblockstate1 =
    //        itemBlock
    //            .getBlock()
    //            .getStateForPlacement(new BlockItemUseContext(worldIn, player, Hand.MAIN_HAND,
    // foundStack, new BlockRayTraceResult(,facing,newPosition,false));
    //
    //    if (itemBlock.placeBlockAt(
    //        foundStack, player, worldIn, newPosition, facing, hitX, hitY, hitZ, iblockstate1)) {
    //      SoundType soundtype =
    //          worldIn
    //              .getBlockState(newPosition)
    //              .getBlock()
    //              .getSoundType(worldIn.getBlockState(newPosition), worldIn, newPosition, player);
    //      worldIn.playSound(
    //          null,
    //          newPosition,
    //          soundtype.getPlaceSound(),
    //          SoundCategory.BLOCKS,
    //          (soundtype.getVolume() + 1.0F) / 2.0F,
    //          soundtype.getPitch() * 0.8F);
    //
    //      if (!player.capabilities.isCreativeMode) energy.extractEnergy((int) cost, false);
    //      return BlockPlaceResult.SUCCESS;
    //    }
    //    inventory.insertItem(foundStackSlot, foundStack, player.capabilities.isCreativeMode);
    return BlockPlaceResult.FAIL_DENY;
  }

  private static int findItemStackSlot(@Nonnull ItemStack item, @Nonnull IItemHandler inventory) {
    int size = inventory.getSlots();
    for (int i = 0; i < size; i++) {
      ItemStack stack = inventory.getStackInSlot(i);
      if (!stack.isEmpty() && stack.isItemEqual(item)) {
        return i;
      }
    }

    return -1;
  }

  @Nullable
  @OnlyIn(Dist.CLIENT)
  public static BlockRayTraceResult getBlockPlayerLookingAtClient(
      PlayerEntity player, float partialTicks) {
    //    RayTraceResult result =
    //        player.rayTrace(Overloaded.cachedConfig.multiToolConfig.reach, partialTicks);
    //
    //    if (result == null || result.typeOfHit != RayTraceResult.Type.BLOCK) return null;
    //    return result;
    return null;
  }
}
