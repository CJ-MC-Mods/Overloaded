package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.LeftClickBlockMessage;
import com.cjm721.overloaded.network.packets.RightClickBlockMessage;
import com.cjm721.overloaded.util.BlockBreakResult;
import com.cjm721.overloaded.util.BlockPlaceResult;
import com.cjm721.overloaded.util.PlayerInteractionUtil;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IWorld;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
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
    super(
        new Properties()
            .addToolType(ToolType.AXE, Integer.MAX_VALUE)
            .addToolType(ToolType.PICKAXE, Integer.MAX_VALUE)
            .addToolType(ToolType.SHOVEL, Integer.MAX_VALUE));
    setRegistryName("multi_tool");
    //        setTranslationKey("multi_tool");
    //        setCreativeTab(OverloadedItemGroups.TECH);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
    return enchantment != null && enchantment.type == EnchantmentType.DIGGER;
  }

  @Override
  public int getItemEnchantability(ItemStack stack) {
    return 15;
  }

  @Override
  public boolean isEnchantable(@Nonnull ItemStack stack) {
    return this.getItemStackLimit(stack) == 1;
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "multi_tool"), null);
    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/items/ntool.png"),
        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void addInformation(
      ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new StringTextComponent("Assist Mode: " + getAssistMode().getName()));

    super.addInformation(stack, worldIn, tooltip, flagIn);
  }

  @Override
  public boolean onBlockDestroyed(
      ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
    LazyOptional<IEnergyStorage> storage = stack.getCapability(ENERGY, null);

    if (storage.isPresent()) {
      int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
      int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
      float breakCost =
          getBreakCost(
              worldIn.getBlockState(pos).getBlockHardness(worldIn, pos),
              efficiency,
              unbreaking,
              entityLiving == null ? 10 : getDistance(entityLiving, pos));

      storage.orElse(null).extractEnergy((int) Math.min(Integer.MAX_VALUE, breakCost), false);
    }

    return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
  }

  private double getDistance(@Nonnull LivingEntity entityLiving, @Nonnull BlockPos pos) {
    return Math.sqrt(entityLiving.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()));
  }

  @Override
  public boolean canHarvestBlock(BlockState blockIn) {
    return false;
  }

  //  @Override
  //  public boolean canDestroyBlockInCreative(
  //      World world, BlockPos pos, ItemStack stack, PlayerEntity player) {
  //    return player == null
  //        || super.canDestroyBlockInCreative(world, pos, stack, player) && !player.isSneaking();
  //  }

  @Override
  public float getDestroySpeed(ItemStack stack, BlockState state) {
    return 0f;
  }

  @Override
  @Nonnull
  @OnlyIn(Dist.CLIENT)
  public ActionResult<ItemStack> onItemRightClick(
      @Nonnull World worldIn, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
    if (worldIn.isRemote) {
      BlockRayTraceResult result =
          PlayerInteractionUtil.getBlockPlayerLookingAtClient(
              player, Minecraft.getInstance().getRenderPartialTicks());
      if (result != null) {
        Overloaded.proxy.networkWrapper.sendToServer(
            new RightClickBlockMessage(
                result.getPos(),
                result.getFace(),
                (float) result.getHitVec().x - result.getPos().getX(),
                (float) result.getHitVec().y - result.getPos().getY(),
                (float) result.getHitVec().z - result.getPos().getZ()));
      }
    }
    return ActionResult.newResult(ActionResultType.SUCCESS, player.getHeldItem(hand));
  }

  private float getBreakCost(float hardness, int efficiency, int unbreaking, double distance) {
    return (float)
        ((hardness
                * OverloadedConfig.INSTANCE.multiToolConfig.breakCostMultiplier
                / (efficiency + 1))
            + (OverloadedConfig.INSTANCE.multiToolConfig.breakBaseCost / (unbreaking + 1))
            + distance);
  }

  /** @return True if the break was successful, false otherwise */
  @Nonnull
  private BlockBreakResult breakAndUseEnergy(
      @Nonnull World worldIn,
      @Nonnull BlockPos blockPos,
      @Nonnull IEnergyStorage energy,
      @Nonnull ServerPlayerEntity player,
      int efficiency,
      int unbreaking) {
    BlockState state = worldIn.getBlockState(blockPos);
    // state = state.getBlock().getExtendedState(state, worldIn,blockPos);

    if (!player.abilities.isCreativeMode) {
      float hardness = state.getBlockHardness(worldIn, blockPos);
      if (hardness < 0) {
        return BlockBreakResult.FAIL_UNBREAKABLE;
      }

      float floatBreakCost =
          getBreakCost(hardness, efficiency, unbreaking, getDistance(player, blockPos));
      if (Float.isInfinite(floatBreakCost) || Float.isNaN(floatBreakCost))
        return BlockBreakResult.FAIL_ENERGY;

      int breakCost = Math.round(floatBreakCost);

      if (breakCost < 0 || energy.getEnergyStored() < breakCost) {
        return BlockBreakResult.FAIL_ENERGY;
      }
    }

    if (player.getDistanceSq(blockPos.getX(), blockPos.getY(), blockPos.getZ())
        > OverloadedConfig.INSTANCE.multiToolConfig.reach
            * OverloadedConfig.INSTANCE.multiToolConfig.reach) {
      return BlockBreakResult.FAIL_RANGE;
    }

    if (!state.getBlock().canEntityDestroy(state, worldIn, blockPos, player)) {
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

  //  public void drawParticleStreamTo(
  //      @Nonnull PlayerEntity source,
  //      @Nonnull Vec3d endingLocation,
  //      @Nonnull EnumParticleTypes type) {
  //    double xOffset = 0; // 0.25;
  //    double yOffset = -.25;
  //    double zOffset = 0; // .25;
  //
  //    Vec3d startingLocation = source.getPositionEyes(1).add(xOffset, yOffset, zOffset);
  //    startingLocation =
  //        startingLocation.add(source.getLookVec().rotateYaw((float) (Math.PI /
  // -2.0D)).scale(0.5D));
  //    Vec3d direction = endingLocation.subtract(startingLocation).normalize();
  //
  //    startingLocation = startingLocation.add(direction);
  //    World world = source.getEntityWorld();
  //    double distanceToEnd = endingLocation.distanceTo(startingLocation);
  //    // Make the reach check unnessicary * Change to for loop
  //    while (distanceToEnd > 0.3D
  //        && distanceToEnd < (OverloadedConfig.INSTANCE.multiToolConfig.reach * 2)) {
  //      world.spawnParticle(
  //          type,
  //          startingLocation.x,
  //          startingLocation.y,
  //          startingLocation.z,
  //          0,
  //          0,
  //          0); // direction.xCoord, direction.yCoord, direction.zCoord);
  //      startingLocation = startingLocation.add(direction.scale(0.25D));
  //      distanceToEnd = endingLocation.distanceTo(startingLocation);
  //    }
  //  }

  // Registering only on client side
  @SubscribeEvent(priority = EventPriority.LOWEST)
  @OnlyIn(Dist.CLIENT)
  public void leftClickBlock(@Nonnull PlayerInteractEvent.LeftClickBlock event) {
    if (event.getSide() == LogicalSide.SERVER
        || event.getEntityPlayer() != Minecraft.getInstance().player) return;

    ItemStack stack = event.getItemStack();
    if (stack.getItem().equals(this)) {
      //      leftClickOnBlockClient(event.getPos(), event.getHitVec());
    }
  }

  // Registering only on client side
  @SubscribeEvent(priority = EventPriority.LOWEST)
  @OnlyIn(Dist.CLIENT)
  public void leftClickEmpty(@Nonnull PlayerInteractEvent.LeftClickEmpty event) {
    if (event.getSide() == LogicalSide.SERVER
        || event.getEntityPlayer() != Minecraft.getInstance().player) return;

    ItemStack stack = event.getItemStack();

    if (stack.getItem().equals(this)) {
      PlayerEntity entityLiving = event.getEntityPlayer();
      BlockRayTraceResult result =
          PlayerInteractionUtil.getBlockPlayerLookingAtClient(
              entityLiving, Minecraft.getInstance().getRenderPartialTicks());
      if (result != null) {
        //        leftClickOnBlockClient(result.getPos(), result.hitVec);
      }
    }
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void teleportDrops(@Nonnull BlockEvent.HarvestDropsEvent event) {
    PlayerEntity player = event.getHarvester();
    if (player == null
        || event.getHarvester().getHeldItemMainhand().getItem() != this
        || event.getDrops() instanceof ImmutableList) return;

    IWorld world = event.getWorld();
    float chance = event.getDropChance();
    for (ItemStack stack : event.getDrops()) {
      if (world.getRandom().nextFloat() <= chance) {
        ItemHandlerHelper.giveItemToPlayer(player, stack);
      }
    }
    event.getDrops().clear();
  }

  @OnlyIn(Dist.CLIENT)
  private void leftClickOnBlockClient(BlockPos pos, Vec3d hitVec) {
    Overloaded.proxy.networkWrapper.sendToServer(new LeftClickBlockMessage(pos));

    //        PlayerEntitySP player = Minecraft.getMinecraft().player;
    //        drawParticleStreamTo(player, hitVec,
    // EnumParticleTypes.SMOKE_NORMAL);//EnumParticleTypes.TOWN_AURA
  }

  public void leftClickOnBlockServer(
      @Nonnull ServerPlayerEntity player, LeftClickBlockMessage message) {
    BlockPos pos = message.getPos();
    ServerWorld world = player.getServerWorld();
    ItemStack itemStack = player.getHeldItem(Hand.MAIN_HAND);
    if (itemStack.getItem() != this || world.isAirBlock(pos)) {
      return;
    }

    player.setActiveHand(Hand.MAIN_HAND);

    if (player.isSneaking()) {
      CompoundNBT tag = itemStack.getTag();
      if (tag == null) {
        tag = new CompoundNBT();
      }
      BlockState state = world.getBlockState(pos);
      Item item = Item.getItemFromBlock(state.getBlock());
      ItemStack stackToPlace = new ItemStack(() -> item, 1);
      CompoundNBT blockTag = new CompoundNBT();
      stackToPlace.write(blockTag);
      tag.put("Item", blockTag);
      itemStack.setTag(tag);
      ITextComponent component = stackToPlace.getTextComponent();
      player.sendStatusMessage(
          new StringTextComponent("Bound tool to ").appendSibling(component), true);
    } else {
      IEnergyStorage energy = itemStack.getCapability(ENERGY, null).orElse(null);

      int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemStack);
      int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, itemStack);
      switch (breakAndUseEnergy(world, pos, energy, player, efficiency, unbreaking)) {
        case FAIL_REMOVE:
          player.sendStatusMessage(
              new StringTextComponent("Unable to break block, reason unknown"), true);
          break;
        case FAIL_ENERGY:
          player.sendStatusMessage(
              new StringTextComponent("Unable to break block, not enough energy"), true);
          break;
        case FAIL_UNBREAKABLE:
          player.sendStatusMessage(new StringTextComponent("Block is unbreakable"), true);
          break;
        case FAIL_RANGE:
          player.sendStatusMessage(new StringTextComponent("Block is out of range."), true);
          break;
        case SUCCESS:
          break;
      }
    }
  }

  public void rightClickWithItem(
      @Nonnull ServerPlayerEntity player, RightClickBlockMessage message) {
    BlockPos pos = message.getPos();
    Direction sideHit = message.getHitSide();
    float hitX = message.getHitX();
    float hitY = message.getHitY();
    float hitZ = message.getHitZ();

    ServerWorld worldIn = player.getServerWorld();
    ItemStack multiTool = player.getHeldItemMainhand();

    if (multiTool.getItem() != this) {
      return;
    }

    ItemStack blockStack = getSelectedBlockItemStack(multiTool);

    if (blockStack.isEmpty()) {
      player.sendStatusMessage(new StringTextComponent("No block type selected to place."), true);
      return;
    }

    if (!(blockStack.getItem() instanceof BlockItem)) {
      player.sendStatusMessage(
          new StringTextComponent("No valid block type selected to place."), true);
      return;
    }

    IEnergyStorage energy = multiTool.getCapability(ENERGY, null).orElse(null);

    Vec3i sideVector = sideHit.getDirectionVec();
    BlockPos.MutableBlockPos newPosition = new BlockPos.MutableBlockPos(pos.add(sideVector));

    switch (placeBlock(
        blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ)) {
      case FAIL_PREREQUISITE:
        player.sendStatusMessage(new StringTextComponent("Do not have the required items"), true);
        return;
      case FAIL_DENY:
        player.sendStatusMessage(new StringTextComponent("Unable to place blocks"), true);
        return;
      case FAIL_RANGE:
        player.sendStatusMessage(new StringTextComponent("To far away"), true);
        return;
      case FAIL_ENERGY:
        player.sendStatusMessage(new StringTextComponent("Not enough energy"), true);
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
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
        case DOWN:
          while (newPosition.getY() > playerPos.getY()) {
            newPosition.move(sideHit);
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
        case NORTH:
          while (newPosition.getZ() > playerPos.getZ()) {
            newPosition.move(sideHit);
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
        case SOUTH:
          while (newPosition.getZ() < playerPos.getZ()) {
            newPosition.move(sideHit);
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
        case EAST:
          while (newPosition.getX() < playerPos.getX()) {
            newPosition.move(sideHit);
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
        case WEST:
          while (newPosition.getX() > playerPos.getX()) {
            newPosition.move(sideHit);
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
      }
    }
  }

  @Nonnull
  public ItemStack getSelectedBlockItemStack(ItemStack multiTool) {
    CompoundNBT tagCompound = multiTool.getTag();

    if (tagCompound == null || !tagCompound.contains("Item")) {
      return ItemStack.EMPTY;
    }

    CompoundNBT itemTag = tagCompound.getCompound("Item");
    return ItemStack.read(itemTag);
  }

  private static final Set<String> toolClasses =
      com.google.common.collect.ImmutableSet.of("pickaxe", "shovel", "axe");

  @Override
  public boolean canHarvestBlock(ItemStack stack, BlockState state) {
    return true;
  }

  @Override
  @Nonnull
  public ITextComponent getDisplayName(@Nonnull ItemStack stack) {
    return super.getDisplayName(stack).applyTextStyle(TextFormatting.GOLD);
  }
}
