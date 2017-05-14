package com.cjm721.overloaded.common.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.config.MultiToolConfig;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import com.cjm721.overloaded.common.item.ModItem;
import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.network.packets.MultiToolLeftClickMessage;
import com.cjm721.overloaded.common.network.packets.MultiToolRightClickMessage;
import com.cjm721.overloaded.common.storage.LongEnergyStack;
import com.cjm721.overloaded.common.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.common.util.BlockResult;
import com.cjm721.overloaded.common.util.EnergyWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

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

//    @Override
//    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
//        return enchantment != null && enchantment.type == EnumEnchantmentType.DIGGER;
//    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return 15;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "multi_tool"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    }

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.multiTool)
            GameRegistry.addRecipe(new ItemStack(this), "NI ", "IES", " SB", 'N', Items.NETHER_STAR, 'I', Items.IRON_INGOT, 'E', ModItems.energyCore, 'B', Blocks.IRON_BLOCK, 'S', ModBlocks.netherStarBlock);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        IHyperHandlerEnergy handler = stack.getCapability(HYPER_ENERGY_HANDLER, null);
        tooltip.add("Energy Stored: " + handler.status().getAmount());

        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack itemStackIn, World worldIn, EntityPlayer player, EnumHand hand) {
        RayTraceResult result = worldIn.rayTraceBlocks(player.getPositionEyes(1), player.getPositionVector().add(player.getLookVec().scale(MultiToolConfig.reach)));
        if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            Overloaded.proxy.networkWrapper.sendToServer(new MultiToolRightClickMessage(result.getBlockPos(),result.sideHit, (float) result.hitVec.xCoord - result.getBlockPos().getX(), (float) result.hitVec.yCoord - result.getBlockPos().getY(), (float) result.hitVec.zCoord - result.getBlockPos().getZ()));

        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    /**
     * @return True if the break was successful, false otherwise
     */
    @Nonnull
    private BlockResult breakAndUseEnergy(@Nonnull World worldIn, @Nonnull BlockPos blockPos, @Nonnull LongEnergyStack energyStack, EntityPlayerMP player, int efficiency, int unbreaking) {
        IBlockState state = worldIn.getBlockState(blockPos);
        //state = state.getBlock().getExtendedState(state, worldIn,blockPos);

        float hardness = state.getBlockHardness(worldIn, blockPos);

        if(hardness < 0) {
            return BlockResult.FAIL_UNBREAKABLE;
        }

        float floatBreakCost = MultiToolConfig.breakBaseCost + (hardness * MultiToolConfig.breakCostMultiplier / (efficiency + 1)) + (100  / (unbreaking + 1)) + (float)blockPos.getDistance((int)player.posX,(int)player.posY,(int)player.posZ);
        if(Float.isInfinite(floatBreakCost) || Float.isNaN(floatBreakCost))
            return BlockResult.FAIL_ENERGY;

        long breakCost = Math.round(floatBreakCost);

        if(breakCost < 0 || energyStack.getAmount() < breakCost){
            return BlockResult.FAIL_ENERGY;
        }


        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(worldIn, blockPos, state, player);
        MinecraftForge.EVENT_BUS.post(event);

        if(event.isCanceled())
            return BlockResult.FAIL_REMOVE;

        drawParticleStreamTo(player, worldIn, blockPos.getX(), blockPos.getY(), blockPos.getZ());

        boolean result = tryHarvestBlock(player,worldIn,blockPos);
        if (result) {
            energyStack.amount -= breakCost;
            return BlockResult.SUCCESS;
        }
        return BlockResult.FAIL_REMOVE;
    }


    public boolean tryHarvestBlock(EntityPlayerMP player, World world, BlockPos pos)
    {
        int exp = net.minecraftforge.common.ForgeHooks.onBlockBreakEvent(world, player.interactionManager.getGameType(), player, pos);
        if (exp == -1)
        {
            return false;
        }
        else
        {
            IBlockState iblockstate = world.getBlockState(pos);
            TileEntity tileentity = world.getTileEntity(pos);
            Block block = iblockstate.getBlock();

            if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !player.canUseCommandBlock())
            {
                world.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);
                return false;
            }
            else
            {
                world.playEvent(null, 2001, pos, Block.getStateId(iblockstate));
                boolean flag1;

                if (player.interactionManager.isCreative())
                {
                    flag1 = removeBlock(world,pos,player,false);
                    player.connection.sendPacket(new SPacketBlockChange(world, pos));
                }
                else
                {
                    ItemStack itemstack1 = player.getHeldItemMainhand();
                    boolean flag = iblockstate.getBlock().canHarvestBlock(world, pos, player);

                    itemstack1.onBlockDestroyed(world, iblockstate, pos, player);

                    flag1 = removeBlock(world,pos,player, flag);
                    if (flag1 && flag)
                    {
                        iblockstate.getBlock().harvestBlock(world, player, pos, iblockstate, tileentity, itemstack1.copy());
                    }
                }

                // Drop experience
                if (!player.isCreative() && flag1 && exp > 0)
                {
                    iblockstate.getBlock().dropXpOnBlockBreak(world, player.getPosition(), exp);
                }
                return flag1;
            }
        }
    }

    private boolean removeBlock(World world, BlockPos pos, EntityPlayer player,boolean canHarvest)
    {
        IBlockState iblockstate = world.getBlockState(pos);
        boolean flag = iblockstate.getBlock().removedByPlayer(iblockstate, world, pos, player, canHarvest);

        if (flag)
        {
            iblockstate.getBlock().onBlockDestroyedByPlayer(world, pos, iblockstate);
        }

        return flag;
    }

    public void drawParticleStreamTo(EntityPlayer source, World world, double x, double y, double z) {
//        Vec3d direction = source.getLookVec().normalize();
//        double scale = 1.0;
//        double xoffset = 1.3f;
//        double yoffset = -.2;
//        double zoffset = 0.3f;
//        Vec3d horzdir = direction.normalize();
//        horzdir = new Vec3d(horzdir.xCoord, 0, horzdir.zCoord);
//        horzdir = horzdir.normalize();
//        double cx = source.posX + direction.xCoord * xoffset - direction.yCoord * horzdir.xCoord * yoffset - horzdir.zCoord * zoffset;
//        double cy = source.posY + source.getEyeHeight() + direction.yCoord * xoffset + (1 - Math.abs(direction.yCoord)) * yoffset;
//        double cz = source.posZ + direction.zCoord * xoffset - direction.yCoord * horzdir.zCoord * yoffset + horzdir.xCoord * zoffset;
//        double dx = x - cx;
//        double dy = y - cy;
//        double dz = z - cz;
//        double ratio = Math.sqrt(dx * dx + dy * dy + dz * dz);
//
//        while (Math.abs(cx - x) > Math.abs(dx / ratio)) {
//            world.spawnParticle(EnumParticleTypes.TOWN_AURA, cx, cy, cz, 0.0D, 0.0D, 0.0D);
//            cx += dx * 0.1 / ratio;
//            cy += dy * 0.1 / ratio;
//            cz += dz * 0.1 / ratio;
//        }
    }


    // Registering only on client side
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void leftClickBlock(@Nonnull PlayerInteractEvent.LeftClickBlock event) {
        if(event.getSide() == Side.SERVER || event.getEntityPlayer() != Minecraft.getMinecraft().thePlayer)
            return;

        ItemStack stack = event.getItemStack();
        if(stack != null && stack.getItem().equals(this)) {
            leftClickOnBlockClient(event.getPos());
        }
    }

    // Registering only on client side
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void leftClickEmpty(@Nonnull PlayerInteractEvent.LeftClickEmpty event) {
        if(event.getSide() == Side.SERVER || event.getEntityPlayer() != Minecraft.getMinecraft().thePlayer)
            return;

        ItemStack stack = event.getItemStack();

        if(stack != null && stack.getItem().equals(this)) {
            EntityPlayer entityLiving = event.getEntityPlayer();
            RayTraceResult result = entityLiving.rayTrace(MultiToolConfig.reach, 0);
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                leftClickOnBlockClient(result.getBlockPos());
            }
        }
    }

    @SubscribeEvent
    public void teleportDrops(@Nonnull BlockEvent.HarvestDropsEvent event) {
        if(event.isCanceled())
            return;
        EntityPlayer player = event.getHarvester();
        if(player == null || event.getHarvester().getHeldItemMainhand().getItem() != this)
            return;

        World world = event.getWorld();
        float chance = event.getDropChance();
        for(ItemStack stack: event.getDrops()) {
            if (world.rand.nextFloat() <= chance) {
                EntityItem toSpawn = new EntityItem(world, player.posX, player.posY, player.posZ, stack);
                world.spawnEntityInWorld(toSpawn);
            }
        }
        event.getDrops().clear();
    }

    private void leftClickOnBlockClient(BlockPos pos) {
        IMessage message = new MultiToolLeftClickMessage(pos);
        Overloaded.proxy.networkWrapper.sendToServer(message);
    }

    public void leftClickOnBlockServer(@Nonnull World world, @Nonnull EntityPlayerMP player, @Nonnull BlockPos pos) {
        ItemStack itemStack = player.getHeldItem(EnumHand.MAIN_HAND);
        if(itemStack.getItem() != this || world.isAirBlock(pos)) {
            return;
        }

        player.setActiveHand(EnumHand.MAIN_HAND);

        if(player.isSneaking()) {
            NBTTagCompound tag  = itemStack.getTagCompound();
            if(tag == null) {
                tag = new NBTTagCompound();
            }
            IBlockState state = world.getBlockState(pos);
            Item item = Item.getItemFromBlock(state.getBlock());
            ItemStack stackToPlace = new ItemStack(item,1,state.getBlock().damageDropped(state));
            NBTTagCompound blockTag = new NBTTagCompound();
            stackToPlace.writeToNBT(blockTag);
            tag.setTag("Item", blockTag);
            itemStack.setTagCompound(tag);
            ITextComponent component = stackToPlace.getTextComponent();
            player.addChatMessage( new TextComponentString("Bound tool to ").appendSibling(component));
        } else {
            IHyperHandlerEnergy energy = itemStack.getCapability(HYPER_ENERGY_HANDLER, null);
            LongEnergyStack energyStack = energy.take(new LongEnergyStack(Long.MAX_VALUE),true);

            int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemStack);
            int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemStack);
            switch(breakAndUseEnergy(world, pos, energyStack,player,efficiency,unbreaking)) {
                case FAIL_REMOVE:
                    player.addChatMessage( new TextComponentString("Unable to break block, reason unknown"));
                    break;
                case FAIL_ENERGY:
                    player.addChatMessage( new TextComponentString("Unable to break block, not enough energy"));
                    break;
                case FAIL_UNBREAKABLE:
                    player.addChatMessage( new TextComponentString("Block is unbreakable"));
                    break;
                case SUCCESS:
                    break;
            }
            energy.give(energyStack,true);

        }
    }

    public void rightClickWithItem(@Nonnull World worldIn, @Nonnull EntityPlayerMP player, @Nonnull BlockPos pos, @Nonnull EnumFacing sideHit, float hitX, float hitY, float hitZ) {
        ItemStack multiTool = player.getHeldItemMainhand();

        if(multiTool.getItem() != this) {
            return;
        }

        NBTTagCompound tagCompound = multiTool.getTagCompound();

        if(tagCompound == null || !tagCompound.hasKey("Item")){
            player.addChatMessage( new TextComponentString("No block type selected to place."));
            return;
        }

        NBTTagCompound itemTag = tagCompound.getCompoundTag("Item");
        ItemStack blockStack = ItemStack.loadItemStackFromNBT(itemTag);
        if(blockStack == null || !(blockStack.getItem() instanceof ItemBlock)) {
            player.addChatMessage(new TextComponentString("No valid block type selected to place."));
            return;
        }

        IHyperHandlerEnergy energy = multiTool.getCapability(HYPER_ENERGY_HANDLER, null);
        LongEnergyStack energyStack = energy.take(new LongEnergyStack(Long.MAX_VALUE),true);

        Vec3i sideVector = sideHit.getDirectionVec();
        BlockPos newPosition = pos.add(sideVector);

        try {
            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                return;
            if (player.isSneaking()) {
                BlockPos playerPos = player.getPosition();
                switch (sideHit) {
                    case UP:
                        while (newPosition.getY() < playerPos.getY()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                                break;
                        }
                        break;
                    case DOWN:
                        while (newPosition.getY() > playerPos.getY()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                                break;
                        }
                        break;
                    case NORTH:
                        while (newPosition.getZ() > playerPos.getZ()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                                break;
                        }
                        break;
                    case SOUTH:
                        while (newPosition.getZ() < playerPos.getZ()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                                break;
                        }
                        break;
                    case EAST:
                        while (newPosition.getX() < playerPos.getX()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                                break;
                        }
                        break;
                    case WEST:
                        while (newPosition.getX() > playerPos.getX()) {
                            newPosition = newPosition.add(sideVector);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
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

    private boolean placeBlock(@Nonnull ItemStack searchStack, @Nonnull EntityPlayerMP player, @Nonnull World worldIn, @Nonnull BlockPos newPosition, @Nonnull EnumFacing facing, @Nonnull LongEnergyStack energyStack, float hitX, float hitY, float hitZ) {
        // Can we place a block at this Pos
        ItemBlock itemBlock = ((ItemBlock) searchStack.getItem());
        if(!worldIn.canBlockBePlaced(itemBlock.getBlock(), newPosition, false,facing,player,searchStack))
            return false;

        BlockEvent.PlaceEvent event = ForgeEventFactory.onPlayerBlockPlace(player,new BlockSnapshot(worldIn,newPosition, worldIn.getBlockState(newPosition)), facing, EnumHand.MAIN_HAND);
        if(event.isCanceled())
            return false;

        long distance = Math.round(player.getPosition().getDistance(newPosition.getX(),newPosition.getY(),newPosition.getZ()));
        long cost = MultiToolConfig.placeBaseCost + MultiToolConfig.costPerMeterAway * distance;

        if(cost < 0 || energyStack.amount < cost)
            return false;

        int foundStackSlot = findItemStack(searchStack, player);
        if(foundStackSlot == -1)
            return false;

        player.inventory.getStackInSlot(foundStackSlot);

        ItemStack foundStack = player.inventory.getStackInSlot(foundStackSlot);



        int i = itemBlock.getMetadata(foundStack.getMetadata());
        IBlockState iblockstate1 = itemBlock.block.getStateForPlacement(worldIn, newPosition, facing, hitX, hitY, hitZ, i, player, foundStack);

        if (itemBlock.placeBlockAt(foundStack, player, worldIn, newPosition, facing, hitX, hitY, hitZ, iblockstate1))
        {
            SoundType soundtype = worldIn.getBlockState(newPosition).getBlock().getSoundType(worldIn.getBlockState(newPosition), worldIn, newPosition, player);
            worldIn.playSound(null, newPosition, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            foundStack.splitStack(1);
            if(foundStack.stackSize == 0) {
                player.inventory.removeStackFromSlot(foundStackSlot);
            }
            return true;
        }

        return false;
    }

    private int findItemStack(@Nonnull ItemStack item, @Nonnull EntityPlayerMP player) {
        int size = player.inventory.getSizeInventory();
        for(int i = 0; i < size; i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if(stack != null  && stack.isItemEqual(item))
                return i;
        }

        return -1;
    }

    @Override
    @Nonnull
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new EnergyWrapper(stack);
    }

    private static final Set<String> toolClasses = com.google.common.collect.ImmutableSet.of(
      "pickaxe",
        "shovel",
        "axe"
    );

    @Override
    @Nonnull
    public Set<String> getToolClasses(ItemStack stack) {
        return toolClasses;
    }

    @Override
    public boolean canHarvestBlock(@Nonnull IBlockState state, ItemStack stack) {
        return true;
    }
}
