package com.cjm721.overloaded.network.handler;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.config.syncer.ConfigSyncMerger;
import com.cjm721.overloaded.network.packets.ConfigSyncMessage;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.config.ForgeOverloadedConfigHolder.overloadedConfig;

public class ConfigSyncHandler implements IMessageHandler<ConfigSyncMessage, IMessage> {
    public static ConfigSyncHandler INSTANCE = new ConfigSyncHandler();

    private final ConfigSyncMerger configSyncMerger;
    private OverloadedConfig serverConfigOptions;

    private ConfigSyncHandler() {
        configSyncMerger = new ConfigSyncMerger();
    }

    @Override
    @Nullable
    public IMessage onMessage(ConfigSyncMessage message, MessageContext ctx) {
        OverloadedConfig serverConfigEntires = new Gson().fromJson(message.config, OverloadedConfig.class);
        this.serverConfigOptions = serverConfigEntires;
        updateConfig();
        return null;
    }

    public void updateConfig() {
        Gson gson = new Gson();
        Overloaded.cachedConfig = gson.fromJson(gson.toJsonTree(overloadedConfig), OverloadedConfig.class);

        if(this.serverConfigOptions == null) {
            return;
        }

        try {
            configSyncMerger.updateConfigFromServer(Overloaded.cachedConfig, this.serverConfigOptions);
        } catch (IllegalAccessException e) {
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Unable to sync settings with server. Some GUIs / items may not behave as expected."));
            e.printStackTrace();
        }
    }
}
