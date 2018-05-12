package com.cjm721.overloaded.config.syncer;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.ForgeOverloadedConfigHolder;
import com.cjm721.overloaded.network.packets.ConfigSyncMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nonnull;

public class ConfigSyncEventHandler {
    @Nonnull private final Gson gson;

    public ConfigSyncEventHandler() {
        gson = new GsonBuilder().setExclusionStrategies(new ShouldSyncExclusionStrategy()).create();
    }

    @SubscribeEvent
    public void onPlayerLogin(@Nonnull PlayerEvent.PlayerLoggedInEvent event) {
        String config = gson.toJson(ForgeOverloadedConfigHolder.overloadedConfig);
        Overloaded.proxy.networkWrapper.sendTo(new ConfigSyncMessage(config), (EntityPlayerMP) event.player);
    }
}
