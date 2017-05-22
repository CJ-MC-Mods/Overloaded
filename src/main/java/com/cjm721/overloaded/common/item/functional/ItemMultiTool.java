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
import com.cjm721.overloaded.common.util.LongEnergyWrapper;
import com.cjm721.overloaded.common.util.PlayerInteractionUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
import static com.cjm721.overloaded.common.util.PlayerInteractionUtil.placeBlock;

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
        return enchantment != null && enchantment.type == EnumEnchantmentType.DIGGER;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return 15;
    }

    @Override
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return this.getItemStackLimit(stack) == 1;
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
        return true;
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        return player == null || super.canDestroyBlockInCreative(world, pos, stack, player) && !player.isSneaking();
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        if(worldIn.isRemote) {
            // TODO Make distance a config option
            RayTraceResult result = worldIn.rayTraceBlocks(player.getPositionEyes(1), player.getPositionVector().add(player.getLookVec().scale(MultiToolConfig.reach)));
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
//                ((ItemBlock)Item.getItemFromBlock(Blocks.GLASS)).canPlaceBlockOnSide(worldIn, result.getBlockPos(), result.sideHit,player, null);
                Overloaded.proxy.networkWrapper.sendToServer(new MultiToolRightClickMessage(result.getBlockPos(),result.sideHit, (float) result.hitVec.xCoord - result.getBlockPos().getX(), (float) result.hitVec.yCoord - result.getBlockPos().getY(), (float) result.hitVec.zCoord - result.getBlockPos().getZ()));

            }
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

        float floatBreakCost = (hardness * MultiToolConfig.breakCostMultiplier / (efficiency + 1)) + (MultiToolConfig.breakBaseCost / (unbreaking + 1)) + (float)blockPos.getDistance((int)player.posX,(int)player.posY,(int)player.posZ);
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

        boolean result = PlayerInteractionUtil.tryHarvestBlock(player,worldIn,blockPos);
        if (result) {
            energyStack.amount -= breakCost;
            return BlockResult.SUCCESS;
        }
        return BlockResult.FAIL_REMOVE;
    }

    public void drawParticleStreamTo(EntityPlayer source, Vec3d endingLocation, EnumParticleTypes type) {
        double xOffset = 0;//0.25;
        double yOffset = -.25;
        double zOffset = 0;//.25;

        Vec3d startingLocation = source.getPositionEyes(1).addVector(xOffset,yOffset,zOffset);
        startingLocation = startingLocation.add(source.getLookVec().rotateYaw((float)(Math.PI/-2.0D)).scale(0.5D));
        Vec3d direction = endingLocation.subtract(startingLocation).normalize();

        startingLocation = startingLocation.add(direction);
        World world = source.getEntityWorld();
        double distanceToEnd = endingLocation.distanceTo(startingLocation);
        // Make the reach check unnessicary * Change to for loop
        while (distanceToEnd > 0.3D && distanceToEnd < (MultiToolConfig.reach * 2)) {
            world.spawnParticle(type, startingLocation.xCoord, startingLocation.yCoord, startingLocation.zCoord, 0,0,0);//direction.xCoord, direction.yCoord, direction.zCoord);
            startingLocation = startingLocation.add(direction.scale(0.25D));
            distanceToEnd = endingLocation.distanceTo(startingLocation);
        }
    }


    // Registering only on client side
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void leftClickBlock(@Nonnull PlayerInteractEvent.LeftClickBlock event) {
        if(event.getSide() == Side.SERVER || event.getEntityPlayer() != Minecraft.getMinecraft().player)
            return;

        ItemStack stack = event.getItemStack();
        if(stack.getItem().equals(this)) {
            leftClickOnBlockClient(event.getPos(), event.getHitVec());
        }
    }

    // Registering only on client side
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void leftClickEmpty(@Nonnull PlayerInteractEvent.LeftClickEmpty event) {
        if(event.getSide() == Side.SERVER || event.getEntityPlayer() != Minecraft.getMinecraft().player)
            return;

        ItemStack stack = event.getItemStack();

        if(stack.getItem().equals(this)) {
            EntityPlayer entityLiving = event.getEntityPlayer();
            RayTraceResult result = entityLiving.rayTrace(MultiToolConfig.reach, 0);
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                leftClickOnBlockClient(result.getBlockPos(), result.hitVec);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
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
                world.spawnEntity(toSpawn);
            }
        }
        event.getDrops().clear();
    }

    @SideOnly(Side.CLIENT)
    private void leftClickOnBlockClient(BlockPos pos, Vec3d hitVec) {
        IMessage message = new MultiToolLeftClickMessage(pos);
        Overloaded.proxy.networkWrapper.sendToServer(message);

        EntityPlayerSP player = Minecraft.getMinecraft().player;
         drawParticleStreamTo(player, hitVec, EnumParticleTypes.SMOKE_NORMAL);//EnumParticleTypes.TOWN_AURA
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
            player.sendStatusMessage( new TextComponentString("Bound tool to ").appendSibling(component), true);
        } else {
            IHyperHandlerEnergy energy = itemStack.getCapability(HYPER_ENERGY_HANDLER, null);
            LongEnergyStack energyStack = energy.take(new LongEnergyStack(Long.MAX_VALUE),true);
            try {
                int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemStack);
                int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemStack);
                switch(breakAndUseEnergy(world, pos, energyStack,player,efficiency,unbreaking)) {
                    case FAIL_REMOVE:
                        player.sendStatusMessage( new TextComponentString("Unable to break block, reason unknown"), true);
                        break;
                    case FAIL_ENERGY:
                        player.sendStatusMessage( new TextComponentString("Unable to break block, not enough energy"), true);
                        break;
                    case FAIL_UNBREAKABLE:
                        player.sendStatusMessage( new TextComponentString("Block is unbreakable"),true);
                        break;
                    case SUCCESS:
                        break;
                }
            } finally {
                energy.give(energyStack,true);
            }
        }
    }

    public void rightClickWithItem(@Nonnull World worldIn, @Nonnull EntityPlayerMP player, @Nonnull BlockPos pos, @Nonnull EnumFacing sideHit, float hitX, float hitY, float hitZ) {
        ItemStack multiTool = player.getHeldItemMainhand();

        if(multiTool.getItem() != this) {
            return;
        }

        NBTTagCompound tagCompound = multiTool.getTagCompound();

        if(tagCompound == null || !tagCompound.hasKey("Item")){
            player.sendStatusMessage( new TextComponentString("No block type selected to place."), true);
            return;
        }

        NBTTagCompound itemTag = tagCompound.getCompoundTag("Item");
        ItemStack blockStack = new ItemStack(itemTag);
        if(!(blockStack.getItem() instanceof ItemBlock)) {
            player.sendStatusMessage(new TextComponentString("No valid block type selected to place."), true);
            return;
        }

        IHyperHandlerEnergy energy = multiTool.getCapability(HYPER_ENERGY_HANDLER, null);
        LongEnergyStack energyStack = energy.take(new LongEnergyStack(Long.MAX_VALUE),true);

        Vec3i sideVector = sideHit.getDirectionVec();
        BlockPos.MutableBlockPos newPosition = new BlockPos.MutableBlockPos(pos.add(sideVector));
        try {
            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                return;
            if (player.isSneaking()) {
                BlockPos playerPos = player.getPosition();
                switch (sideHit) {
                    case UP:
                        while (newPosition.getY() < playerPos.getY()) {
                            newPosition.move(sideHit);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                                break;
                        }
                        break;
                    case DOWN:
                        while (newPosition.getY() > playerPos.getY()) {
                            newPosition.move(sideHit);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                                break;
                        }
                        break;
                    case NORTH:
                        while (newPosition.getZ() > playerPos.getZ()) {
                            newPosition.move(sideHit);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                                break;
                        }
                        break;
                    case SOUTH:
                        while (newPosition.getZ() < playerPos.getZ()) {
                            newPosition.move(sideHit);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                                break;
                        }
                        break;
                    case EAST:
                        while (newPosition.getX() < playerPos.getX()) {
                            newPosition.move(sideHit);
                            if (!placeBlock(blockStack, player, worldIn, newPosition, sideHit, energyStack, hitX, hitY, hitZ))
                                break;
                        }
                        break;
                    case WEST:
                        while (newPosition.getX() > playerPos.getX()) {
                            newPosition.move(sideHit);
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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new LongEnergyWrapper(stack);
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

//
//    @SubscribeEvent
//    @SideOnly(Side.CLIENT)
//    public void renderBlockOverlayEvent(RenderBlockOverlayEvent event) {
//        EntityPlayer player = event.getPlayer();
//        if(player == null || player.getHeldItemMainhand().getItem() != this)
//            return;
//
//        RayTraceResult result = player.rayTrace(128,event.getRenderPartialTicks());
//
//        if(result == null)
//            return;
//
//        BlockPos toRenderAt = result.getBlockPos().add(result.sideHit.getDirectionVec());
//    }
}
