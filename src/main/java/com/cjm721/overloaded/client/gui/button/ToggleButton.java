package com.cjm721.overloaded.client.gui.button;

import net.minecraft.client.gui.widget.button.Button;

import javax.annotation.Nonnull;

public class ToggleButton extends Button {

  private boolean booleanState;
  private String baseText;

  public ToggleButton(int x, int y, boolean startingState, @Nonnull String baseText) {
    super(x, y, 150, 20, baseText, b -> {
      ((ToggleButton)b).toggle();
    });
    this.baseText = baseText;
    this.booleanState = startingState;
    this.setMessage(String.format("%s %b", baseText, startingState));
  }

  public void toggle() {
    booleanState = !booleanState;
    this.setMessage(String.format("%s %b", baseText, booleanState));
  }

  public boolean getBooleanState() {
    return this.booleanState;
  }
}
