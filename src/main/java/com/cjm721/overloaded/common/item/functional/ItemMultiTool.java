package com.cjm721.overloaded.common.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.item.ModItem;
import com.cjm721.overloaded.common.network.packets.MultiToolLeftClickMessage;
import com.cjm721.overloaded.common.network.packets.MultiToolRightClickMessage;
import com.cjm721.overloaded.common.storage.LongEnergyStack;
import com.cjm721.overloaded.common.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.common.util.BlockResult;
import com.cjm721.overloaded.common.util.EnergyWrapper;
import mcjty.lib.tools.ChatTools;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.common.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class ItemMultiTool extends ModItem {

    public ItemMultiTool() {
        setMaxStackSize(1);
        setRegistryName("multi_tool");
        setUnlocalizedName("multi_tool");
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.register(this);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type == EnumEnchantmentType.DIGGER;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return 15;
    }

    @Override
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return this.getItemStackLimit(stack) == 1;
    }

    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "multi_tool"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    }

    @Override
    public void clAddInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        IHyperHandlerEnergy handler = stack.getCapability(HYPER_ENERGY_HANDLER, null);
        tooltip.add("Energy Stored: " + handler.status().getAmount());

        super.clAddInformation(stack, playerIn, tooltip, advanced);
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
//                ((ItemBlock)Item.getItemFromBlock(Blocks.GLASS)).canPlaceBlockOnSide(worldIn, result.getBlockPos(), result.sideHit,player, null);
                Overloaded.proxy.networkWrapper.sendToServer(new MultiToolRightClickMessage(result.getBlockPos(),result.sideHit));
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    /**
     * @return True if the break was successful, false otherwise
     */
    @Nonnull
    private BlockResult breakAndDropAtPlayer(@Nonnull World worldIn,@Nonnull BlockPos blockPos, double posX, double posY, double posZ, @Nonnull LongEnergyStack energyStack, int fortune, int effiency, int unbreaking) {
        IBlockState state = worldIn.getBlockState(blockPos);

        float hardness = state.getBlockHardness(worldIn, blockPos);

        if(hardness < 0) {
            return BlockResult.FAIL_UNBREAKABLE;
        }

        float breakCost = (hardness / effiency + 1) + (100  / (unbreaking + 1));

        if(energyStack.getAmount() < breakCost){
            return BlockResult.FAIL_ENERGY;
        }

        List<ItemStack> drops = state.getBlock().getDrops(worldIn, blockPos, state, fortune);
        boolean result = worldIn.setBlockToAir(blockPos);

        if(result) {
            SoundType soundType = state.getBlock().getSoundType(state,worldIn,blockPos,null);

            worldIn.playSound(null,blockPos,soundType.getBreakSound(), SoundCategory.BLOCKS, soundType.getVolume(), soundType.getPitch());

            int xp = state.getBlock().getExpDrop(state,worldIn,blockPos,0);
            if(xp > 0)
                worldIn.spawnEntity(new EntityXPOrb(worldIn, posX,posY,posZ,xp));
            for (ItemStack droppedStack : drops) {
                EntityItem entity = new EntityItem(worldIn, posX, posY, posZ, droppedStack);
                worldIn.spawnEntity(entity);
            }
            energyStack.amount -= breakCost;
            return BlockResult.SUCCESS;
        }
        return BlockResult.FAIL_REMOVE;
    }

    // Registering only on client side
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void leftClickBlock(@Nonnull PlayerInteractEvent.LeftClickBlock event) {
        if(event.getSide() == Side.SERVER || event.getEntityPlayer() != Minecraft.getMinecraft().player)
            return;

        ItemStack stack = event.getItemStack();
        if(!ItemStackTools.isEmpty(stack) && stack.getItem().equals(this)) {
            leftClickOnBlockClient(event.getPos());
        }
    }

    // Registering only on client side
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void leftClickEmpty(@Nonnull PlayerInteractEvent.LeftClickEmpty event) {
        if(event.getSide() == Side.SERVER || event.getEntityPlayer() != Minecraft.getMinecraft().player)
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
            NBTTagCompound tag  = itemStack.getTagCompound();
            if(tag == null) {
                tag = new NBTTagCompound();
            }
            IBlockState state = world.getBlockState(pos);
            tag.setInteger("Block", Block.getIdFromBlock(state.getBlock()));

            NBTTagCompound stateTag = new NBTTagCompound();
            NBTUtil.writeBlockState(stateTag, state);
            tag.setTag("BlockState", stateTag);
            itemStack.setTagCompound(tag);
            ITextComponent component = new ItemStack(Item.getItemFromBlock(state.getBlock())).getTextComponent();
            ChatTools.addChatMessage(player, new TextComponentString("Bound tool to ").appendSibling(component));
        } else {
            IHyperHandlerEnergy energy = itemStack.getCapability(HYPER_ENERGY_HANDLER, null);
            LongEnergyStack energyStack = energy.take(new LongEnergyStack(Long.MAX_VALUE),true);

            int efficency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemStack);
            int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack);
            int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemStack);

            switch(breakAndDropAtPlayer(world, pos, player.posX, player.posY, player.posZ, energyStack,fortune,efficency,unbreaking)) {
                case FAIL_REMOVE:
                    ChatTools.addChatMessage(player, new TextComponentString("Unable to break block, reason unknown"));
                    break;
                case FAIL_ENERGY:
                    ChatTools.addChatMessage(player, new TextComponentString("Unable to break block, not enough energy"));
                    break;
                case FAIL_UNBREAKABLE:
                    ChatTools.addChatMessage(player, new TextComponentString("Block is unbreakable"));
                    break;
            }
            energy.give(energyStack,true);
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

        Item tempItem = Item.getItemFromBlock(Block.getBlockById(multiTool.getTagCompound().getInteger("Block")));

        if(!(tempItem instanceof ItemBlock)) {
            ChatTools.addChatMessage(player, new TextComponentString("No valid block type selected to place."));
            return;
        }

        ItemBlock blockToPlace = (ItemBlock) tempItem;


        IHyperHandlerEnergy energy = multiTool.getCapability(HYPER_ENERGY_HANDLER, null);
        LongEnergyStack energyStack = energy.take(new LongEnergyStack(Long.MAX_VALUE),true);
        ChatTools.addChatMessage(player, new TextComponentString("Amount: " + energyStack.getAmount()));


        IBlockState state = NBTUtil.readBlockState(tagCompound.getCompoundTag("BlockState"));

        Vec3i sideVector = sideHit.getDirectionVec();
        BlockPos newPosition = pos.add(sideVector);


        try {
            if (!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit, energyStack))
                return;
            if (player.isSneaking()) {
                BlockPos playerPos = player.getPosition();
                //playerPos = playerPos.add(sideHit.getOpposite().getDirectionVec());
                switch (sideHit) {
                    case UP:
                        while (newPosition.getY() < playerPos.getY()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit, energyStack))
                                break;
                        }
                        break;
                    case DOWN:
                        while (newPosition.getY() > playerPos.getY()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit, energyStack))
                                break;
                        }
                        break;
                    case NORTH:
                        while (newPosition.getZ() > playerPos.getZ()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit, energyStack))
                                break;
                        }
                        break;
                    case SOUTH:
                        while (newPosition.getZ() < playerPos.getZ()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit, energyStack))
                                break;
                        }
                        break;
                    case EAST:
                        while (newPosition.getX() < playerPos.getX()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit, energyStack))
                                break;
                        }
                        break;
                    case WEST:
                        while (newPosition.getX() > playerPos.getX()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockToPlace, state, player, worldIn, newPosition, sideHit, energyStack))
                                break;
                        }
                        break;
                }
            }
        }
        finally {
            energy.give(energyStack, true);
        }
    }

    private boolean placeBlock(@Nonnull ItemBlock block, @Nonnull IBlockState state, @Nonnull EntityPlayerMP player, @Nonnull World worldIn, @Nonnull BlockPos newPosition, @Nonnull EnumFacing facing, @Nonnull LongEnergyStack energyStack) {
        // Can we place a block at this Pos
        if(!worldIn.mayPlace(block.getBlock(), newPosition, false,facing, null)) {
            return false;
        }

        if(energyStack.amount < 100)
            return false;

        ItemStack searchStack = new ItemStack(block);

        int foundStackSlot = findItemStack(searchStack, player);
        if(foundStackSlot == -1)
            return false;

        player.inventory.getStackInSlot(foundStackSlot);

        ItemStack foundStack = player.inventory.getStackInSlot(foundStackSlot);


        boolean result = block.placeBlockAt(foundStack, player,worldIn, newPosition,facing,0.5F,0.5F,0.5F,state);
        player.inventory.decrStackSize(foundStackSlot, 1);
        if(result)
            energyStack.amount -= 100;

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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new EnergyWrapper(stack);
    }
}
