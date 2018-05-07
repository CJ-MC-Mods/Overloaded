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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.DataKeys;
import static com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.Default;
import static com.cjm721.overloaded.storage.GenericDataStorage.GENERIC_DATA_STORAGE;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class ArmorEventHandler {

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(MODID, "playerData"), new GenericDataCapabilityProvider());
        }
    }

    private static final String set = "set";
    private static final String noClip = "noClip";

    @SubscribeEvent
    public void onPlayerTickEvent(@Nonnull TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (player == null)
            return;

        IGenericDataStorage playerDataStorage = getPlayerDataStorage(player);

        if (isMultiArmorSetEquipped(player) && hasEnergy(player)) {
            IGenericDataStorage armorDataStorage = getHelmetDataStorage(player);
            Map<String, Boolean> armorBooleans = armorDataStorage.getBooleanMap();

            playerDataStorage.getBooleanMap().put(set, true);

            if (armorBooleans.getOrDefault(DataKeys.FLIGHT, Default.FLIGHT)) {
                tryEnableFlight(player, playerDataStorage, armorDataStorage, event.side);
            } else {
                disableFlight(player, playerDataStorage, event.side);
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
            tryGroundSpeed(player, playerDataStorage, armorDataStorage, event.side);
        } else {
            Map<String, Boolean> boolMap = playerDataStorage.getBooleanMap();
            if (boolMap.containsKey(set) && boolMap.get(set)) {
                boolMap.put(set, false);
                disableFlight(player, playerDataStorage, event.side);
                disableNoClip(player, playerDataStorage, event.side);
                disableGroundSpeed(player);
            }
        }
    }

    private void tryGroundSpeed(EntityPlayer player, IGenericDataStorage playerDataStorage, IGenericDataStorage armorDataStorage, Side side) {
        float groundSpeed = armorDataStorage.getFloatMap().getOrDefault(DataKeys.GROUND_SPEED, Default.GROUND_SPEED);

        float powerRequired = (player.distanceWalkedModified - player.prevDistanceWalkedModified) / 0.6F *
                OverloadedConfig.multiArmorConfig.energyPerBlockWalked *
                OverloadedConfig.multiArmorConfig.energyMultiplierPerGroundSpeed * groundSpeed;

        if (extractEnergy(player, Math.round(powerRequired), side.isClient())) {
            player.capabilities.setPlayerWalkSpeed(groundSpeed);
        } else {
            disableGroundSpeed(player);
        }
    }

    private void disableGroundSpeed(EntityPlayer player) {
        player.capabilities.setPlayerWalkSpeed(0.1F);
    }

    private void disableNoClip(EntityPlayer player, IGenericDataStorage dataStorage, Side side) {
        player.noClip = false;
        dataStorage.getBooleanMap().put(noClip, false);
    }

    private void tryEnableNoClip(EntityPlayer player, IGenericDataStorage dataStorage, IGenericDataStorage helmetDataStorage, Side side) {
        final Map<String, Boolean> playerBooleans = dataStorage.getBooleanMap();
        final Map<String, Boolean> armorBooleans = helmetDataStorage.getBooleanMap();

        if (playerBooleans.containsKey(set) && playerBooleans.get(set) && playerBooleans.containsKey(noClip) && playerBooleans.get(noClip)) {
            if (extractEnergy(player, OverloadedConfig.multiArmorConfig.noClipEnergyPerTick, side.isClient())) {
                player.noClip = true;
                if (armorBooleans.getOrDefault(DataKeys.NOCLIP_FLIGHT_LOCK, Default.NOCLIP_FLIGHT_LOCK)) {
                    tryEnableFlight(player, dataStorage, helmetDataStorage, side);
                    player.capabilities.isFlying = true;
                }
            } else {
                setNoClip(player, false);
            }
        }
    }

    private void tryGiveAir(EntityPlayer player, Side side) {
        int airNeeded = 300 - player.getAir();

        if (airNeeded > 0 && extractEnergy(player, airNeeded * OverloadedConfig.multiArmorConfig.costPerAir, side.isClient())) {
            player.setAir(300);
        }
    }

    private void tryExtinguish(@Nonnull EntityPlayer player, @Nonnull Side side) {
        if (player.isBurning() && extractEnergy(player, OverloadedConfig.multiArmorConfig.extinguishCost, side.isClient())) {
            player.extinguish();
        }
    }

    private void tryHealPlayer(@Nonnull EntityPlayer player, @Nonnull Side side) {
        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();

        int toHeal = (int) Math.ceil(maxHealth - currentHealth);
        if (toHeal > 0 && extractEnergy(player, OverloadedConfig.multiArmorConfig.costPerHealth * toHeal, side.isClient())) {
            player.setHealth(maxHealth);
        }
    }

    private void tryRemoveHarmful(@Nonnull EntityPlayer player, @Nonnull Side side) {
        Iterator<PotionEffect> potionEffectIterator = player.getActivePotionEffects().iterator();

        while (potionEffectIterator.hasNext()) {
            PotionEffect effect = potionEffectIterator.next();
            Potion potion = effect.getPotion();
            if (!potion.isBadEffect())
                continue;

            if (extractEnergy(player, OverloadedConfig.multiArmorConfig.removeEffect, side.isClient())) {
                potionEffectIterator.remove();
            }
        }
    }

    private void tryFeedPlayer(@Nonnull EntityPlayer player, @Nonnull Side side) {
        FoodStats foodStats = player.getFoodStats();
        int foodLevel = foodStats.getFoodLevel();
        int toFeed = OverloadedConfig.multiArmorConfig.maxFoodLevel - foodLevel;
        float staturationLevel = foodStats.getSaturationLevel();
        float toAdd = OverloadedConfig.multiArmorConfig.maxFoodLevel - staturationLevel;

        if (toFeed > 0 && extractEnergy(player, Math.round(OverloadedConfig.multiArmorConfig.costPerFood * toFeed), side.isClient())) {
            foodStats.addStats(toFeed, 0);
        }

        if (toAdd > 0.0F && extractEnergy(player, Math.round(OverloadedConfig.multiArmorConfig.costPerSaturation * toAdd), side.isClient())) {
            toFeed = Math.round(toAdd);
            foodStats.addStats(toFeed, 0.5F);
        }
    }

    private void tryEnableFlight(@Nonnull EntityPlayer player, @Nonnull IGenericDataStorage dataStorage, IGenericDataStorage armorDataStorage, @Nonnull Side side) {
        final Map<String, Boolean> booleans = dataStorage.getBooleanMap();
        final Map<String, Float> armorFloats = armorDataStorage.getFloatMap();

        float flightSpeed = armorFloats.getOrDefault(DataKeys.FLIGHT_SPEED, Default.FLIGHT_SPEED);

        player.capabilities.allowFlying = true;
        if (side.isClient()) {
            player.capabilities.setFlySpeed(armorFloats.getOrDefault(DataKeys.FLIGHT_SPEED, Default.FLIGHT_SPEED));
        }
        booleans.put(set, true);

        int energyCost = Math.round(OverloadedConfig.multiArmorConfig.energyPerTickFlying * flightSpeed * OverloadedConfig.multiArmorConfig.energyMultiplerPerFlightSpeed);

        if (player.capabilities.isFlying && !extractEnergy(player, energyCost, side.isClient())) {
            disableFlight(player, dataStorage, side);
        }
    }

    private void disableFlight(@Nonnull EntityPlayer player, @Nonnull IGenericDataStorage dataStorage, @Nonnull Side side) {
        player.capabilities.allowFlying = false;
        player.capabilities.isFlying = false;
        if (side.isClient()) {
            player.capabilities.setFlySpeed(0.05F);
        }
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingAttackedEvent(LivingAttackEvent event) {
        Entity entity = event.getEntity();
        if (entity == null || !(entity instanceof EntityPlayer))
            return;

        EntityPlayer player = ((EntityPlayer) entity);
        boolean setEquipped = isMultiArmorSetEquipped(player);

        if (setEquipped) {
            DamageSource damageSource = event.getSource();

            int energyCost = OverloadedConfig.multiArmorConfig.baseCost;

            float damageAmount = event.getAmount() * OverloadedConfig.multiArmorConfig.damageMultiplier;

            if (damageSource.isDamageAbsolute())
                damageAmount *= OverloadedConfig.multiArmorConfig.absoluteDamageMultiplier;

            if (damageSource.isUnblockable())
                damageAmount *= OverloadedConfig.multiArmorConfig.unblockableMultiplier;

            if (damageAmount > Integer.MAX_VALUE)
                return;

            energyCost += damageAmount;

            // Overflow
            if (energyCost < 0)
                return;

            if (extractEnergy(player, energyCost, false))
                event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = ((EntityPlayer) entity);
            tryEnableNoClip(player, getPlayerDataStorage(player), getHelmetDataStorage(player), Side.SERVER);
        }
    }

    private boolean hasEnergy(EntityPlayer player) {
        for (ItemStack stack : player.getArmorInventoryList()) {
            if (stack.getCapability(ENERGY, null).getEnergyStored() > 0)
                return true;
        }

        return false;
    }

    private boolean extractEnergy(EntityPlayer player, int energyCost, boolean simulated) {
        final int orignalCost = energyCost;
        for (ItemStack stack : player.getArmorInventoryList()) {
            IEnergyStorage energyStorage = stack.getCapability(ENERGY, null);

            if (energyStorage != null)
                energyCost -= energyStorage.extractEnergy(orignalCost / 4, simulated);

            if (energyCost <= 0) {
                return true;
            }
        }

        for (ItemStack stack : player.getArmorInventoryList()) {
            IEnergyStorage energyStorage = stack.getCapability(ENERGY, null);

            if (energyStorage != null)
                energyCost -= energyStorage.extractEnergy(energyCost, simulated);
            if (energyCost == 0) {
                return true;
            }
        }
        return false;
    }

    @Nonnull
    private static IGenericDataStorage getPlayerDataStorage(EntityPlayer player) {
        return player.getCapability(GENERIC_DATA_STORAGE, null);
    }

    @Nonnull
    private static IGenericDataStorage getHelmetDataStorage(EntityPlayer player) {
        for (ItemStack stack : player.inventory.armorInventory) {
            if (stack.getItem() instanceof ItemMultiHelmet) {
                IGenericDataStorage cap = stack.getCapability(GENERIC_DATA_STORAGE, null);
                cap.suggestUpdate();
                return cap;
            }
        }
        return new GenericDataStorage();
    }

    private boolean isMultiArmorSetEquipped(EntityPlayer player) {
        boolean setEquipped = true;
        for (ItemStack stack : player.inventory.armorInventory) {
            if (!(stack.getItem() instanceof IMultiArmor)) {
                setEquipped = false;
            }
        }
        return setEquipped;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInputEvent(InputEvent.KeyInputEvent event) {
        if (((ClientProxy) Overloaded.proxy).noClipKeybind.isPressed() && isMultiArmorSetEquipped(Minecraft.getMinecraft().player)) {
            IMessage message = new KeyBindPressedMessage(KeyBindPressedMessage.KeyBind.NO_CLIP);
            Overloaded.proxy.networkWrapper.sendToServer(message);
        }
    }

    public static boolean toggleNoClip(EntityPlayerMP player) {
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

    public static void setNoClip(EntityPlayer player, boolean enabled) {
        IGenericDataStorage storage = getPlayerDataStorage(player);

        final Map<String, Boolean> booleans = storage.getBooleanMap();
        booleans.put(noClip, enabled);
    }
}
