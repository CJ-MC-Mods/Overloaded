package com.cjm721.overloaded.config;

import com.cjm721.overloaded.network.handler.ConfigSyncHandler;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.cjm721.overloaded.Overloaded.MODID;

@Mod.EventBusSubscriber
class ConfigChangedHandler {

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Config.Type.INSTANCE);

            ConfigSyncHandler.INSTANCE.updateConfig();
        }
    }
}
