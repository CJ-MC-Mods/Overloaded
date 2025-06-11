package com.cjm721.overloaded.client.gui.button;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public class ToggleButton extends Button {

  private boolean booleanState;
  private final String baseText;

  public ToggleButton(int x, int y, boolean startingState, @Nonnull String baseText) {
    super(
        x,
        y,
        150,
        20,
        Component.literal(baseText),
        b -> ((ToggleButton) b).toggle(),
        Button.DEFAULT_NARRATION);

    this.baseText = baseText;
    this.booleanState = startingState;
    this.setMessage(Component.literal(String.format("%s %b", baseText, startingState)));
  }

  public void toggle() {
    booleanState = !booleanState;
    this.setMessage(Component.literal(String.format("%s %b", baseText, booleanState)));
  }

  public boolean getBooleanState() {
    return this.booleanState;
  }
}
