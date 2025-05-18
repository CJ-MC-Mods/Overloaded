package com.cjm721.overloaded.util;

import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class PlayerInteractionUtil {

  public static boolean tryHarvestBlock(ServerPlayer player, ServerLevel world, BlockPos pos) {
    net.neoforged.neoforge.event.level.BlockEvent.BreakEvent exp = CommonHooks.fireBlockBreak(
            world, player.gameMode.getGameModeForPlayer(), player, pos, world.getBlockState(pos));
    if (exp.isCanceled()) {
      return false;
    } else {
      BlockState iblockstate = world.getBlockState(pos);
      BlockEntity tileentity = world.getBlockEntity(pos);
      Block block = iblockstate.getBlock();

      if ((block.equals(Blocks.COMMAND_BLOCK) || block.equals(Blocks.STRUCTURE_BLOCK))
          && !player.canUseGameMasterBlocks()) {
        world.sendBlockUpdated(pos, iblockstate, iblockstate, 3);
        return false;
      } else {
        world.levelEvent(null, 2001, pos, Block.getId(iblockstate));
        boolean flag1;

        if (player.getAbilities().instabuild) {
          flag1 = removeBlock(world, pos, player, false);
          player.connection.send(new ClientboundBlockUpdatePacket(world, pos));
        } else {
          ItemStack itemstack1 = player.getMainHandItem();
          ItemStack itemstack2 = itemstack1.isEmpty() ? ItemStack.EMPTY : itemstack1.copy();
          boolean flag = iblockstate.canHarvestBlock(world, pos, player);

          itemstack1.mineBlock(world, iblockstate, pos, player);

          flag1 = removeBlock(world, pos, player, flag);
          if (flag1 && flag) {
            iblockstate
                .getBlock()
                .playerDestroy(world, player, pos, iblockstate, tileentity, itemstack2);
          }
        }

        // Drop experience
//        if (!player.isCreative() && flag1 && exp > 0) {
//          iblockstate.getBlock().popExperience(world, player.blockPosition(), exp);
//        }
        return flag1;
      }
    }
  }

  private static boolean removeBlock(
          Level world, BlockPos pos, Player player, boolean canHarvest) {
//    BlockState iblockstate = world.getBlockState(pos);
//    BlockState flag =
//        iblockstate.getBlock().playerWillDestroy(world, pos,iblockstate, player);
//
//    world.destroyBlock()
//    if (flag) {
//      iblockstate.getBlock().destroy(world, pos, iblockstate);
//    }
//
//    return flag;
    return false;
  }
//
//  @Nonnull
//  public static BlockPlaceResult placeBlock(
//      @Nonnull ItemStack searchStack,
//      @Nonnull ServerPlayerEntity player,
//      @Nonnull World worldIn,
//      @Nonnull BlockPos newPosition,
//      @Nonnull Direction facing,
//      @Nonnull IEnergyStorage energy,
//      float hitX,
//      float hitY,
//      float hitZ) {
//
//    // Can we place a block at this Pos
//    BlockItem itemBlock = ((BlockItem) searchStack.getItem());
////    if (worldIn.loadedAndEntityCanStandOn(newPosition, player)) {
////      return BlockPlaceResult.FAIL_DENY;
////    }
//
//    BlockSnapshot blockSnapshot =
//        BlockSnapshot.create(worldIn.dimension(), worldIn, newPosition);
//    BlockState placedAgainst =
//        blockSnapshot.getWorld().getBlockState(blockSnapshot.getPos().relative(facing.getOpposite()));
//    BlockEvent.EntityPlaceEvent event =
//        new BlockEvent.EntityPlaceEvent(blockSnapshot, placedAgainst, player);
//    neoforged.EVENT_BUS.post(event);
//
//    if (event.isCanceled()) {
//      return BlockPlaceResult.FAIL_DENY;
//    }
//
//    long distance = Math.round(Math.sqrt(player.blockPosition().distSqr(newPosition)));
//
//    long cost =
//        OverloadedConfig.INSTANCE.multiToolConfig.placeBaseCost
//            + OverloadedConfig.INSTANCE.multiToolConfig.costPerMeterAway * distance;
//    if (!player.abilities.instabuild
//        && (cost > Integer.MAX_VALUE || cost < 0 || energy.getEnergyStored() < cost))
//      return BlockPlaceResult.FAIL_ENERGY;
//
//    LazyOptional<IItemHandler> opInventory =
//        player.getCapability(ITEM_HANDLER_CAPABILITY, Direction.UP);
//    if (!opInventory.isPresent()) {
//      Overloaded.logger.warn("Player has no ItemHandler Capability? NBT: " + player.serializeNBT());
//      return BlockPlaceResult.FAIL_PREREQUISITE;
//    }
//    IItemHandler inventory =
//        opInventory.orElseThrow(() -> new RuntimeException("Impossible Condition"));
//
//    int foundStackSlot = findItemStackSlot(searchStack, inventory);
//    if (foundStackSlot == -1) {
//      return BlockPlaceResult.FAIL_PREREQUISITE;
//    }
//    ItemStack foundStack =
//        inventory.extractItem(foundStackSlot, 1, player.abilities.instabuild);
//
//    BlockItemUseContext context =
//        new BlockItemUseContextPublic(
//            worldIn,
//            player,
//            Hand.MAIN_HAND,
//            foundStack,
//            new BlockRayTraceResult(
//                new Vector3d(
//                    hitX + newPosition.getX(),
//                    hitY + newPosition.getY(),
//                    hitZ + newPosition.getZ()),
//                facing,
//                newPosition,
//                false));
//
//    InteractionResult result = ForgeHooks.onPlaceItemIntoWorld(context);
//
//    switch (result) {
//      case CONSUME:
//      case SUCCESS:
//        SoundType soundtype =
//            worldIn
//                .getBlockState(newPosition)
//                .getBlock()
//                .getSoundType(worldIn.getBlockState(newPosition), worldIn, newPosition, player);
//        worldIn.playSound(
//            null,
//            newPosition,
//            soundtype.getPlaceSound(),
//            SoundCategory.BLOCKS,
//            (soundtype.getVolume() + 1.0F) / 2.0F,
//            soundtype.getPitch() * 0.8F);
//        if (!player.abilities.instabuild) {
//          energy.extractEnergy((int) cost, false);
//        }
//        return BlockPlaceResult.SUCCESS;
//      case PASS:
//      case FAIL:
//      default:
//        inventory.insertItem(foundStackSlot, foundStack, player.abilities.instabuild);
//        return BlockPlaceResult.FAIL_DENY;
//    }
//  }

  private static int findItemStackSlot(@Nonnull ItemStack item, @Nonnull IItemHandler inventory) {
    int size = inventory.getSlots();
    for (int i = 0; i < size; i++) {
      ItemStack stack = inventory.getStackInSlot(i);
      if (!stack.isEmpty() && ItemStack.isSameItem(stack, item)) {
        return i;
      }
    }

    return -1;
  }

//  @Nonnull
//  @OnlyIn(Dist.CLIENT)
//  public static BlockRayTraceResult getBlockPlayerLookingAtClient(
//      PlayerEntity player, float partialTicks) {
//    return player
//        .getCommandSenderWorld()
//        .clip(
//            new RayTraceContext(
//                player.getEyePosition(partialTicks),
//                player
//                    .getViewVector(partialTicks)
//                    .scale(OverloadedConfig.INSTANCE.multiToolConfig.reach)
//                    .add(player.getEyePosition(partialTicks)),
//                RayTraceContext.BlockMode.COLLIDER,
//                RayTraceContext.FluidMode.NONE,
//                player));
//  }
}
