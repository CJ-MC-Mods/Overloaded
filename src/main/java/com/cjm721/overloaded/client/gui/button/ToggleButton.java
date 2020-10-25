package com.cjm721.overloaded.client.gui.button;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public class ToggleButton extends Button {

  private boolean booleanState;
  private final String baseText;

  public ToggleButton(int x, int y, boolean startingState, @Nonnull String baseText) {
    super(x, y, 150, 20, new StringTextComponent(baseText), b -> ((ToggleButton)b).toggle());
    this.baseText = baseText;
    this.booleanState = startingState;
    this.setMessage(new StringTextComponent(String.format("%s %b", baseText, startingState)));
  }

  public void toggle() {
    booleanState = !booleanState;
    this.setMessage(new StringTextComponent(String.format("%s %b", baseText, booleanState)));
  }

  public boolean getBooleanState() {
    return this.booleanState;
  }
}
