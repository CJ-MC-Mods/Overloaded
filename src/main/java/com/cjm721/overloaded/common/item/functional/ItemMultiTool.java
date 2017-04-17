package com.cjm721.overloaded.common.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.item.ModItem;
import com.cjm721.overloaded.common.network.packets.MultiToolLeftClickMessage;
import com.cjm721.overloaded.common.network.packets.MultiToolRightClickMessage;
import mcjty.lib.tools.ChatTools;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemMultiTool extends ModItem {

    public ItemMultiTool() {
        setMaxStackSize(1);
        setRegistryName("multi_toll");
        setUnlocalizedName("multi_toll");
        setCreativeTab(OverloadedCreativeTabs.TECH);


        GameRegistry.register(this);
    }

    @Override
    public void registerModel() {

    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        return false;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return false;
    }

    @Override
    protected ActionResult<ItemStack> clOnItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer player, EnumHand hand) {
        if(worldIn.isRemote) {
            // TODO Make distance a config option
            RayTraceResult result = worldIn.rayTraceBlocks(player.getPositionEyes(1), player.getPositionVector().add(player.getLookVec().scale(128)));
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                Overloaded.proxy.networkWrapper.sendToServer(new MultiToolRightClickMessage(result.getBlockPos(),result.sideHit));
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    private void breakAndDropAtPlayer(@Nonnull World worldIn,@Nonnull BlockPos blockPos, double posX, double posY, double posZ) {
        IBlockState state = worldIn.getBlockState(blockPos);
        List<ItemStack> drops = state.getBlock().getDrops(worldIn, blockPos, state, 0);
        worldIn.setBlockToAir(blockPos);
        for (ItemStack droppedStack : drops) {
            EntityItem entity = new EntityItem(worldIn, posX, posY, posZ, droppedStack);
            worldIn.spawnEntity(entity);
        }
    }

    // Registering only on client side
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void leftClickBlock(@Nonnull PlayerInteractEvent.LeftClickBlock event) {
        if(event.getSide() == Side.SERVER)
            return;

        ItemStack stack = event.getItemStack();

        // 1.10.2 can give null
        if(stack != null && stack.getItem().equals(this)) {
            leftClickOnBlockClient(event.getPos());
        }
    }

    // Registering only on client side
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void leftClickEmpty(@Nonnull PlayerInteractEvent.LeftClickEmpty event) {
        if(event.getSide() == Side.SERVER)
            return;

        ItemStack stack = event.getItemStack();

        // 1.10.2 can give null
        if(!ItemStackTools.isEmpty(stack) && stack.getItem().equals(this)) {
            EntityPlayer entityLiving = event.getEntityPlayer();
            RayTraceResult result = entityLiving.rayTrace(128, 0);
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                leftClickOnBlockClient(result.getBlockPos());
            }
        }
    }

    private void leftClickOnBlockClient(BlockPos pos) {
        IMessage message = new MultiToolLeftClickMessage(pos);
        Overloaded.proxy.networkWrapper.sendToServer(message);
    }

    public void leftClickOnBlockServer(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull BlockPos pos) {
        ItemStack itemStack = player.getHeldItem(EnumHand.MAIN_HAND);
        if(ItemStackTools.isEmpty(itemStack) || itemStack.getItem() != this || world.isAirBlock(pos)) {
            return;
        }

        if(player.isSneaking()) {
            IBlockState state = world.getBlockState(pos);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("Block", Block.getIdFromBlock(state.getBlock()));

            NBTTagCompound stateTag = new NBTTagCompound();
            NBTUtil.writeBlockState(stateTag, state);
            tag.setTag("BlockState", stateTag);
            itemStack.setTagCompound(tag);
            ITextComponent component = new ItemStack(Item.getItemFromBlock(state.getBlock())).getTextComponent();
            ChatTools.addChatMessage(player, new TextComponentString("Bound tool to ").appendSibling(component));
        } else {
            breakAndDropAtPlayer(world, pos, player.posX, player.posY, player.posZ);
        }
    }

    public void rightClickWithItem(@Nonnull World worldIn, @Nonnull EntityPlayerMP player, @Nonnull BlockPos pos, @Nonnull EnumFacing sideHit) {
        ItemStack multiTool = player.getHeldItemMainhand();

        if(ItemStackTools.isEmpty(multiTool) || multiTool.getItem() != this) {
            return;
        }

        NBTTagCompound tagCompound = multiTool.getTagCompound();

        if(tagCompound == null){
            ChatTools.addChatMessage(player, new TextComponentString("No block type selected to place."));
            return;
        }

        ItemBlock blockToPlace = (ItemBlock) Item.getItemFromBlock(Block.getBlockById(multiTool.getTagCompound().getInteger("Block")));
        IBlockState state = NBTUtil.readBlockState(tagCompound.getCompoundTag("BlockState"));

        Vec3i sideVector = sideHit.getDirectionVec();
        BlockPos newPosition = pos.add(sideVector);

        if(!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit))
            return;

        if (player.isSneaking()) {
            BlockPos playerPos = player.getPosition();
            playerPos = playerPos.add(sideHit.getOpposite().getDirectionVec());
            switch(sideHit) {
                case UP:
                    while(newPosition.getY() < playerPos.getY()) {
                        newPosition = newPosition.add(sideVector);
                        if(!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit))
                            break;
                    }
                    break;
                case DOWN:
                    while(newPosition.getY() - 1 > playerPos.getY()) {
                        newPosition = newPosition.add(sideVector);
                        if(!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit))
                            break;
                    }
                    break;
                case NORTH:
                    while(newPosition.getZ() > playerPos.getZ()) {
                        newPosition = newPosition.add(sideVector);
                        if(!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit))
                            break;
                    }
                    break;
                case SOUTH:
                    while(newPosition.getZ() < playerPos.getZ()) {
                        newPosition = newPosition.add(sideVector);
                        if(!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit))
                            break;
                    }
                    break;
                case EAST:
                    while(newPosition.getX() < playerPos.getX()) {
                        newPosition = newPosition.add(sideVector);
                        if(!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit))
                            break;
                    }
                    break;
                case WEST:
                    while(newPosition.getX() > playerPos.getX()) {
                        newPosition = newPosition.add(sideVector);
                        if(!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit))
                            break;
                    }
                    break;
            }
        }
    }

    private boolean placeBlock(@Nonnull ItemBlock block, @Nonnull IBlockState state, @Nonnull EntityPlayerMP player, @Nonnull World worldIn, @Nonnull BlockPos newPosition, EnumFacing facing) {
        // Can we place a block at this Pos
        if((!worldIn.isAirBlock(newPosition) && !worldIn.getBlockState(newPosition).getMaterial().isReplaceable())) {
            return false;
        }

        ItemStack searchStack = new ItemStack(block);

        int foundStackSlot = findItemStack(searchStack, player);
        if(foundStackSlot == -1)
            return false;

        player.inventory.getStackInSlot(foundStackSlot);

        ItemStack foundStack = player.inventory.getStackInSlot(foundStackSlot);

        boolean result = block.placeBlockAt(foundStack, player,worldIn, newPosition,facing,0.5F,0.5F,0.5F,state);;
        player.inventory.decrStackSize(foundStackSlot, 1);
        return result;
    }


    private int findItemStack(@Nonnull ItemStack item, @Nonnull EntityPlayerMP player) {
        int size = player.inventory.getSizeInventory();
        for(int i = 0; i < size; i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if(stack.isItemEqual(item))
                return i;
        }

        return -1;
    }
}
