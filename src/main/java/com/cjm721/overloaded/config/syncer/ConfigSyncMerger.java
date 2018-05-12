package com.cjm721.overloaded.config.syncer;

import com.cjm721.overloaded.config.OverloadedConfig;

import java.lang.reflect.Field;

public class ConfigSyncMerger {
    public void updateConfigFromServer(OverloadedConfig configToUpdate, OverloadedConfig serverConfigEntries) throws IllegalAccessException {
        for (Field subConfigField : serverConfigEntries.getClass().getFields()) {
            Object subConfig = subConfigField.get(serverConfigEntries);
            Object baseSubConfig = subConfigField.get(configToUpdate);
            for (Field configEntry : subConfig.getClass().getFields()) {
                if (configEntry.isAnnotationPresent(SyncToClient.class)) {
                    configEntry.set(baseSubConfig, configEntry.get(subConfig));
                }
            }
        }
    }
}
