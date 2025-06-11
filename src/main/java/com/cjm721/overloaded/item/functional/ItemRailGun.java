package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.RailGunFireMessage;
import com.cjm721.overloaded.network.packets.RailGunSettingsMessage;
import com.cjm721.overloaded.proxy.ClientProxy;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import com.google.common.primitives.Ints;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE_ITEM;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class ItemRailGun extends PowerModItem {

  @Nonnull private static final String RAILGUN_POWER_KEY = "railgun.power";

  public ItemRailGun(Properties properties) {
    super(properties);
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    @org.jetbrains.annotations.Nullable
    IGenericDataStorage cap = stack.getCapability(GENERIC_DATA_STORAGE_ITEM);
    if (cap != null) {
      cap.suggestUpdate();
      int energyRequirement =
          cap.getIntegerMap()
              .getOrDefault(RAILGUN_POWER_KEY, OverloadedConfig.INSTANCE.railGun.minEnergy);
      tooltipComponents.add(
          Component.literal(
              String.format(
                  "Power Usage: %s", NumberFormat.getInstance().format(energyRequirement))));
    }

    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    //    ModelResourceLocation location =
    //        new ModelResourceLocation(new ResourceLocation(MODID, "railgun"), null);
    //    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);
    //
    //    ImageUtil.registerDynamicTexture(
    //        new ResourceLocation(MODID, "textures/item/railgun.png"),
    //        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(
      Level worldIn, Player playerIn, InteractionHand handIn) {
    if (worldIn.isClientSide) {
      int distance = OverloadedConfig.INSTANCE.railGun.maxRange;
      Vec3 vec3d =
          playerIn.getEyePosition(Minecraft.getInstance().getTimer().getGameTimeDeltaTicks());
      Vec3 vec3d1 =
          playerIn.getViewVector(Minecraft.getInstance().getTimer().getGameTimeDeltaTicks());
      Vec3 vec3d2 = vec3d.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
      float f = 1.0F;
      AABB axisalignedbb =
          playerIn.getBoundingBox().expandTowards(vec3d1.scale(distance)).inflate(1.0D, 1.0D, 1.0D);
      EntityHitResult ray =
          ProjectileUtil.getEntityHitResult(
              playerIn,
              vec3d,
              vec3d2,
              axisalignedbb,
              (p_215312_0_) -> !p_215312_0_.isSpectator() && p_215312_0_.isPickable(),
              distance * distance);
      if (ray != null) {
        Vec3 moveVev =
            playerIn.getEyePosition(1).subtract(ray.getLocation()).normalize().scale(-1.0);
        PacketDistributor.sendToServer(
            new RailGunFireMessage(ray.getEntity().getId(), moveVev, handIn));
      } else {
        PacketDistributor.sendToServer(new RailGunFireMessage(0, Vec3.ZERO, handIn));
      }
    }

    return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
  }

  @SubscribeEvent
  public static void onMouseEvent(InputEvent.MouseScrollingEvent event) {
    LocalPlayer player = Minecraft.getInstance().player;
    if (event.getScrollDeltaY() != 0 && player != null && player.isShiftKeyDown()) {
      ItemStack stack = player.getMainHandItem();
      if (player.isShiftKeyDown() && !stack.isEmpty() && stack.getItem() instanceof ItemRailGun) {
        int powerDelta =
            Long.signum(Math.round(event.getScrollDeltaY()))
                * OverloadedConfig.INSTANCE.railGun.stepEnergy;
        if (InputConstants.isKeyDown(
            Minecraft.getInstance().getWindow().getWindow(),
            ClientProxy.railGun100x.getKey().getValue())) {
          powerDelta *= 100;
        }
        PacketDistributor.sendToServer(new RailGunSettingsMessage(powerDelta));
        event.setCanceled(true);
      }
    }
  }

  public static void handleFireMessage(
      @Nonnull ServerPlayer player, @Nonnull RailGunFireMessage message) {
    ItemStack itemStack = player.getItemInHand(message.hand);
    if (!(itemStack.getItem() instanceof ItemRailGun)) {
      return;
    }

    IEnergyStorage opEnergy = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);

    if (opEnergy == null) {
      Overloaded.logger.warn(
          "RailGun has no Energy Capability? NBT: " + itemStack.getAttributeModifiers());
      return;
    }

    IGenericDataStorage opSettingCapability = itemStack.getCapability(GENERIC_DATA_STORAGE_ITEM);
    if (opSettingCapability == null) {
      Overloaded.logger.warn(
          "RailGun has no GenericData Capability? NBT: " + itemStack.getAttributeModifiers());
      return;
    }

    opSettingCapability.suggestUpdate();
    int energyRequired =
        opSettingCapability
            .getIntegerMap()
            .getOrDefault(RAILGUN_POWER_KEY, OverloadedConfig.INSTANCE.railGun.minEnergy);

    if (opEnergy.getEnergyStored() < energyRequired) {
      player.displayClientMessage(Component.literal("Not enough power to fire."), true);
      return;
    }

    int energyExtracted = opEnergy.extractEnergy(energyRequired, false);

    @Nullable Entity entity = player.level().getEntity(message.id);
    double amount = OverloadedConfig.INSTANCE.railGun.damagePerRF * energyExtracted;
    if (entity == null || !entity.isAlive()) {
      return;
    } else if (player.distanceTo(entity) > OverloadedConfig.INSTANCE.rayGun.maxRange) {
      player.displayClientMessage(Component.literal("Target out of range."), true);
    } else if (entity.hurt(
        new DamageSource(
            player
                .registryAccess()
                .lookupOrThrow(Registries.DAMAGE_TYPE)
                .getOrThrow(DamageTypes.GENERIC),
            null,
            player,
            player.getEyePosition()),
        (float) (amount))) {
      Vec3 knockback =
          message.moveVector.scale(
              energyExtracted * OverloadedConfig.INSTANCE.railGun.knockbackPerRF);
      entity.push(Math.min(knockback.x, 25), Math.min(knockback.y, 25), Math.min(knockback.z, 25));
    }
  }

  public static void handleSettingsMessage(
      @Nonnull ServerPlayer player, @Nonnull RailGunSettingsMessage message) {
    ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
    if (!(itemStack.getItem() instanceof ItemRailGun)) {
      return;
    }

    IGenericDataStorage opCap = itemStack.getCapability(GENERIC_DATA_STORAGE_ITEM);

    if (opCap == null) {
      Overloaded.logger.warn(
          "RailGun has no GenericData Capability? NBT: " + itemStack.getAttributeModifiers());
      return;
    }

    Map<String, Integer> integerMap = opCap.getIntegerMap();

    int power = integerMap.getOrDefault(RAILGUN_POWER_KEY, 0) + message.powerDelta;
    power =
        Ints.constrainToRange(
            power,
            OverloadedConfig.INSTANCE.railGun.minEnergy,
            OverloadedConfig.INSTANCE.railGun.maxEnergy);

    integerMap.put(RAILGUN_POWER_KEY, power);
    opCap.suggestSave();

    player.displayClientMessage(
        Component.literal("Power usage set to: " + NumberFormat.getInstance().format(power)), true);
    player.getInventory().setChanged();
  }

  //  @EventBusSubscriber(value = Dist.CLIENT, modid = MODID, bus = EventBusSubscriber.Bus.GAME)
  //  private static class ClientSideEvents {}
}
