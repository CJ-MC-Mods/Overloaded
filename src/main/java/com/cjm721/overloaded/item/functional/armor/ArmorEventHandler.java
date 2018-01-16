package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.KeyBindPressedMessage;
import com.cjm721.overloaded.proxy.ClientProxy;
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
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class ArmorEventHandler {

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(MODID, "playerData"), new MultiArmorCapabilityProvider(((EntityPlayer) event.getObject())));
        }
    }

    private static final String set = "set";
    private static final String noClip = "noClip";

    @SubscribeEvent
    public void onPlayerTickEvent(@Nonnull TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (player == null)
            return;

        IOverloadedPlayerDataStorage dataStorage = getDataStorage(player);

        if (isMultiArmorSetEquipped(player) && hasEnergy(player)) {
            dataStorage.getBooleanMap().put(set, true);

            tryEnableFlight(player, dataStorage, event.side);
            tryFeedPlayer(player, event.side);
            tryHealPlayer(player, event.side);
            tryRemoveHarmful(player, event.side);
            tryExtinguish(player, event.side);
            tryGiveAir(player, event.side);
        } else {
            Map<String, Boolean> boolMap = dataStorage.getBooleanMap();
            if (boolMap.containsKey(set) && boolMap.get(set)) {
                boolMap.put(set, false);
                disableFlight(player, dataStorage, event.side);
                disableNoClip(player, dataStorage, event.side);
            }
        }
    }

    private void disableNoClip(EntityPlayer player, IOverloadedPlayerDataStorage dataStorage, Side side) {
        player.noClip = false;
        dataStorage.getBooleanMap().put(noClip, false);
    }

    private void tryEnableNoClip(EntityPlayer player, IOverloadedPlayerDataStorage dataStorage, Side side) {
        final Map<String, Boolean> booleans = dataStorage.getBooleanMap();
        if (booleans.containsKey(set) && booleans.get(set) && booleans.containsKey(noClip) && booleans.get(noClip)) {
            if (extractEnergy(player, OverloadedConfig.multiArmorConfig.noClipEnergyPerTick, side.isClient())) {
                player.noClip = true;
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

    private void tryEnableFlight(@Nonnull EntityPlayer player, @Nonnull IOverloadedPlayerDataStorage dataStorage, @Nonnull Side side) {
        final Map<String, Boolean> booleans = dataStorage.getBooleanMap();
        player.capabilities.allowFlying = true;
        if (side.isClient()) {
            player.capabilities.setFlySpeed(0.1F);
        }
        booleans.put(set, true);

        if (player.capabilities.isFlying && !extractEnergy(player, OverloadedConfig.multiArmorConfig.energyPerTickFlying, side.isClient())) {
            disableFlight(player, dataStorage, side);
        }
    }

    private void disableFlight(@Nonnull EntityPlayer player, @Nonnull IOverloadedPlayerDataStorage dataStorage, @Nonnull Side side) {
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
            tryEnableNoClip(player, getDataStorage(player), Side.SERVER);
        }
    }

    private boolean hasEnergy(EntityPlayer player) {
        for (ItemStack stack : player.inventory.armorInventory) {
            if (stack.getCapability(ENERGY, null).getEnergyStored() > 0)
                return true;
        }

        return false;
    }

    private boolean extractEnergy(EntityPlayer player, int energyCost, boolean simulated) {
        final int orignalCost = energyCost;
        for (ItemStack stack : player.inventory.armorInventory) {
            IEnergyStorage energyStorage = stack.getCapability(ENERGY, null);

            if (energyStorage != null)
                energyCost -= energyStorage.extractEnergy(orignalCost / 4, simulated);

            if (energyCost <= 0) {
                return true;
            }
        }

        for (ItemStack stack : player.inventory.armorInventory) {
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
    private static IOverloadedPlayerDataStorage getDataStorage(EntityPlayer player) {
        return player.getCapability(MultiArmorCapabilityProvider.PLAYER_DATA_STORAGE, null);
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
        IOverloadedPlayerDataStorage storage = getDataStorage(player);

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
        IOverloadedPlayerDataStorage storage = getDataStorage(player);

        final Map<String, Boolean> booleans = storage.getBooleanMap();
        booleans.put(noClip, enabled);
    }
}
