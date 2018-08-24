package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.network.packets.LeftClickBlockMessage;
import com.cjm721.overloaded.network.packets.RightClickBlockMessage;
import com.cjm721.overloaded.util.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.client.render.item.RenderMultiToolAssist.getAssistMode;
import static com.cjm721.overloaded.util.PlayerInteractionUtil.placeBlock;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class ItemMultiTool extends PowerModItem {

    public ItemMultiTool() {
        setRegistryName("multi_tool");
        setUnlocalizedName("multi_tool");
        setCreativeTab(OverloadedCreativeTabs.TECH);
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

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/items/ntool.png"),
                Overloaded.cachedConfig.textureResolutions.itemResolution);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Assist Mode: " + getAssistMode().getName());

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        IEnergyStorage storage = stack.getCapability(ENERGY, null);

        if (storage != null) {
            int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
            float breakCost = getBreakCost(worldIn.getBlockState(pos).getBlockHardness(worldIn, pos), efficiency, unbreaking, entityLiving == null ? 10 : getDistance(entityLiving, pos));

            storage.extractEnergy((int) Math.min(Integer.MAX_VALUE, breakCost), false);
        }

        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    private double getDistance(@Nonnull EntityLivingBase entityLiving, @Nonnull BlockPos pos) {
        return entityLiving.getDistance(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return false;
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        return player == null || super.canDestroyBlockInCreative(world, pos, stack, player) && !player.isSneaking();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return 0f;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        if (worldIn.isRemote) {
            RayTraceResult result = PlayerInteractionUtil.getBlockPlayerLookingAtClient(player, Minecraft.getMinecraft().getRenderPartialTicks());
            if (result != null) {
                Overloaded.proxy.networkWrapper.sendToServer(new RightClickBlockMessage(result.getBlockPos(), result.sideHit, (float) result.hitVec.x - result.getBlockPos().getX(), (float) result.hitVec.y - result.getBlockPos().getY(), (float) result.hitVec.z - result.getBlockPos().getZ()));
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    private float getBreakCost(float hardness, int efficiency, int unbreaking, double distance) {
        return (float) ((hardness * Overloaded.cachedConfig.multiToolConfig.breakCostMultiplier / (efficiency + 1)) + (Overloaded.cachedConfig.multiToolConfig.breakBaseCost / (unbreaking + 1)) + distance);
    }

    /**
     * @return True if the break was successful, false otherwise
     */
    @Nonnull
    private BlockBreakResult breakAndUseEnergy(@Nonnull World worldIn, @Nonnull BlockPos blockPos, @Nonnull IEnergyStorage energy, @Nonnull EntityPlayerMP player, int efficiency, int unbreaking) {
        IBlockState state = worldIn.getBlockState(blockPos);
        //state = state.getBlock().getExtendedState(state, worldIn,blockPos);

        if (!player.capabilities.isCreativeMode) {
            float hardness = state.getBlockHardness(worldIn, blockPos);
            if (hardness < 0) {
                return BlockBreakResult.FAIL_UNBREAKABLE;
            }

            float floatBreakCost = getBreakCost(hardness, efficiency, unbreaking, getDistance(player, blockPos));
            if (Float.isInfinite(floatBreakCost) || Float.isNaN(floatBreakCost))
                return BlockBreakResult.FAIL_ENERGY;

            int breakCost = Math.round(floatBreakCost);

            if (breakCost < 0 || energy.getEnergyStored() < breakCost) {
                return BlockBreakResult.FAIL_ENERGY;
            }
        }

        if(player.getDistanceSq(blockPos) > Overloaded.cachedConfig.multiToolConfig.reach * Overloaded.cachedConfig.multiToolConfig.reach ) {
            return BlockBreakResult.FAIL_RANGE;
        }

        if(!state.getBlock().canEntityDestroy(state,worldIn,blockPos,player)) {
            return BlockBreakResult.FAIL_REMOVE;
        }

        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(worldIn, blockPos, state, player);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            return BlockBreakResult.FAIL_REMOVE;
        }

        boolean result = PlayerInteractionUtil.tryHarvestBlock(player, worldIn, blockPos);
        return result ? BlockBreakResult.SUCCESS : BlockBreakResult.FAIL_REMOVE;
    }

    public void drawParticleStreamTo(@Nonnull EntityPlayer source, @Nonnull Vec3d endingLocation, @Nonnull EnumParticleTypes type) {
        double xOffset = 0;//0.25;
        double yOffset = -.25;
        double zOffset = 0;//.25;

        Vec3d startingLocation = source.getPositionEyes(1).addVector(xOffset, yOffset, zOffset);
        startingLocation = startingLocation.add(source.getLookVec().rotateYaw((float) (Math.PI / -2.0D)).scale(0.5D));
        Vec3d direction = endingLocation.subtract(startingLocation).normalize();

        startingLocation = startingLocation.add(direction);
        World world = source.getEntityWorld();
        double distanceToEnd = endingLocation.distanceTo(startingLocation);
        // Make the reach check unnessicary * Change to for loop
        while (distanceToEnd > 0.3D && distanceToEnd < (Overloaded.cachedConfig.multiToolConfig.reach * 2)) {
            world.spawnParticle(type, startingLocation.x, startingLocation.y, startingLocation.z, 0, 0, 0);//direction.xCoord, direction.yCoord, direction.zCoord);
            startingLocation = startingLocation.add(direction.scale(0.25D));
            distanceToEnd = endingLocation.distanceTo(startingLocation);
        }
    }


    // Registering only on client side
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void leftClickBlock(@Nonnull PlayerInteractEvent.LeftClickBlock event) {
        if (event.getSide() == Side.SERVER || event.getEntityPlayer() != Minecraft.getMinecraft().player)
            return;

        ItemStack stack = event.getItemStack();
        if (stack.getItem().equals(this)) {
            leftClickOnBlockClient(event.getPos(), event.getHitVec());
        }
    }

    // Registering only on client side
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void leftClickEmpty(@Nonnull PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getSide() == Side.SERVER || event.getEntityPlayer() != Minecraft.getMinecraft().player)
            return;

        ItemStack stack = event.getItemStack();

        if (stack.getItem().equals(this)) {
            EntityPlayer entityLiving = event.getEntityPlayer();
            RayTraceResult result = PlayerInteractionUtil.getBlockPlayerLookingAtClient(entityLiving, Minecraft.getMinecraft().getRenderPartialTicks());
            if (result != null) {
                leftClickOnBlockClient(result.getBlockPos(), result.hitVec);
            }
        }
    }




    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void teleportDrops(@Nonnull BlockEvent.HarvestDropsEvent event) {
        EntityPlayer player = event.getHarvester();
        if (player == null || event.getHarvester().getHeldItemMainhand().getItem() != this || event.getDrops() instanceof ImmutableList)
            return;

        World world = event.getWorld();
        float chance = event.getDropChance();
        for (ItemStack stack : event.getDrops()) {
            if (world.rand.nextFloat() <= chance) {
                ItemHandlerHelper.giveItemToPlayer(player, stack);
            }
        }
        event.getDrops().clear();
    }

    @SideOnly(Side.CLIENT)
    private void leftClickOnBlockClient(BlockPos pos, Vec3d hitVec) {
        IMessage message = new LeftClickBlockMessage(pos);
        Overloaded.proxy.networkWrapper.sendToServer(message);

//        EntityPlayerSP player = Minecraft.getMinecraft().player;
//        drawParticleStreamTo(player, hitVec, EnumParticleTypes.SMOKE_NORMAL);//EnumParticleTypes.TOWN_AURA
    }

    public void leftClickOnBlockServer(@Nonnull EntityPlayerMP player, LeftClickBlockMessage message) {
        BlockPos pos = message.getPos();
        WorldServer world = player.getServerWorld();
        ItemStack itemStack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (itemStack.getItem() != this || world.isAirBlock(pos)) {
            return;
        }

        player.setActiveHand(EnumHand.MAIN_HAND);

        if (player.isSneaking()) {
            NBTTagCompound tag = itemStack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
            }
            IBlockState state = world.getBlockState(pos);
            Item item = Item.getItemFromBlock(state.getBlock());
            ItemStack stackToPlace = new ItemStack(item, 1, state.getBlock().damageDropped(state));
            NBTTagCompound blockTag = new NBTTagCompound();
            stackToPlace.writeToNBT(blockTag);
            tag.setTag("Item", blockTag);
            itemStack.setTagCompound(tag);
            ITextComponent component = stackToPlace.getTextComponent();
            player.sendStatusMessage(new TextComponentString("Bound tool to ").appendSibling(component), true);
        } else {
            IEnergyStorage energy = itemStack.getCapability(ENERGY, null);

            int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemStack);
            int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemStack);
            switch (breakAndUseEnergy(world, pos, energy, player, efficiency, unbreaking)) {
                case FAIL_REMOVE:
                    player.sendStatusMessage(new TextComponentString("Unable to break block, reason unknown"), true);
                    break;
                case FAIL_ENERGY:
                    player.sendStatusMessage(new TextComponentString("Unable to break block, not enough energy"), true);
                    break;
                case FAIL_UNBREAKABLE:
                    player.sendStatusMessage(new TextComponentString("Block is unbreakable"), true);
                    break;
                case FAIL_RANGE:
                    player.sendStatusMessage(new TextComponentString("Block is out of range."), true);
                    break;
                case SUCCESS:
                    break;
            }
        }
    }

    public void rightClickWithItem(@Nonnull EntityPlayerMP player, RightClickBlockMessage message) {
        BlockPos pos = message.getPos();
        EnumFacing sideHit = message.getHitSide();
        float hitX = message.getHitX();
        float hitY = message.getHitY();
        float hitZ = message.getHitZ();

        WorldServer worldIn = player.getServerWorld();
        ItemStack multiTool = player.getHeldItemMainhand();

        if (multiTool.getItem() != this) {
            return;
        }

        ItemStack blockStack = getSelectedBlockItemStack(multiTool);

        if (blockStack.isEmpty()) {
            player.sendStatusMessage(new TextComponentString("No block type selected to place."), true);
            return;
        }

        if (!(blockStack.getItem() instanceof ItemBlock)) {
            player.sendStatusMessage(new TextComponentString("No valid block type selected to place."), true);
            return;
        }

        IEnergyStorage energy = multiTool.getCapability(ENERGY, null);

        Vec3i sideVector = sideHit.getDirectionVec();
        BlockPos.MutableBlockPos newPosition = new BlockPos.MutableBlockPos(pos.add(sideVector));

        switch(placeBlock(blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ)) {
            case FAIL_PREREQUISITE:
                player.sendStatusMessage(new TextComponentString("Do not have the required items"), true);
                return;
            case FAIL_DENY:
                player.sendStatusMessage(new TextComponentString("Unable to place blocks"), true);
                return;
            case FAIL_RANGE:
                player.sendStatusMessage(new TextComponentString("To far away"), true);
                return;
            case FAIL_ENERGY:
                player.sendStatusMessage(new TextComponentString("Not enough energy"), true);
                return;
            case SUCCESS:
                // Ok Continue
        }
        if (player.isSneaking()) {
            BlockPos playerPos = player.getPosition();
            switch (sideHit) {
                case UP:
                    while (newPosition.getY() < playerPos.getY()) {
                        newPosition.move(sideHit);
                        if (placeBlock(blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ) != BlockPlaceResult.SUCCESS)
                            break;
                    }
                    break;
                case DOWN:
                    while (newPosition.getY() > playerPos.getY()) {
                        newPosition.move(sideHit);
                        if (placeBlock(blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ) != BlockPlaceResult.SUCCESS)
                            break;
                    }
                    break;
                case NORTH:
                    while (newPosition.getZ() > playerPos.getZ()) {
                        newPosition.move(sideHit);
                        if (placeBlock(blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ) != BlockPlaceResult.SUCCESS)
                            break;
                    }
                    break;
                case SOUTH:
                    while (newPosition.getZ() < playerPos.getZ()) {
                        newPosition.move(sideHit);
                        if (placeBlock(blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ) != BlockPlaceResult.SUCCESS)
                            break;
                    }
                    break;
                case EAST:
                    while (newPosition.getX() < playerPos.getX()) {
                        newPosition.move(sideHit);
                        if (placeBlock(blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ) != BlockPlaceResult.SUCCESS)
                            break;
                    }
                    break;
                case WEST:
                    while (newPosition.getX() > playerPos.getX()) {
                        newPosition.move(sideHit);
                        if (placeBlock(blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ) != BlockPlaceResult.SUCCESS)
                            break;
                    }
                    break;
            }
        }
    }

    @Nonnull
    public ItemStack getSelectedBlockItemStack(ItemStack multiTool) {
        NBTTagCompound tagCompound = multiTool.getTagCompound();

        if (tagCompound == null || !tagCompound.hasKey("Item")) {
            return ItemStack.EMPTY;
        }

        NBTTagCompound itemTag = tagCompound.getCompoundTag("Item");
        return new ItemStack(itemTag);
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

    @Override
    @Nonnull
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        return TextFormatting.GOLD + super.getItemStackDisplayName(stack);
    }
}
