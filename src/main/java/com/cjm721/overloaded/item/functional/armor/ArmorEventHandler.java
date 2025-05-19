package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.KeyBindPressedMessage;
import com.cjm721.overloaded.proxy.ClientProxy;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE_ENTITY;
import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE_ITEM;
import static com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.DataKeys;
import static com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.Default;
import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class ArmorEventHandler {

  private static final UUID HEALTH_MODIFIER = UUID.fromString("def4cf44-4a8a-11ec-81d3-0242ac130003");

//  @SubscribeEvent
//  public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
//    if (event.getObject() instanceof Player) {
//      event.addCapability(
//          new ResourceLocation(MODID, "player_data"), new GenericDataCapabilityProvider());
//    }
//  }

  private static final String set = "set";
  private static final String noClip = "noClip";
  private static final UUID groundSpeedAttribute =
      UUID.fromString("241a8bbe-1660-11eb-adc1-0242ac120002");

//  @SubscribeEvent
//  public void onPlayerTickEvent(@Nonnull EntityTickEvent.Pre event) {
//    Entity entity = event.getEntity();
//    if (!(entity instanceof Player player)) {
//      return;
//    }
//      if (player == null || player.isDeadOrDying()) return;
//
//    IGenericDataStorage playerDataStorage = getPlayerDataStorage(player);
//
//    if (isMultiArmorSetEquipped(player) && hasEnergy(player)) {
//      IGenericDataStorage armorDataStorage = getHelmetDataStorage(player);
//      Map<String, Boolean> armorBooleans = armorDataStorage.getBooleanMap();
//
//      playerDataStorage.getBooleanMap().put(set, true);
//
//      AttributeModifier modifier = player.getAttribute(Attributes.MAX_HEALTH).getModifier(HEALTH_MODIFIER);
//      if (modifier == null) {
//        player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier(
//            HEALTH_MODIFIER,
//            "Max Health",
//            100,
//            AttributeModifier.Operation.ADDITION));
//      }
//
//      if (armorBooleans.getOrDefault(DataKeys.FLIGHT, Default.FLIGHT)) {
//        tryEnableFlight(player, playerDataStorage, armorDataStorage, event.side);
//      } else {
//        disableFlight(player, event.side);
//      }
//      if (armorBooleans.getOrDefault(DataKeys.FEED, Default.FEED)) {
//        tryFeedPlayer(player, event.side);
//      }
//      if (armorBooleans.getOrDefault(DataKeys.HEAL, Default.HEAL)) {
//        tryHealPlayer(player, event.side);
//      }
//      if (armorBooleans.getOrDefault(DataKeys.REMOVE_HARMFUL, Default.REMOVE_HARMFUL)) {
//        tryRemoveHarmful(player, event.side);
//      }
//      if (armorBooleans.getOrDefault(DataKeys.EXTINGUISH, Default.EXTINGUISH)) {
//        tryExtinguish(player, event.side);
//      }
//      if (armorBooleans.getOrDefault(DataKeys.GIVE_AIR, Default.GIVE_AIR)) {
//        tryGiveAir(player, event.side);
//      }
//      tryGroundSpeed(player, armorDataStorage, event.side);
//    } else {
//      Map<String, Boolean> boolMap = playerDataStorage.getBooleanMap();
//      if (boolMap.containsKey(set) && boolMap.get(set)) {
//        boolMap.put(set, false);
//        disableFlight(player, event.side);
//        disableNoClip(player, playerDataStorage);
//        disableGroundSpeed(player, event.side);
//        player.getAttribute(Attributes.MAX_HEALTH).removeModifier  (HEALTH_MODIFIER);
//        player.setHealth(Math.min(player.getHealth(), player.getMaxHealth()));
//      }
//    }
//  }

  private void tryGroundSpeed(
      Player player, IGenericDataStorage armorDataStorage, LogicalSide side) {
    float groundSpeed =
        armorDataStorage.getFloatMap().getOrDefault(DataKeys.GROUND_SPEED, Default.GROUND_SPEED);

//    float powerRequired =
//        (float)
//            ((player.walkDist - player.walkDistO)
//                / 0.6F
//                * OverloadedConfig.INSTANCE.multiArmorConfig.energyPerBlockWalked
//                * OverloadedConfig.INSTANCE.multiArmorConfig.energyMultiplierPerGroundSpeed
//                * (groundSpeed - Default.GROUND_SPEED));
//
//    if (extractEnergy(player, Math.round(powerRequired), side == LogicalSide.CLIENT)) {
//      AttributeModifier modifier = new AttributeModifier(
//          groundSpeedAttribute,
//          "Ground Speed modifier",
//          groundSpeed,
//          AttributeModifier.Operation.ADDITION);
//      if(!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(modifier)) {
//        player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(modifier);
//      }
//    } else {
//      disableGroundSpeed(player, side);
//    }
  }

//  private void disableGroundSpeed(Player player, LogicalSide side) {
//    player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).removeModifier(groundSpeedAttribute);
//  }

  private void disableNoClip(Player player, IGenericDataStorage dataStorage) {
    player.noPhysics = false;
    dataStorage.getBooleanMap().put(noClip, false);
  }

  private void tryEnableNoClip(
      Player player,
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
        player.noPhysics = true;
        if (armorBooleans.getOrDefault(DataKeys.NOCLIP_FLIGHT_LOCK, Default.NOCLIP_FLIGHT_LOCK)) {
          tryEnableFlight(player, dataStorage, helmetDataStorage, side);
          player.getAbilities().flying = true;
        }
      } else {
        disableNoClip(player, dataStorage);
      }
    }
  }

  private void tryGiveAir(Player player, LogicalSide side) {
    int airNeeded = 300 - player.getAirSupply();

    if (airNeeded > 0
        && extractEnergy(
            player,
            airNeeded * OverloadedConfig.INSTANCE.multiArmorConfig.costPerAir,
            side == LogicalSide.CLIENT)) {
      player.setAirSupply(300);
    }
  }

  private void tryExtinguish(@Nonnull Player player, @Nonnull LogicalSide side) {
    if (player.isOnFire()
        && extractEnergy(
            player,
            OverloadedConfig.INSTANCE.multiArmorConfig.extinguishCost,
            side == LogicalSide.CLIENT)) {
      player.clearFire();
    }
  }

  private void tryHealPlayer(@Nonnull Player player, @Nonnull LogicalSide side) {
    float currentHealth = player.getHealth();
    float maxHealth = player.getMaxHealth();

    int toHeal = (int) Math.ceil(maxHealth - currentHealth);
    if (toHeal > 0
        && extractEnergy(
            player,
            OverloadedConfig.INSTANCE.multiArmorConfig.costPerHealth * toHeal,
            side == LogicalSide.CLIENT)) {
      player.heal(toHeal);
    }
  }

  private void tryRemoveHarmful(@Nonnull Player player, @Nonnull LogicalSide side) {
    Iterator<MobEffectInstance> potionEffectIterator = player.getActiveEffects().iterator();

    while (potionEffectIterator.hasNext()) {
      MobEffectInstance effect = potionEffectIterator.next();
      MobEffect potion = effect.getEffect().value();
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
        if(!EVENT_BUS.post(new MobEffectEvent.Remove(player, effect.getEffect(), null)).isCanceled()) {
          potionEffectIterator.remove();
        }
      }
    }
  }

  private void tryFeedPlayer(@Nonnull Player player, @Nonnull LogicalSide side) {
    FoodData foodStats = player.getFoodData();
    int foodLevel = foodStats.getFoodLevel();
    int toFeed = OverloadedConfig.INSTANCE.multiArmorConfig.maxFoodLevel - foodLevel;
    float saturationLevel = foodStats.getSaturationLevel();
    float toAdd = OverloadedConfig.INSTANCE.multiArmorConfig.maxFoodLevel - saturationLevel;

    if (toFeed > 0
        && extractEnergy(
            player,
            Math.round(OverloadedConfig.INSTANCE.multiArmorConfig.costPerFood * toFeed),
            side == LogicalSide.CLIENT)) {
      foodStats.eat(toFeed, 0);
    }

    if (toAdd > 0.0F
        && extractEnergy(
            player,
            (int) Math.round(OverloadedConfig.INSTANCE.multiArmorConfig.costPerSaturation * toAdd),
            side == LogicalSide.CLIENT)) {
      toFeed = Math.round(toAdd);
      foodStats.eat(toFeed, 0.5F);
    }
  }

  private void tryEnableFlight(
      @Nonnull Player player,
      @Nonnull IGenericDataStorage dataStorage,
      IGenericDataStorage armorDataStorage,
      @Nonnull LogicalSide side) {
    final Map<String, Boolean> booleans = dataStorage.getBooleanMap();
    final Map<String, Float> armorFloats = armorDataStorage.getFloatMap();

    float flightSpeed = armorFloats.getOrDefault(DataKeys.FLIGHT_SPEED, Default.FLIGHT_SPEED);

    player.getAbilities().mayfly = true;
    if (side == LogicalSide.CLIENT) {
      player.getAbilities().setFlyingSpeed(
          armorFloats.getOrDefault(DataKeys.FLIGHT_SPEED, Default.FLIGHT_SPEED));
    }
    booleans.put(set, true);

    int energyCost =
        (int)
            Math.round(
                OverloadedConfig.INSTANCE.multiArmorConfig.energyPerTickFlying
                    * flightSpeed
                    * OverloadedConfig.INSTANCE.multiArmorConfig.energyMultiplierPerFlightSpeed);

    if (player.getAbilities().flying
        && !extractEnergy(player, energyCost, side == LogicalSide.CLIENT)) {
      disableFlight(player, side);
    }
  }

  private void disableFlight(@Nonnull Player player, @Nonnull LogicalSide side) {
    player.getAbilities().mayfly = false;
    player.getAbilities().flying = false;
    if (side == LogicalSide.CLIENT) {
      player.getAbilities().setFlyingSpeed(0.05F);
    }
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void onLivingAttackedEvent(EntityInvulnerabilityCheckEvent event) {
    Entity entity = event.getEntity();
    if (!(entity instanceof Player)) return;

    Player player = ((Player) entity);
    boolean setEquipped = isMultiArmorSetEquipped(player);

    if (setEquipped) {
      DamageSource damageSource = event.getSource();

      int energyCost = OverloadedConfig.INSTANCE.multiArmorConfig.baseCost;

      float damageAmount = 100;
//          (float) (event..getOriginalDamage() * OverloadedConfig.INSTANCE.multiArmorConfig.damageMultiplier);

      if (damageSource.is(DamageTypeTags.BYPASSES_RESISTANCE))
        damageAmount *= OverloadedConfig.INSTANCE.multiArmorConfig.absoluteDamageMultiplier;


      if (damageSource.is(DamageTypeTags.BYPASSES_ARMOR))
        damageAmount *= OverloadedConfig.INSTANCE.multiArmorConfig.unblockableMultiplier;

      if (damageAmount > Integer.MAX_VALUE) return;

      energyCost += damageAmount;

      // Overflow
      if (energyCost < 0) return;

      if (extractEnergy(player, energyCost, false)) {
        event.setInvulnerable(true);
//        event.setNewDamage(0);
      }
    }
  }

//  @SubscribeEvent
//  public void onLivingUpdateEvent(LivingEvent.LivingTickEvent LivingEvent.LivingUpdateEvent event) {
//    Entity entity = event.getEntity();
//
//    if (entity instanceof Player && isMultiArmorSetEquipped((Player) entity)) {
//      Player player = ((Player) entity);
//      tryEnableNoClip(
//          player, getPlayerDataStorage(player), getHelmetDataStorage(player), LogicalSide.SERVER);
//    }
//  }

  private boolean hasEnergy(Player player) {
    for (ItemStack stack : player.getArmorSlots()) {
      if (stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored() > 0) {
        return true;
      }
    }

    return false;
  }

  private boolean extractEnergy(Player player, int energyCost, boolean simulated) {
    if (energyCost <= 0) {
      return true;
    }

    final int originalCost = energyCost;
    for (ItemStack stack : player.getArmorSlots()) {
      IEnergyStorage opEnergyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
      energyCost -=
          opEnergyStorage.extractEnergy(originalCost / 4, simulated);

      if (energyCost <= 0) {
        return true;
      }
    }

    for (ItemStack stack : player.getArmorSlots()) {
      IEnergyStorage opEnergyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
      final int extractAmount = energyCost;
      energyCost -=
          opEnergyStorage.extractEnergy(extractAmount, simulated);
      if (energyCost <= 0) {
        return true;
      }
    }
    return false;
  }

  @Nonnull
  private static IGenericDataStorage getPlayerDataStorage(Player player) {
    return player.getCapability(GENERIC_DATA_STORAGE_ENTITY);
  }

//  @Nonnull
//  private static IGenericDataStorage getHelmetDataStorage(Player player) {
//    for (ItemStack stack : player.getInventory().armor) {
//      if (stack.getItem() instanceof ItemMultiHelmet) {
//        IGenericDataStorage cap =
//            stack.getCapability(GENERIC_DATA_STORAGE_ITEM);
//        cap.suggestUpdate();
//        return cap;
//      }
//    }
//    return new GenericDataStorage();
//  }

  private boolean isMultiArmorSetEquipped(Player player) {
    for (ItemStack stack : player.getInventory().armor) {
      if (!(stack.getItem() instanceof IMultiArmor)) {
        return false;
      }
    }
    return true;
  }

  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public void onKeyInputEvent(InputEvent.Key event) {
    if (ClientProxy.noClipKeybind.consumeClick()
        && isMultiArmorSetEquipped(Minecraft.getInstance().player)) {
      PacketDistributor.sendToServer(
          new KeyBindPressedMessage(KeyBindPressedMessage.KeyBind.NO_CLIP));
    }
  }

  public static boolean toggleNoClip(ServerPlayer player) {
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

  public static void setNoClip(Player player, boolean enabled) {
    IGenericDataStorage storage = getPlayerDataStorage(player);

    final Map<String, Boolean> booleans = storage.getBooleanMap();
    booleans.put(noClip, enabled);
  }
}
