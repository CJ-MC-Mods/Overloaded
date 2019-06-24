package com.cjm721.overloaded.network.handler;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.config.syncer.ConfigSyncMerger;
import com.cjm721.overloaded.network.packets.ConfigSyncMessage;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ConfigSyncHandler
    implements BiConsumer<ConfigSyncMessage, Supplier<NetworkEvent.Context>> {
  public static ConfigSyncHandler INSTANCE = new ConfigSyncHandler();

  private final ConfigSyncMerger configSyncMerger;
  private OverloadedConfig serverConfigOptions;

  private ConfigSyncHandler() {
    configSyncMerger = new ConfigSyncMerger();
  }

  public void updateConfig() {
    Gson gson = new Gson();
    //    OverloadedConfig.INSTANCE =
    //        gson.fromJson(gson.toJsonTree(overloadedConfig), OverloadedConfig.class);

    if (this.serverConfigOptions == null) {
      return;
    }

    try {
      configSyncMerger.updateConfigFromServer(OverloadedConfig.INSTANCE, this.serverConfigOptions);
    } catch (IllegalAccessException e) {
      Minecraft.getInstance()
          .player
          .sendMessage(
              new StringTextComponent(
                  "Unable to sync settings with server. Some GUIs / items may not behave as expected."));
      e.printStackTrace();
    }
  }

  public void clearServerConfigOptions() {
    this.serverConfigOptions = null;
  }

  @Override
  public void accept(ConfigSyncMessage message, Supplier<NetworkEvent.Context> ctx) {
    OverloadedConfig serverConfigEntires =
        new Gson().fromJson(message.config, OverloadedConfig.class);
    this.serverConfigOptions = serverConfigEntires;
    updateConfig();
  }
}
