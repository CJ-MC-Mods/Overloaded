package com.cjm721.overloaded.item.functional.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;

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

        IOverloadedPlayerDataStorage dataStorage = player.getCapability(MultiArmorCapabilityProvider.PLAYER_DATA_STORAGE, null);

//        Map<String, Integer> integers = dataStorage.getIntegerMap();
        Map<String, Boolean> booleans = dataStorage.getBooleanMap();

        boolean setEquipped = true;
        for(ItemStack stack: player.inventory.armorInventory) {
            if( !(stack.getItem() instanceof  IMultiArmor)) {
                setEquipped = false;
            }
        }
        if (setEquipped) {
            player.capabilities.allowFlying = true;
            if(event.side == Side.CLIENT) {
                player.capabilities.setFlySpeed(0.5F);
                player.capabilities.setPlayerWalkSpeed(1F);
                player.getFoodStats().setFoodSaturationLevel(20);
            }
            player.getFoodStats().setFoodLevel(20);
            booleans.put(set, true);
        }else if(!player.capabilities.isCreativeMode && !player.isSpectator() && booleans.containsKey(set) && booleans.get(set)){
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
            if(event.side == Side.CLIENT) {
                player.capabilities.setFlySpeed(0.05F);
                player.capabilities.setPlayerWalkSpeed(0.1F);
            }
            booleans.put(set,false);
        }
    }
}
