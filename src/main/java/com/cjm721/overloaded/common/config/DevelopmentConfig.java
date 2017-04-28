package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nonnull;

/**
 * Created by CJ on 4/27/2017.
 */
public enum DevelopmentConfig implements IConfig {
    I;

    public boolean wipStuff;

    private final String category = "development";

    @Override
    public void init(@Nonnull Configuration config) {
        config.addCustomCategoryComment(category, "WIP and other items that are not meant for normal use. Anything in the section should be expected to break your game.");

        wipStuff = config.get(category, "wipStuff", false).getBoolean();
    }
}
