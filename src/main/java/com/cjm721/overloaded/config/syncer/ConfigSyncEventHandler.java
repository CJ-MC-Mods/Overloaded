package com.cjm721.overloaded.config.syncer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nonnull;

public class ConfigSyncEventHandler {
  @Nonnull private final Gson gson;

  public ConfigSyncEventHandler() {
    gson = new GsonBuilder().setExclusionStrategies(new ShouldSyncExclusionStrategy()).create();
  }

  @SubscribeEvent
  public void onPlayerLogin(@Nonnull PlayerEvent.PlayerLoggedInEvent event) {
    //        String config = gson.toJson(ForgeOverloadedConfigHolder.overloadedConfig);
    //        Overloaded.proxy.networkWrapper.sendTo(new ConfigSyncMessage(config),
    // (ServerPlayerEntity) event.player);
  }
}
