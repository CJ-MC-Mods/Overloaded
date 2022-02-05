package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.network.packets.LeftClickBlockMessage;
import com.cjm721.overloaded.network.packets.RightClickBlockMessage;
import com.cjm721.overloaded.util.BlockBreakResult;
import com.cjm721.overloaded.util.BlockPlaceResult;
import com.cjm721.overloaded.util.PlayerInteractionUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.client.render.item.RenderMultiToolAssist.getAssistMode;
import static com.cjm721.overloaded.util.PlayerInteractionUtil.placeBlock;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

import net.minecraft.item.Item.Properties;

public class ItemMultiTool extends PowerModItem {

  public ItemMultiTool() {
    super(
        new Properties()
            .addToolType(ToolType.AXE, Integer.MAX_VALUE)
            .addToolType(ToolType.PICKAXE, Integer.MAX_VALUE)
            .addToolType(ToolType.SHOVEL, Integer.MAX_VALUE));
    setRegistryName("multi_tool");
  }

  private static double getDistance(@Nonnull LivingEntity entityLiving, @Nonnull BlockPos pos) {
    return Math.sqrt(entityLiving.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()));
  }

  private static float getBreakCost(
      float hardness, int efficiency, int unbreaking, double distance) {
    return (float)
        ((hardness
                * OverloadedConfig.INSTANCE.multiToolConfig.breakCostMultiplier
                / (efficiency + 1))
            + (OverloadedConfig.INSTANCE.multiToolConfig.breakBaseCost / (unbreaking + 1))
            + distance);
  }

  /** @return True if the break was successful, false otherwise */
  @Nonnull
  private static BlockBreakResult breakAndUseEnergy(
      @Nonnull ServerWorld worldIn,
      @Nonnull BlockPos blockPos,
      @Nonnull IEnergyStorage energy,
      @Nonnull ServerPlayerEntity player,
      int efficiency,
      int unbreaking) {
    BlockState state = worldIn.getBlockState(blockPos);

    if (!player.abilities.instabuild) {
      float hardness = state.getDestroySpeed(worldIn, blockPos);
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

    if (player.distanceToSqr(blockPos.getX(), blockPos.getY(), blockPos.getZ())
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

  @OnlyIn(Dist.CLIENT)
  private static void leftClickOnBlockClient(BlockPos pos) {
    Overloaded.proxy.networkWrapper.sendToServer(new LeftClickBlockMessage(pos));
    //            PlayerEntitySP player = Minecraft.getMinecraft().player;
    //            drawParticleStreamTo(player, hitVec,
    //     EnumParticleTypes.SMOKE_NORMAL);//EnumParticleTypes.TOWN_AURA
  }

  public static void leftClickOnBlockServer(
      @Nonnull ServerPlayerEntity player, LeftClickBlockMessage message) {
    BlockPos pos = message.getPos();
    ServerWorld world = player.getLevel();
    ItemStack itemStack = player.getItemInHand(Hand.MAIN_HAND);
    if (itemStack.getItem() != ModItems.multiTool || world.isEmptyBlock(pos)) {
      return;
    }

    player.startUsingItem(Hand.MAIN_HAND);

    if (player.isShiftKeyDown()) {
      CompoundNBT tag = itemStack.getTag();
      if (tag == null) {
        tag = new CompoundNBT();
      }
      BlockState state = world.getBlockState(pos);
      Item item = Item.byBlock(state.getBlock());
      ItemStack stackToPlace = new ItemStack(() -> item, 1);
      CompoundNBT blockTag = new CompoundNBT();
      stackToPlace.save(blockTag);
      tag.put("Item", blockTag);
      itemStack.setTag(tag);
      ITextComponent component = stackToPlace.getDisplayName();
      player.displayClientMessage(
          new StringTextComponent("Bound tool to ").append(component), true);
    } else {
      LazyOptional<IEnergyStorage> opEnergy = itemStack.getCapability(ENERGY);
      if (!opEnergy.isPresent()) {
        Overloaded.logger.warn("MultiTool has no Energy Capability? NBT: " + itemStack.getTag());
        return;
      }

      // Used to catch item spawn to teleport
      RegistryKey<World> worldId = world.dimension();
      CommonSideEvents.enabled = true;
      CommonSideEvents.world = worldId;
      CommonSideEvents.pos = pos;
      CommonSideEvents.uuid = player.getUUID();

      IEnergyStorage energy = opEnergy.orElseThrow(() -> new RuntimeException("Impossible Error"));
      int efficiency = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, itemStack);
      int unbreaking = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, itemStack);
      switch (breakAndUseEnergy(world, pos, energy, player, efficiency, unbreaking)) {
        case FAIL_REMOVE:
          player.displayClientMessage(
              new StringTextComponent("Unable to break block, reason unknown"), true);
          break;
        case FAIL_ENERGY:
          player.displayClientMessage(
              new StringTextComponent("Unable to break block, not enough energy"), true);
          break;
        case FAIL_UNBREAKABLE:
          player.displayClientMessage(new StringTextComponent("Block is unbreakable"), true);
          break;
        case FAIL_RANGE:
          player.displayClientMessage(new StringTextComponent("Block is out of range."), true);
          break;
        case SUCCESS:
          break;
      }
      CommonSideEvents.enabled = false;
    }
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
    return enchantment != null && enchantment.category == EnchantmentType.DIGGER;
  }

  @Override
  public int getItemEnchantability(ItemStack stack) {
    return 15;
  }

  @Override
  public boolean isEnchantable(@Nonnull ItemStack stack) {
    return stack.getCount() == 1;
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "multi_tool"), null);
    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/multi_tool.png"),
        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void appendHoverText(
      ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new StringTextComponent("Assist Mode: " + getAssistMode().getName()));
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
  }

  @Override
  public boolean mineBlock(
      ItemStack stack, @Nonnull World worldIn, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entityLiving) {
    LazyOptional<IEnergyStorage> storage = stack.getCapability(ENERGY, null);

    if (storage.isPresent()) {
      int efficiency = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack);
      int unbreaking = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, stack);
      float breakCost =
          getBreakCost(
              worldIn.getBlockState(pos).getDestroySpeed(worldIn, pos),
              efficiency,
              unbreaking,
              entityLiving == null ? 10 : getDistance(entityLiving, pos));

      storage
          .orElseThrow(() -> new RuntimeException("Impossible Condition"))
          .extractEnergy((int) Math.min(Integer.MAX_VALUE, breakCost), false);
    }

    return super.mineBlock(stack, worldIn, state, pos, entityLiving);
  }

  @Override
  public boolean isCorrectToolForDrops(@Nonnull BlockState blockIn) {
    return false;
  }

  @Override
  public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull BlockState state) {
    return 0f;
  }

  @Override
  @Nonnull
  @OnlyIn(Dist.CLIENT)
  public ActionResult<ItemStack> use(
      @Nonnull World worldIn, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
    if (worldIn.isClientSide) {
      BlockRayTraceResult result =
          PlayerInteractionUtil.getBlockPlayerLookingAtClient(
              player, Minecraft.getInstance().getFrameTime());
      if (result.getType() == RayTraceResult.Type.BLOCK) {
        Overloaded.proxy.networkWrapper.sendToServer(
            new RightClickBlockMessage(
                result.getBlockPos(),
                result.getDirection(),
                (float) result.getLocation().x - result.getBlockPos().getX(),
                (float) result.getLocation().y - result.getBlockPos().getY(),
                (float) result.getLocation().z - result.getBlockPos().getZ()));
      }
    }
    return ActionResult.sidedSuccess(player.getItemInHand(hand), true);
  }

  public void rightClickWithItem(
      @Nonnull ServerPlayerEntity player, RightClickBlockMessage message) {
    BlockPos pos = message.getPos();
    Direction sideHit = message.getHitSide();
    float hitX = message.getHitX();
    float hitY = message.getHitY();
    float hitZ = message.getHitZ();

    ServerWorld worldIn = player.getLevel();
    ItemStack multiTool = player.getMainHandItem();

    if (multiTool.getItem() != this) {
      return;
    }

    ItemStack blockStack = getSelectedBlockItemStack(multiTool);

    if (blockStack.isEmpty()) {
      player.displayClientMessage(new StringTextComponent("No block type selected to place."), true);
      return;
    }

    if (!(blockStack.getItem() instanceof BlockItem)) {
      player.displayClientMessage(
          new StringTextComponent("No valid block type selected to place."), true);
      return;
    }

    LazyOptional<IEnergyStorage> opEnergy = multiTool.getCapability(ENERGY);
    if (!opEnergy.isPresent()) {
      Overloaded.logger.warn("MultiTool has no Energy Capability? NBT: " + multiTool.getTag());
      return;
    }

    IEnergyStorage energy =
        opEnergy.orElseThrow(() -> new RuntimeException("Impossible Condition"));

    Vector3i sideVector = sideHit.getNormal();
    BlockPos.Mutable newPosition = pos.offset(sideVector).mutable();

    switch (placeBlock(
        blockStack, player, worldIn, newPosition, sideHit, energy, hitX, hitY, hitZ)) {
      case FAIL_PREREQUISITE:
        player.displayClientMessage(new StringTextComponent("Do not have the required items"), true);
        return;
      case FAIL_DENY:
        player.displayClientMessage(new StringTextComponent("Unable to place blocks"), true);
        return;
      case FAIL_RANGE:
        player.displayClientMessage(new StringTextComponent("To far away"), true);
        return;
      case FAIL_ENERGY:
        player.displayClientMessage(new StringTextComponent("Not enough energy"), true);
        return;
      case SUCCESS:
        // Ok Continue
    }
    if (player.isShiftKeyDown()) {
      BlockPos playerPos = player.blockPosition();
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

  @Override
  public boolean canAttackBlock(
      @Nonnull BlockState p_195938_1_, @Nonnull World p_195938_2_, @Nonnull BlockPos p_195938_3_, PlayerEntity p_195938_4_) {
    return !p_195938_4_.isShiftKeyDown();
  }

  @Nonnull
  public ItemStack getSelectedBlockItemStack(ItemStack multiTool) {
    CompoundNBT tagCompound = multiTool.getTag();

    if (tagCompound == null || !tagCompound.contains("Item")) {
      return ItemStack.EMPTY;
    }

    CompoundNBT itemTag = tagCompound.getCompound("Item");
    return ItemStack.of(itemTag);
  }

  @Override
  public boolean canHarvestBlock(ItemStack stack, BlockState state) {
    return true;
  }

  @Override
  @Nonnull
  public ITextComponent getName(@Nonnull ItemStack stack) {
    ITextComponent text = super.getName(stack);
    text.getStyle().applyFormat(TextFormatting.GOLD);
    return text;
  }

  @Mod.EventBusSubscriber(
      value = Dist.CLIENT,
      modid = MODID,
      bus = Mod.EventBusSubscriber.Bus.FORGE)
  public static class ClientSideEvents {
    @SubscribeEvent
    public static void leftClickBlock(@Nonnull PlayerInteractEvent.LeftClickBlock event) {
      if (!Objects.equals(getUUID(event.getEntity()),getUUID(Minecraft.getInstance().player))) {
        return;
      }

      ItemStack stack = event.getItemStack();
      if (stack.getItem().equals(ModItems.multiTool)) {
        leftClickOnBlockClient(event.getPos());
      }
    }

    @Nullable
    private static UUID getUUID(@Nullable Entity entity) {
      if(entity == null)
        return null;

      return entity.getUUID();
    }

    @SubscribeEvent
    public static void leftClickEmpty(@Nonnull PlayerInteractEvent.LeftClickEmpty event) {
      if (event.getSide() == LogicalSide.SERVER
          || event.getEntity() != Minecraft.getInstance().player) return;

      ItemStack stack = event.getItemStack();

      if (stack.getItem().equals(ModItems.multiTool)) {
        PlayerEntity entityLiving = (PlayerEntity) event.getEntity();
        BlockRayTraceResult result =
            PlayerInteractionUtil.getBlockPlayerLookingAtClient(
                entityLiving, Minecraft.getInstance().getFrameTime());
        if (result.getType() != RayTraceResult.Type.MISS)
          leftClickOnBlockClient(result.getBlockPos()); // result.getHitVec()
      }
    }
  }

  @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
  public static class CommonSideEvents {

    static boolean enabled = false;
    static RegistryKey<World> world;
    static BlockPos pos;
    static UUID uuid;

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void teleportDrops(@Nonnull EntityJoinWorldEvent event) {
      if (!enabled ||
          event.getWorld().isClientSide() ||
          !event.getEntity().blockPosition().equals(pos) ||
          !(event.getEntity() instanceof ItemEntity) ||
          uuid == null) {
        return;
      }

      PlayerEntity player = event.getWorld().getPlayerByUUID(uuid);
      if (player == null) {
        return;
      }

      ItemEntity itemEntity = ((ItemEntity) event.getEntity());
      itemEntity.setPickUpDelay(0);
      itemEntity.playerTouch(player);

      if (!itemEntity.isAlive()) {
        event.setCanceled(true);
        event.setResult(Event.Result.ALLOW);
      }
    }
  }
}
