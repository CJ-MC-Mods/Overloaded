package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.RailGunFireMessage;
import com.cjm721.overloaded.network.packets.RailGunSettingsMessage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import com.cjm721.overloaded.storage.itemwrapper.GenericDataCapabilityProviderWrapper;
import com.google.common.primitives.Ints;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.storage.GenericDataStorage.GENERIC_DATA_STORAGE;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class ItemRailGun extends PowerModItem {

  @Nonnull private static final String RAILGUN_POWER_KEY = "railgun.power";

  public ItemRailGun() {
    setRegistryName("railgun");
    //    setTranslationKey("railgun");
  }

  @Override
  public void addInformation(
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

    super.addInformation(stack, worldIn, tooltip, flagIn);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "railgun"), null);
    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/items/railgun.png"),
        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }

  @Override
  @Nonnull
  @OnlyIn(Dist.CLIENT)
  public ActionResult<ItemStack> onItemRightClick(
      World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
    if (worldIn.isRemote) {
      //      RayTraceResult ray =
      //          rayTraceWithEntities(
      //              worldIn,
      //              playerIn.getEyePosition(Minecraft.getInstance().getRenderPartialTicks()),
      //              playerIn.getLook(Minecraft.getInstance().getRenderPartialTicks()),
      //              playerIn,
      //              OverloadedConfig.INSTANCE.railGun.maxRange);
      //      if (ray != null && ray.entityHit != null) {
      //        Vec3d moveVev =
      // playerIn.getPositionEyes(1).subtract(ray.hitVec).normalize().scale(-1.0);
      //
      //        Overloaded.proxy.networkWrapper.sendToServer(
      //            new RailGunFireMessage(ray.entityHit.getEntityId(), moveVev, handIn));
      //      } else {
      //        Overloaded.proxy.networkWrapper.sendToServer(new RailGunFireMessage(0, Vec3d.ZERO,
      // handIn));
      //      }
    }

    return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
  }

  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public void onMouseEvent(@Nonnull InputEvent.MouseInputEvent event) {
    ClientPlayerEntity player = Minecraft.getInstance().player;
    //    if (event.getDwheel() != 0 && player != null && player.isSneaking()) {
    //      ItemStack stack = player.getHeldItemMainhand();
    //      if (player.isSneaking() && !stack.isEmpty() && stack.getItem() == this) {
    //        int powerDelta =
    //            Integer.signum(event.getDwheel()) * OverloadedConfig.INSTANCE.railGun.stepEnergy;
    //        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
    //            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
    //          powerDelta *= 100;
    //        }
    //        Overloaded.proxy.networkWrapper.sendToServer(new RailGunSettingsMessage(powerDelta));
    //        event.setCanceled(true);
    //      }
    //    }
  }

  public void handleFireMessage(
      @Nonnull ServerPlayerEntity player, @Nonnull RailGunFireMessage message) {
    ItemStack itemStack = player.getHeldItem(message.hand);
    if (itemStack.getItem() != this) {
      return;
    }

    IEnergyStorage energy = itemStack.getCapability(ENERGY).orElse(null);

    IGenericDataStorage settingCapability =
        itemStack.getCapability(GENERIC_DATA_STORAGE).orElse(null);
    settingCapability.suggestUpdate();
    int energyRequired =
        settingCapability
            .getIntegerMap()
            .getOrDefault(RAILGUN_POWER_KEY, OverloadedConfig.INSTANCE.railGun.minEnergy);

    if (energy.getEnergyStored() < energyRequired) {
      player.sendStatusMessage(new StringTextComponent("Not enough power to fire."), true);
      return;
    }

    int energyExtracted = energy.extractEnergy(energyRequired, false);

    @Nullable Entity entity = player.world.getEntityByID(message.id);
    if (entity == null || !entity.isAlive()) {
      return;
    } else if (player.getDistance(entity) > OverloadedConfig.INSTANCE.rayGun.maxRange) {
      player.sendStatusMessage(new StringTextComponent("Target out of range."), true);
    } else if (entity.attackEntityFrom(
        DamageSource.causePlayerDamage(player),
        (float) (OverloadedConfig.INSTANCE.railGun.damagePerRF * energyExtracted))) {
      Vec3d knockback =
          message.moveVector.scale(
              energyExtracted * OverloadedConfig.INSTANCE.railGun.knockbackPerRF);
      entity.addVelocity(knockback.x, knockback.y, knockback.z);
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
    ItemStack itemStack = player.getHeldItem(Hand.MAIN_HAND);
    if (itemStack.getItem() != this) {
      return;
    }

    IGenericDataStorage cap = itemStack.getCapability(GENERIC_DATA_STORAGE).orElse(null);
    Map<String, Integer> integerMap = cap.getIntegerMap();

    int power = integerMap.getOrDefault(RAILGUN_POWER_KEY, 0) + message.powerDelta;
    power =
        Ints.constrainToRange(
            power,
            OverloadedConfig.INSTANCE.railGun.minEnergy,
            OverloadedConfig.INSTANCE.railGun.maxEnergy);

    integerMap.put(RAILGUN_POWER_KEY, power);
    cap.suggestSave();

    player.sendStatusMessage(
        new StringTextComponent("Power usage set to: " + NumberFormat.getInstance().format(power)),
        true);
  }
}
