package com.cjm721.overloaded.util;

import javax.annotation.Nonnull;

public enum AssistMode {
    NONE(0,"None"),
    PREVIEW(1,"Preview");

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
