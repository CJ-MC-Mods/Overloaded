package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class ArmorEventHandler {

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer){
            
            event.addCapability(new ResourceLocation(MODID, "playerData"), new MultiArmorCapabilityProvider(((EntityPlayer) event.getObject())));
        }
    }

    private static final String set = "set";

    @SubscribeEvent
    public void onLivingUpdateEvent(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;

        IOverloadedPlayerDataStorage dataStorage = getDataStorage(player);

        if (isMultiArmorSetEquipped(player)) {
            tryEnableFlight(player,dataStorage,event.side);
            tryFeedPlayer(player,event.side);
            tryHealPlayer(player,event.side);
            tryRemoveHarmful(player,event.side);
            tryExtinguish(player,event.side);
        } else {
            disableFlight(player,dataStorage, event.side);
        }
    }

    private void tryExtinguish(EntityPlayer player, Side side) {
        if(player.isBurning() && extractEnergy(player,OverloadedConfig.multiArmorConfig.extinguishCost, side.isClient())) {
            player.extinguish();
        }
    }

    private void tryHealPlayer(EntityPlayer player, Side side) {
        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();

        int toHeal = (int) Math.ceil(maxHealth - currentHealth);
        if(toHeal > 0&& extractEnergy(player, OverloadedConfig.multiArmorConfig.costPerHealth * toHeal, side.isClient())) {
            player.setHealth(maxHealth);
        }
    }

    private void tryRemoveHarmful(EntityPlayer player, Side side) {
        for(PotionEffect effect: player.getActivePotionEffects()) {
            Potion potion = effect.getPotion();
            if(!potion.isBadEffect())
                continue;

            if(extractEnergy(player,OverloadedConfig.multiArmorConfig.removeEffect,side.isClient())) {
                player.removeActivePotionEffect(potion);
            }
        }
    }

    private void tryFeedPlayer(EntityPlayer player, Side side) {
        FoodStats foodStats = player.getFoodStats();
        int foodLevel = foodStats.getFoodLevel();
        int toFeed = OverloadedConfig.multiArmorConfig.maxFoodLevel - foodLevel;

        if(toFeed > 0 && extractEnergy(player, OverloadedConfig.multiArmorConfig.costPerFood * toFeed, side.isClient())) {
            foodStats.setFoodLevel(OverloadedConfig.multiArmorConfig.maxFoodLevel);
        }
    }

    private void tryEnableFlight(EntityPlayer player, IOverloadedPlayerDataStorage dataStorage, Side side) {
        final Map<String, Boolean> booleans = dataStorage.getBooleanMap();
        player.capabilities.allowFlying = true;
        if(side.isClient()) {
            player.capabilities.setFlySpeed(0.1F);
        }
        booleans.put(set, true);

        if(player.capabilities.isFlying && !extractEnergy(player, OverloadedConfig.multiArmorConfig.energyPerTickFlying, side.isClient()))
            disableFlight(player,dataStorage,side);
    }

    private void disableFlight(EntityPlayer player, IOverloadedPlayerDataStorage dataStorage, Side side) {
        final Map<String, Boolean> booleans = dataStorage.getBooleanMap();
        if(booleans.containsKey(set) && booleans.get(set)) {
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
            if(side.isClient()) {
                player.capabilities.setFlySpeed(0.05F);
            }
            booleans.put(set,false);
        }
    }



    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingAttackedEvent(LivingAttackEvent event) {
        Entity entity = event.getEntity();
        if(entity == null || !(entity instanceof EntityPlayer))
            return;

        EntityPlayer player = ((EntityPlayer) entity);
        boolean setEquipped = isMultiArmorSetEquipped(player);

        if(setEquipped) {
            DamageSource damageSource = event.getSource();

            int energyCost = OverloadedConfig.multiArmorConfig.baseCost;

            float damageAmount = event.getAmount() * OverloadedConfig.multiArmorConfig.damageMultiplier;

            if(damageSource.isDamageAbsolute())
                damageAmount *= OverloadedConfig.multiArmorConfig.absoluteDamageMultiplier;

            if(damageSource.isUnblockable())
                damageAmount *= OverloadedConfig.multiArmorConfig.unblockableMultiplier;

            if(damageAmount > Integer.MAX_VALUE)
                return;

            energyCost += damageAmount;

            // Overflow
            if(energyCost < 0)
                return;

            if(extractEnergy(player, energyCost, false))
                event.setCanceled(true);
        }
    }

    private boolean extractEnergy(EntityPlayer player, int energyCost, boolean simulated) {
        for(ItemStack stack: player.inventory.armorInventory) {
            IEnergyStorage energyStorage = stack.getCapability(ENERGY,null);

            if(energyStorage != null)
                energyCost -= energyStorage.extractEnergy(energyCost / 4,simulated);

            if(energyCost == 0)
                return true;
        }

        for(ItemStack stack: player.inventory.armorInventory) {
            IEnergyStorage energyStorage = stack.getCapability(ENERGY,null);

            if(energyStorage != null)
                energyCost -= energyStorage.extractEnergy(energyCost,simulated);

            if(energyCost == 0)
                return true;
        }

        return false;
    }

    private IOverloadedPlayerDataStorage getDataStorage(EntityPlayer player) {
        return player.getCapability(MultiArmorCapabilityProvider.PLAYER_DATA_STORAGE, null);
    }

    protected boolean isMultiArmorSetEquipped(EntityPlayer player) {
        boolean setEquipped = true;
        for(ItemStack stack: player.inventory.armorInventory) {
            if( !(stack.getItem() instanceof  IMultiArmor)) {
                setEquipped = false;
            }
        }
        return setEquipped;
    }

}
