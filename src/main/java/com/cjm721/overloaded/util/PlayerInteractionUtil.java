package com.cjm721.overloaded.util;

import com.cjm721.overloaded.Overloaded;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class PlayerInteractionUtil {

    public static boolean tryHarvestBlock(EntityPlayerMP player, World world, BlockPos pos) {
        int exp = net.minecraftforge.common.ForgeHooks.onBlockBreakEvent(world, player.interactionManager.getGameType(), player, pos);
        if (exp == -1) {
            return false;
        } else {
            IBlockState iblockstate = world.getBlockState(pos);
            TileEntity tileentity = world.getTileEntity(pos);
            Block block = iblockstate.getBlock();

            if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !player.canUseCommandBlock()) {
                world.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);
                return false;
            } else {
                world.playEvent(null, 2001, pos, Block.getStateId(iblockstate));
                boolean flag1;

                if (player.capabilities.isCreativeMode) {
                    flag1 = removeBlock(world, pos, player, false);
                    player.connection.sendPacket(new SPacketBlockChange(world, pos));
                } else {
                    ItemStack itemstack1 = player.getHeldItemMainhand();
                    ItemStack itemstack2 = itemstack1.isEmpty() ? ItemStack.EMPTY : itemstack1.copy();
                    boolean flag = iblockstate.getBlock().canHarvestBlock(world, pos, player);

                    itemstack1.onBlockDestroyed(world, iblockstate, pos, player);

                    flag1 = removeBlock(world, pos, player, flag);
                    if (flag1 && flag) {
                        iblockstate.getBlock().harvestBlock(world, player, pos, iblockstate, tileentity, itemstack2);
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

    private static boolean removeBlock(World world, BlockPos pos, EntityPlayer player, boolean canHarvest) {
        IBlockState iblockstate = world.getBlockState(pos);
        boolean flag = iblockstate.getBlock().removedByPlayer(iblockstate, world, pos, player, canHarvest);

        if (flag) {
            iblockstate.getBlock().onBlockDestroyedByPlayer(world, pos, iblockstate);
        }

        return flag;
    }

    @Nonnull
    public static BlockPlaceResult placeBlock(@Nonnull ItemStack searchStack, @Nonnull EntityPlayerMP player, @Nonnull World worldIn, @Nonnull BlockPos newPosition, @Nonnull EnumFacing facing, @Nonnull IEnergyStorage energy, float hitX, float hitY, float hitZ) {
        // Can we place a block at this Pos
        ItemBlock itemBlock = ((ItemBlock) searchStack.getItem());
        if (!worldIn.mayPlace(itemBlock.getBlock(), newPosition, false, facing, null)) {
            return BlockPlaceResult.FAIL_DENY;
        }

        BlockEvent.PlaceEvent event = ForgeEventFactory.onPlayerBlockPlace(player, new BlockSnapshot(worldIn, newPosition, worldIn.getBlockState(newPosition)), facing, EnumHand.MAIN_HAND);
        if (event.isCanceled())
            return BlockPlaceResult.FAIL_DENY;

        long distance = Math.round(player.getPosition().getDistance(newPosition.getX(), newPosition.getY(), newPosition.getZ()));

        long cost = Overloaded.cachedConfig.multiToolConfig.placeBaseCost + Overloaded.cachedConfig.multiToolConfig.costPerMeterAway * distance;
        if (!player.capabilities.isCreativeMode && (cost > Integer.MAX_VALUE || cost < 0 || energy.getEnergyStored() < cost))
            return BlockPlaceResult.FAIL_ENERGY;

        IItemHandler inventory = player.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

        int foundStackSlot = findItemStackSlot(searchStack, inventory);
        if (foundStackSlot == -1) {
            return BlockPlaceResult.FAIL_PREREQUISITE;
        }
        ItemStack foundStack = inventory.extractItem(foundStackSlot, 1, player.capabilities.isCreativeMode);

        int i = itemBlock.getMetadata(foundStack.getMetadata());
        IBlockState iblockstate1 = itemBlock.getBlock().getStateForPlacement(worldIn, newPosition, facing, hitX, hitY, hitZ, i, player, EnumHand.MAIN_HAND);

        if (itemBlock.placeBlockAt(foundStack, player, worldIn, newPosition, facing, hitX, hitY, hitZ, iblockstate1)) {
            SoundType soundtype = worldIn.getBlockState(newPosition).getBlock().getSoundType(worldIn.getBlockState(newPosition), worldIn, newPosition, player);
            worldIn.playSound(null, newPosition, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

            if (!player.capabilities.isCreativeMode)
                energy.extractEnergy((int) cost, false);
            return BlockPlaceResult.SUCCESS;
        }
        inventory.insertItem(foundStackSlot, foundStack, player.capabilities.isCreativeMode);
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
    @SideOnly(Side.CLIENT)
    public static RayTraceResult getBlockPlayerLookingAtClient(EntityPlayer player, float partialTicks) {
        RayTraceResult result = player.rayTrace(Overloaded.cachedConfig.multiToolConfig.reach, partialTicks);

        if (result == null || result.typeOfHit != RayTraceResult.Type.BLOCK)
            return null;
        return result;
    }
}
