package com.cjm721.overloaded.client.gui.button;

import net.minecraft.client.gui.GuiButton;

import javax.annotation.Nonnull;

public class GuiToggle extends GuiButton {

    private boolean booleanState;
    private String baseText;

    public GuiToggle(int buttonId, int x, int y, boolean startingState, @Nonnull String baseText) {
        super(buttonId, x, y, 150, 20, baseText);
        this.baseText = baseText;
        this.booleanState = startingState;
        this.displayString = String.format("%s %b", baseText, startingState);
    }

    public void toggle() {
        booleanState = !booleanState;
        this.displayString = String.format("%s %b", baseText, booleanState);
    }

    public boolean getBooleanState() {
        return this.booleanState;
    }
}
