package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.KeyBindPressedMessage;
import com.cjm721.overloaded.proxy.ClientProxy;
import com.cjm721.overloaded.storage.GenericDataCapabilityProvider;
import com.cjm721.overloaded.storage.GenericDataStorage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE;
import static com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.DataKeys;
import static com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.Default;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class ArmorEventHandler {

  @SubscribeEvent
  public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
    if (event.getObject() instanceof PlayerEntity) {
      event.addCapability(
          new ResourceLocation(MODID, "player_data"), new GenericDataCapabilityProvider());
    }
  }

  private static final String set = "set";
  private static final String noClip = "noClip";
  private static final UUID groundSpeedAttribute =
      UUID.fromString("241a8bbe-1660-11eb-adc1-0242ac120002");

  @SubscribeEvent
  public void onPlayerTickEvent(@Nonnull TickEvent.PlayerTickEvent event) {
    PlayerEntity player = event.player;
    if (player == null || player.dead) return;

    IGenericDataStorage playerDataStorage = getPlayerDataStorage(player);

    if (isMultiArmorSetEquipped(player) && hasEnergy(player)) {
      IGenericDataStorage armorDataStorage = getHelmetDataStorage(player);
      Map<String, Boolean> armorBooleans = armorDataStorage.getBooleanMap();

      playerDataStorage.getBooleanMap().put(set, true);

      if (armorBooleans.getOrDefault(DataKeys.FLIGHT, Default.FLIGHT)) {
        tryEnableFlight(player, playerDataStorage, armorDataStorage, event.side);
      } else {
        disableFlight(player, event.side);
      }
      if (armorBooleans.getOrDefault(DataKeys.FEED, Default.FEED)) {
        tryFeedPlayer(player, event.side);
      }
      if (armorBooleans.getOrDefault(DataKeys.HEAL, Default.HEAL)) {
        tryHealPlayer(player, event.side);
      }
      if (armorBooleans.getOrDefault(DataKeys.REMOVE_HARMFUL, Default.REMOVE_HARMFUL)) {
        tryRemoveHarmful(player, event.side);
      }
      if (armorBooleans.getOrDefault(DataKeys.EXTINGUISH, Default.EXTINGUISH)) {
        tryExtinguish(player, event.side);
      }
      if (armorBooleans.getOrDefault(DataKeys.GIVE_AIR, Default.GIVE_AIR)) {
        tryGiveAir(player, event.side);
      }
      tryGroundSpeed(player, armorDataStorage, event.side);
    } else {
      Map<String, Boolean> boolMap = playerDataStorage.getBooleanMap();
      if (boolMap.containsKey(set) && boolMap.get(set)) {
        boolMap.put(set, false);
        disableFlight(player, event.side);
        disableNoClip(player, playerDataStorage);
        disableGroundSpeed(player, event.side);
      }
    }
  }

  private void tryGroundSpeed(
      PlayerEntity player, IGenericDataStorage armorDataStorage, LogicalSide side) {
    float groundSpeed =
        armorDataStorage.getFloatMap().getOrDefault(DataKeys.GROUND_SPEED, Default.GROUND_SPEED);

    float powerRequired =
        (float)
            ((player.distanceWalkedModified - player.prevDistanceWalkedModified)
                / 0.6F
                * OverloadedConfig.INSTANCE.multiArmorConfig.energyPerBlockWalked
                * OverloadedConfig.INSTANCE.multiArmorConfig.energyMultiplierPerGroundSpeed
                * (groundSpeed - Default.GROUND_SPEED));

    if (extractEnergy(player, Math.round(powerRequired), side == LogicalSide.CLIENT)) {
      AttributeModifier modifier = new AttributeModifier(
          groundSpeedAttribute,
          "Ground Speed modifier",
          groundSpeed,
          AttributeModifier.Operation.ADDITION);
      if(!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(modifier)) {
        player.getAttribute(Attributes.MOVEMENT_SPEED).applyNonPersistentModifier(modifier);
      }
    } else {
      disableGroundSpeed(player, side);
    }
  }

  private void disableGroundSpeed(PlayerEntity player, LogicalSide side) {
    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(groundSpeedAttribute);
  }

  private void disableNoClip(PlayerEntity player, IGenericDataStorage dataStorage) {
    player.noClip = false;
    dataStorage.getBooleanMap().put(noClip, false);
  }

  private void tryEnableNoClip(
      PlayerEntity player,
      IGenericDataStorage dataStorage,
      IGenericDataStorage helmetDataStorage,
      LogicalSide side) {
    final Map<String, Boolean> playerBooleans = dataStorage.getBooleanMap();
    final Map<String, Boolean> armorBooleans = helmetDataStorage.getBooleanMap();

    if (playerBooleans.containsKey(set)
        && playerBooleans.get(set)
        && playerBooleans.containsKey(noClip)
        && playerBooleans.get(noClip)) {
      if (extractEnergy(
          player,
          OverloadedConfig.INSTANCE.multiArmorConfig.noClipEnergyPerTick,
          side == LogicalSide.CLIENT)) {
        player.noClip = true;
        if (armorBooleans.getOrDefault(DataKeys.NOCLIP_FLIGHT_LOCK, Default.NOCLIP_FLIGHT_LOCK)) {
          tryEnableFlight(player, dataStorage, helmetDataStorage, side);
          player.abilities.isFlying = true;
        }
      } else {
        setNoClip(player, false);
      }
    }
  }

  private void tryGiveAir(PlayerEntity player, LogicalSide side) {
    int airNeeded = 300 - player.getAir();

    if (airNeeded > 0
        && extractEnergy(
            player,
            airNeeded * OverloadedConfig.INSTANCE.multiArmorConfig.costPerAir,
            side == LogicalSide.CLIENT)) {
      player.setAir(300);
    }
  }

  private void tryExtinguish(@Nonnull PlayerEntity player, @Nonnull LogicalSide side) {
    if (player.isBurning()
        && extractEnergy(
            player,
            OverloadedConfig.INSTANCE.multiArmorConfig.extinguishCost,
            side == LogicalSide.CLIENT)) {
      player.extinguish();
    }
  }

  private void tryHealPlayer(@Nonnull PlayerEntity player, @Nonnull LogicalSide side) {
    float currentHealth = player.getHealth();
    float maxHealth = player.getMaxHealth();

    int toHeal = (int) Math.ceil(maxHealth - currentHealth);
    if (toHeal > 0
        && extractEnergy(
            player,
            OverloadedConfig.INSTANCE.multiArmorConfig.costPerHealth * toHeal,
            side == LogicalSide.CLIENT)) {
      player.setHealth(maxHealth);
    }
  }

  private void tryRemoveHarmful(@Nonnull PlayerEntity player, @Nonnull LogicalSide side) {
    Iterator<EffectInstance> potionEffectIterator = player.getActivePotionEffects().iterator();

    while (potionEffectIterator.hasNext()) {
      EffectInstance effect = potionEffectIterator.next();
      Effect potion = effect.getPotion();
      if (potion.isBeneficial()) continue;

      if (!extractEnergy(
          player,
          OverloadedConfig.INSTANCE.multiArmorConfig.removeEffect,
          true)) {
        continue;
      }
      if (extractEnergy(
          player,
          OverloadedConfig.INSTANCE.multiArmorConfig.removeEffect,
          side == LogicalSide.CLIENT)) {
        // If not canceled
        if(!MinecraftForge.EVENT_BUS.post(new PotionEvent.PotionRemoveEvent(player, potion))) {
          potionEffectIterator.remove();
        }
      }
    }
  }

  private void tryFeedPlayer(@Nonnull PlayerEntity player, @Nonnull LogicalSide side) {
    FoodStats foodStats = player.getFoodStats();
    int foodLevel = foodStats.getFoodLevel();
    int toFeed = OverloadedConfig.INSTANCE.multiArmorConfig.maxFoodLevel - foodLevel;
    float saturationLevel = foodStats.getSaturationLevel();
    float toAdd = OverloadedConfig.INSTANCE.multiArmorConfig.maxFoodLevel - saturationLevel;

    if (toFeed > 0
        && extractEnergy(
            player,
            Math.round(OverloadedConfig.INSTANCE.multiArmorConfig.costPerFood * toFeed),
            side == LogicalSide.CLIENT)) {
      foodStats.addStats(toFeed, 0);
    }

    if (toAdd > 0.0F
        && extractEnergy(
            player,
            (int) Math.round(OverloadedConfig.INSTANCE.multiArmorConfig.costPerSaturation * toAdd),
            side == LogicalSide.CLIENT)) {
      toFeed = Math.round(toAdd);
      foodStats.addStats(toFeed, 0.5F);
    }
  }

  private void tryEnableFlight(
      @Nonnull PlayerEntity player,
      @Nonnull IGenericDataStorage dataStorage,
      IGenericDataStorage armorDataStorage,
      @Nonnull LogicalSide side) {
    final Map<String, Boolean> booleans = dataStorage.getBooleanMap();
    final Map<String, Float> armorFloats = armorDataStorage.getFloatMap();

    float flightSpeed = armorFloats.getOrDefault(DataKeys.FLIGHT_SPEED, Default.FLIGHT_SPEED);

    player.abilities.allowFlying = true;
    if (side == LogicalSide.CLIENT) {
      player.abilities.setFlySpeed(
          armorFloats.getOrDefault(DataKeys.FLIGHT_SPEED, Default.FLIGHT_SPEED));
    }
    booleans.put(set, true);

    int energyCost =
        (int)
            Math.round(
                OverloadedConfig.INSTANCE.multiArmorConfig.energyPerTickFlying
                    * flightSpeed
                    * OverloadedConfig.INSTANCE.multiArmorConfig.energyMultiplierPerFlightSpeed);

    if (player.abilities.isFlying
        && !extractEnergy(player, energyCost, side == LogicalSide.CLIENT)) {
      disableFlight(player, side);
    }
  }

  private void disableFlight(@Nonnull PlayerEntity player, @Nonnull LogicalSide side) {
    player.abilities.allowFlying = false;
    player.abilities.isFlying = false;
    if (side == LogicalSide.CLIENT) {
      player.abilities.setFlySpeed(0.05F);
    }
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void onLivingAttackedEvent(LivingAttackEvent event) {
    Entity entity = event.getEntity();
    if (!(entity instanceof PlayerEntity)) return;

    PlayerEntity player = ((PlayerEntity) entity);
    boolean setEquipped = isMultiArmorSetEquipped(player);

    if (setEquipped) {
      DamageSource damageSource = event.getSource();

      int energyCost = OverloadedConfig.INSTANCE.multiArmorConfig.baseCost;

      float damageAmount =
          (float) (event.getAmount() * OverloadedConfig.INSTANCE.multiArmorConfig.damageMultiplier);

      if (damageSource.isDamageAbsolute())
        damageAmount *= OverloadedConfig.INSTANCE.multiArmorConfig.absoluteDamageMultiplier;

      if (damageSource.isUnblockable())
        damageAmount *= OverloadedConfig.INSTANCE.multiArmorConfig.unblockableMultiplier;

      if (damageAmount > Integer.MAX_VALUE) return;

      energyCost += damageAmount;

      // Overflow
      if (energyCost < 0) return;

      if (extractEnergy(player, energyCost, false)) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
    Entity entity = event.getEntity();

    if (entity instanceof PlayerEntity) {
      PlayerEntity player = ((PlayerEntity) entity);
      tryEnableNoClip(
          player, getPlayerDataStorage(player), getHelmetDataStorage(player), LogicalSide.SERVER);
    }
  }

  private boolean hasEnergy(PlayerEntity player) {
    for (ItemStack stack : player.getArmorInventoryList()) {
      if (stack.getCapability(ENERGY).map(e -> e.getEnergyStored() > 0).orElse(false)) {
        return true;
      }
    }

    return false;
  }

  private boolean extractEnergy(PlayerEntity player, int energyCost, boolean simulated) {
    if (energyCost <= 0) {
      return true;
    }

    final int originalCost = energyCost;
    for (ItemStack stack : player.getArmorInventoryList()) {
      LazyOptional<IEnergyStorage> opEnergyStorage = stack.getCapability(ENERGY);
      energyCost -=
          opEnergyStorage.map(e -> e.extractEnergy(originalCost / 4, simulated)).orElse(0);

      if (energyCost <= 0) {
        return true;
      }
    }

    for (ItemStack stack : player.getArmorInventoryList()) {
      LazyOptional<IEnergyStorage> opEnergyStorage = stack.getCapability(ENERGY);
      final int extractAmount = energyCost;
      energyCost -=
          opEnergyStorage.map(e -> e.extractEnergy(extractAmount, simulated)).orElse(0);
      if (energyCost <= 0) {
        return true;
      }
    }
    return false;
  }

  @Nonnull
  private static IGenericDataStorage getPlayerDataStorage(PlayerEntity player) {
    return player.getCapability(GENERIC_DATA_STORAGE).orElse(new GenericDataStorage());
  }

  @Nonnull
  private static IGenericDataStorage getHelmetDataStorage(PlayerEntity player) {
    for (ItemStack stack : player.inventory.armorInventory) {
      if (stack.getItem() instanceof ItemMultiHelmet) {
        IGenericDataStorage cap =
            stack.getCapability(GENERIC_DATA_STORAGE).orElse(new GenericDataStorage());
        cap.suggestUpdate();
        return cap;
      }
    }
    return new GenericDataStorage();
  }

  private boolean isMultiArmorSetEquipped(PlayerEntity player) {
    boolean setEquipped = true;
    for (ItemStack stack : player.inventory.armorInventory) {
      if (!(stack.getItem() instanceof IMultiArmor)) {
        setEquipped = false;
      }
    }
    return setEquipped;
  }

  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public void onKeyInputEvent(InputEvent.KeyInputEvent event) {
    if (((ClientProxy) Overloaded.proxy).noClipKeybind.isPressed()
        && isMultiArmorSetEquipped(Minecraft.getInstance().player)) {
      Overloaded.proxy.networkWrapper.sendToServer(
          new KeyBindPressedMessage(KeyBindPressedMessage.KeyBind.NO_CLIP));
    }
  }

  public static boolean toggleNoClip(ServerPlayerEntity player) {
    IGenericDataStorage storage = getPlayerDataStorage(player);

    final Map<String, Boolean> booleans = storage.getBooleanMap();
    if (booleans.containsKey(noClip) && booleans.get(noClip)) {
      booleans.remove(noClip);
      return false;
    } else {
      booleans.put(noClip, true);
      return true;
    }
  }

  public static void setNoClip(PlayerEntity player, boolean enabled) {
    IGenericDataStorage storage = getPlayerDataStorage(player);

    final Map<String, Boolean> booleans = storage.getBooleanMap();
    booleans.put(noClip, enabled);
  }
}
