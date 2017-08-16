package com.cjm721.overloaded.util;

import javax.annotation.Nonnull;

public enum AssistMode {
    NONE(0, "None"),
    PLACE_PREVIEW(1, "Place Preview"),
    REMOVE_PREVIEW(2, "Remove Preview"),
    BOTH_PREVIEW(3, "Both Preview");

    private final int mode;
    private final String name;

    AssistMode(int mode, @Nonnull String name) {
        this.mode = mode;
        this.name = name;
    }

    public int getMode() {
        return mode;
    }

    @Nonnull
    public String getName() {
        return name;
    }
}
