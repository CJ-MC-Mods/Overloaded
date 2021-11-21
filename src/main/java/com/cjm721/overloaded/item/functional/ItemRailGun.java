package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.RailGunFireMessage;
import com.cjm721.overloaded.network.packets.RailGunSettingsMessage;
import com.cjm721.overloaded.proxy.ClientProxy;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import com.cjm721.overloaded.storage.itemwrapper.GenericDataCapabilityProviderWrapper;
import com.google.common.primitives.Ints;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class ItemRailGun extends PowerModItem {

  @Nonnull private static final String RAILGUN_POWER_KEY = "railgun.power";

  public ItemRailGun() {
    setRegistryName("railgun");
    //    setTranslationKey("railgun");
  }

  @Override
  public void appendHoverText(
      ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    stack
        .getCapability(GENERIC_DATA_STORAGE)
        .ifPresent(
            cap -> {
              cap.suggestUpdate();
              int energyRequirement =
                  cap.getIntegerMap()
                      .getOrDefault(RAILGUN_POWER_KEY, OverloadedConfig.INSTANCE.railGun.minEnergy);
              tooltip.add(
                  new StringTextComponent(
                      String.format(
                          "Power Usage: %s",
                          NumberFormat.getInstance().format(energyRequirement))));
            });

    super.appendHoverText(stack, worldIn, tooltip, flagIn);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "railgun"), null);
    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/railgun.png"),
        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }

  @Override
  @Nonnull
  @OnlyIn(Dist.CLIENT)
  public ActionResult<ItemStack> use(
      World worldIn, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn) {
    if (worldIn.isClientSide) {
      int distance = OverloadedConfig.INSTANCE.railGun.maxRange;
      Vector3d vec3d = playerIn.getEyePosition(Minecraft.getInstance().getFrameTime());
      Vector3d vec3d1 = playerIn.getViewVector(Minecraft.getInstance().getFrameTime());
      Vector3d vec3d2 = vec3d.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
      float f = 1.0F;
      AxisAlignedBB axisalignedbb =
          playerIn.getBoundingBox().expandTowards(vec3d1.scale(distance)).inflate(1.0D, 1.0D, 1.0D);
      EntityRayTraceResult ray =
          ProjectileHelper.getEntityHitResult(
              playerIn,
              vec3d,
              vec3d2,
              axisalignedbb,
              (p_215312_0_) -> !p_215312_0_.isSpectator() && p_215312_0_.isPickable(),
              distance * distance);
      if (ray != null) {
        Vector3d moveVev =
            playerIn.getEyePosition(1).subtract(ray.getLocation()).normalize().scale(-1.0);
        Overloaded.proxy.networkWrapper.sendToServer(
            new RailGunFireMessage(ray.getEntity().getId(), moveVev, handIn));
      } else {
        Overloaded.proxy.networkWrapper.sendToServer(new RailGunFireMessage(0, Vector3d.ZERO, handIn));
      }
    }

    return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));
  }

  @SubscribeEvent
  public void onMouseEvent(InputEvent.MouseScrollEvent event) {
    ClientPlayerEntity player = Minecraft.getInstance().player;
    if (event.getScrollDelta() != 0 && player != null && player.isShiftKeyDown()) {
      ItemStack stack = player.getMainHandItem();
      if (player.isShiftKeyDown() && !stack.isEmpty() && stack.getItem() == this) {
        int powerDelta =
            Long.signum(Math.round(event.getScrollDelta()))
                * OverloadedConfig.INSTANCE.railGun.stepEnergy;
        if (InputMappings.isKeyDown(
            Minecraft.getInstance().getWindow().getWindow(),
            ((ClientProxy) Overloaded.proxy).railGun100x.getKey().getValue())) {
          powerDelta *= 100;
        }
        Overloaded.proxy.networkWrapper.sendToServer(new RailGunSettingsMessage(powerDelta));
        event.setCanceled(true);
      }
    }
  }

  public void handleFireMessage(
      @Nonnull ServerPlayerEntity player, @Nonnull RailGunFireMessage message) {
    ItemStack itemStack = player.getItemInHand(message.hand);
    if (itemStack.getItem() != this) {
      return;
    }

    LazyOptional<IEnergyStorage> opEnergy = itemStack.getCapability(ENERGY);

    if (!opEnergy.isPresent()) {
      Overloaded.logger.warn("RailGun has no Energy Capability? NBT: " + itemStack.getTag());
      return;
    }

    IEnergyStorage energy =
        opEnergy.orElseThrow(() -> new RuntimeException("Impossible Condition"));

    LazyOptional<IGenericDataStorage> opSettingCapability =
        itemStack.getCapability(GENERIC_DATA_STORAGE);
    if (!opSettingCapability.isPresent()) {
      Overloaded.logger.warn("RailGun has no GenericData Capability? NBT: " + itemStack.getTag());
      return;
    }

    IGenericDataStorage settingCapability =
        opSettingCapability.orElseThrow(() -> new RuntimeException("Impossible Condition"));

    settingCapability.suggestUpdate();
    int energyRequired =
        settingCapability
            .getIntegerMap()
            .getOrDefault(RAILGUN_POWER_KEY, OverloadedConfig.INSTANCE.railGun.minEnergy);

    if (energy.getEnergyStored() < energyRequired) {
      player.displayClientMessage(new StringTextComponent("Not enough power to fire."), true);
      return;
    }

    int energyExtracted = energy.extractEnergy(energyRequired, false);

    @Nullable Entity entity = player.level.getEntity(message.id);
    if (entity == null || !entity.isAlive()) {
      return;
    } else if (player.distanceTo(entity) > OverloadedConfig.INSTANCE.rayGun.maxRange) {
      player.displayClientMessage(new StringTextComponent("Target out of range."), true);
    } else if (entity.hurt(
        DamageSource.playerAttack(player),
        (float) (OverloadedConfig.INSTANCE.railGun.damagePerRF * energyExtracted))) {
      Vector3d knockback =
          message.moveVector.scale(
              energyExtracted * OverloadedConfig.INSTANCE.railGun.knockbackPerRF);
      entity.push(knockback.x, knockback.y, knockback.z);
    }
  }

  @Override
  public Collection<ICapabilityProvider> collectCapabilities(
      @Nonnull Collection<ICapabilityProvider> collection,
      ItemStack stack,
      @Nullable CompoundNBT nbt) {
    collection.add(new GenericDataCapabilityProviderWrapper(stack));

    return super.collectCapabilities(collection, stack, nbt);
  }

  public void handleSettingsMessage(
      @Nonnull ServerPlayerEntity player, @Nonnull RailGunSettingsMessage message) {
    ItemStack itemStack = player.getItemInHand(Hand.MAIN_HAND);
    if (itemStack.getItem() != this) {
      return;
    }

    LazyOptional<IGenericDataStorage> opCap = itemStack.getCapability(GENERIC_DATA_STORAGE);

    if (!opCap.isPresent()) {
      Overloaded.logger.warn("RailGun has no GenericData Capability? NBT: " + itemStack.getTag());
      return;
    }

    IGenericDataStorage cap = opCap.orElseThrow(() -> new RuntimeException("Impossible Condition"));

    Map<String, Integer> integerMap = cap.getIntegerMap();

    int power = integerMap.getOrDefault(RAILGUN_POWER_KEY, 0) + message.powerDelta;
    power =
        Ints.constrainToRange(
            power,
            OverloadedConfig.INSTANCE.railGun.minEnergy,
            OverloadedConfig.INSTANCE.railGun.maxEnergy);

    integerMap.put(RAILGUN_POWER_KEY, power);
    cap.suggestSave();

    player.displayClientMessage(
        new StringTextComponent("Power usage set to: " + NumberFormat.getInstance().format(power)),
        true);
  }

  @Mod.EventBusSubscriber(
      value = Dist.CLIENT,
      modid = MOD_ID,
      bus = Mod.EventBusSubscriber.Bus.FORGE)
  private static class ClientSideEvents {}
}
