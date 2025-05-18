package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.network.packets.LeftClickBlockMessage;
import com.cjm721.overloaded.network.packets.RightClickBlockMessage;
import com.cjm721.overloaded.util.BlockBreakResult;
import com.cjm721.overloaded.util.BlockPlaceResult;
import com.cjm721.overloaded.util.PlayerInteractionUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.common.util.LazyOptional;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.event.entity.EntityJoinWorldEvent;
import net.neoforged.event.entity.player.PlayerInteractEvent;
import net.neoforged.eventbus.api.Event;
import net.neoforged.eventbus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.util.BlockPlaceResult.FAIL_DENY;
import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class ItemMultiTool extends PowerModItem {

  public ItemMultiTool(Properties properties) {
    super(
        properties);
//            .addToolType(ToolType.AXE, Integer.MAX_VALUE)
//            .addToolType(ToolType.PICKAXE, Integer.MAX_VALUE)
//            .addToolType(ToolType.SHOVEL, Integer.MAX_VALUE));
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
      @Nonnull ServerLevel worldIn,
      @Nonnull BlockPos blockPos,
      @Nonnull IEnergyStorage energy,
      @Nonnull ServerPlayer player,
      int efficiency,
      int unbreaking) {
    BlockState state = worldIn.getBlockState(blockPos);

    if (!player.getAbilities().instabuild) {
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
    EVENT_BUS.post(event);

    if (event.isCanceled()) {
      return BlockBreakResult.FAIL_REMOVE;
    }

    boolean result = PlayerInteractionUtil.tryHarvestBlock(player, worldIn, blockPos);
    return result ? BlockBreakResult.SUCCESS : BlockBreakResult.FAIL_REMOVE;
  }

  @OnlyIn(Dist.CLIENT)
  private static void leftClickOnBlockClient(BlockPos pos) {
    PacketDistributor.sendToServer(new LeftClickBlockMessage(pos));
    //            PlayerEntitySP player = Minecraft.getMinecraft().player;
    //            drawParticleStreamTo(player, hitVec,
    //     EnumParticleTypes.SMOKE_NORMAL);//EnumParticleTypes.TOWN_AURA
  }

  public static void leftClickOnBlockServer(
          @Nonnull ServerPlayer player, LeftClickBlockMessage message) {
    BlockPos pos = message.getPos();
    ServerLevel world = ((ServerLevel) player.level());
    ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
    if (!itemStack.is(ModItems.multiTool) || world.isEmptyBlock(pos)) {
      return;
    }

    player.startUsingItem(InteractionHand.MAIN_HAND);

    if (player.isShiftKeyDown()) {
      CompoundTag tag = itemStack.getTag();
      if (tag == null) {
        tag = new CompoundTag();
      }
      BlockState state = world.getBlockState(pos);
      Item item = Item.byBlock(state.getBlock());
      ItemStack stackToPlace = new ItemStack(() -> item, 1);
      CompoundTag blockTag = new CompoundTag();
      stackToPlace.save(blockTag);
      tag.put("Item", blockTag);
      itemStack.setTag(tag);
      Component component = stackToPlace.getDisplayName();
      player.displayClientMessage(
          Component.literal("Bound tool to ").append(component), true);
    } else {
      IEnergyStorage opEnergy = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
      if (opEnergy == null) {
        Overloaded.logger.warn("MultiTool has no Energy Capability? NBT: " + itemStack.getAttributeModifiers());
        return;
      }

      // Used to catch item spawn to teleport
      ResourceKey<Level> worldId = world.dimension();
      CommonSideEvents.enabled = true;
      CommonSideEvents.world = worldId;
      CommonSideEvents.pos = pos;
      CommonSideEvents.uuid = player.getUUID();

      IEnergyStorage energy = opEnergy;
      Registry<Enchantment> registry = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
      int efficiency = itemStack.getEnchantmentLevel(registry.getOrThrow(Enchantments.EFFICIENCY));
      int unbreaking = itemStack.getEnchantmentLevel(registry.getOrThrow(Enchantments.UNBREAKING));
      switch (breakAndUseEnergy(world, pos, energy, player, efficiency, unbreaking)) {
        case FAIL_REMOVE:
          player.displayClientMessage(
              Component.literal("Unable to break block, reason unknown"), true);
          break;
        case FAIL_ENERGY:
          player.displayClientMessage(
              Component.literal("Unable to break block, not enough energy"), true);
          break;
        case FAIL_UNBREAKABLE:
          player.displayClientMessage(Component.literal("Block is unbreakable"), true);
          break;
        case FAIL_RANGE:
          player.displayClientMessage(Component.literal("Block is out of range."), true);
          break;
        case SUCCESS:
          break;
      }
      CommonSideEvents.enabled = false;
    }
  }

//  @Override
//  public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
//    return enchantment != null && enchantment.category == EnchantmentType.DIGGER;
//  }
//
//  @Override
//  public int getItemEnchantability(ItemStack stack) {
//    return 15;
//  }
//
//  @Override
//  public boolean isEnchantable(@Nonnull ItemStack stack) {
//    return stack.getCount() == 1;
//  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location =
//        new ModelResourceLocation(new ResourceLocation(MODID, "multi_tool"), null);
//    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/multi_tool.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }

  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    tooltipComponents.add(Component.literal("Assist Mode: " + getAssistMode().getName()));
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
  }

  @Override
  public boolean mineBlock(
      ItemStack stack, @Nonnull Level worldIn, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entityLiving) {
    IEnergyStorage storage = stack.getCapability(Capabilities.EnergyStorage.ITEM, null);

    if (storage != null) {
      Registry<Enchantment> registry = worldIn.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
      int efficiency = stack.getEnchantmentLevel(registry.getOrThrow(Enchantments.EFFICIENCY));
      int unbreaking = stack.getEnchantmentLevel(registry.getOrThrow(Enchantments.UNBREAKING));
      float breakCost =
          getBreakCost(
              worldIn.getBlockState(pos).getDestroySpeed(worldIn, pos),
              efficiency,
              unbreaking,
              entityLiving == null ? 10 : getDistance(entityLiving, pos));

      storage
          .extractEnergy((int) Math.min(Integer.MAX_VALUE, breakCost), false);
    }

    return super.mineBlock(stack, worldIn, state, pos, entityLiving);
  }

  @Override
  public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
    return false;
  }

  @Override
  public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull BlockState state) {
    return 0f;
  }

  @Override
  public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
    return super.onItemUseFirst(stack, context);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
   if (context.getLevel().isClientSide) {
     BlockHitResult result =
          PlayerInteractionUtil.getBlockPlayerLookingAtClient(
              context.getPlayer(), Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaTicks());
      if (result.getType() == HitResult.Type.BLOCK) {
        PacketDistributor.sendToServer(
            new RightClickBlockMessage(
                result.getBlockPos(),
                result.getDirection(),
                (float) result.getLocation().x - result.getBlockPos().getX(),
                (float) result.getLocation().y - result.getBlockPos().getY(),
                (float) result.getLocation().z - result.getBlockPos().getZ()));
      }
    }
   return InteractionResult.SUCCESS;
  }

  public void rightClickWithItem(
      @Nonnull ServerPlayer player, RightClickBlockMessage message) {
    BlockPos pos = message.getPos();
    Direction sideHit = message.getHitSide();
    float hitX = message.getHitX();
    float hitY = message.getHitY();
    float hitZ = message.getHitZ();

    ServerLevel worldIn = (ServerLevel) player.level();
    ItemStack multiTool = player.getMainHandItem();

    if (multiTool.getItem() != this) {
      return;
    }

    ItemStack blockStack = getSelectedBlockItemStack(multiTool);

    if (blockStack.isEmpty()) {
      player.displayClientMessage(Component.literal("No block type selected to place."), true);
      return;
    }

    if (!(blockStack.getItem() instanceof BlockItem)) {
      player.displayClientMessage(
          Component.literal("No valid block type selected to place."), true);
      return;
    }

    IEnergyStorage opEnergy = multiTool.getCapability(Capabilities.EnergyStorage.ITEM);
    if (opEnergy == null) {
      Overloaded.logger.warn("MultiTool has no Energy Capability? NBT: " + multiTool.getTag());
      return;
    }

    Vec3i sideVector = sideHit.getUnitVec3i();
    BlockPos.MutableBlockPos newPosition = pos.offset(sideVector).mutable();

    switch (placeBlock(
        blockStack, player, worldIn, newPosition, sideHit, opEnergy, hitX, hitY, hitZ)) {
      case FAIL_PREREQUISITE:
        player.displayClientMessage(Component.literal("Do not have the required items"), true);
        return;
      case FAIL_DENY:
        player.displayClientMessage(Component.literal("Unable to place blocks"), true);
        return;
      case FAIL_RANGE:
        player.displayClientMessage(Component.literal("To far away"), true);
        return;
      case FAIL_ENERGY:
        player.displayClientMessage(Component.literal("Not enough energy"), true);
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
                    blockStack, player, worldIn, newPosition, sideHit, opEnergy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
        case DOWN:
          while (newPosition.getY() > playerPos.getY()) {
            newPosition.move(sideHit);
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, opEnergy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
        case NORTH:
          while (newPosition.getZ() > playerPos.getZ()) {
            newPosition.move(sideHit);
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, opEnergy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
        case SOUTH:
          while (newPosition.getZ() < playerPos.getZ()) {
            newPosition.move(sideHit);
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, opEnergy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
        case EAST:
          while (newPosition.getX() < playerPos.getX()) {
            newPosition.move(sideHit);
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, opEnergy, hitX, hitY, hitZ)
                != BlockPlaceResult.SUCCESS) break;
          }
          break;
        case WEST:
          while (newPosition.getX() > playerPos.getX()) {
            newPosition.move(sideHit);
            if (placeBlock(
                    blockStack, player, worldIn, newPosition, sideHit, opEnergy, hitX, hitY, hitZ)
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
    CompoundTag tagCompound = multiTool.getTag();

    if (tagCompound == null || !tagCompound.contains("Item")) {
      return ItemStack.EMPTY;
    }

    CompoundTag itemTag = tagCompound.getCompound("Item");
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

  @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
  public static class CommonSideEvents {

    static boolean enabled = false;
    static ResourceKey<Level> world;
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
