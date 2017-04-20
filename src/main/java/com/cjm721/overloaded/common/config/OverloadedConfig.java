package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nonnull;

public enum OverloadedConfig implements IConfig {
    I;

    @Override
    public void init(@Nonnull Configuration configuration) {
        CompressedConfig.I.init(configuration);
        MultiToolConfig.I.init(configuration);
        RecipeEnabledConfig.I.init(configuration);
    }
}
