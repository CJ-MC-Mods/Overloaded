package com.cjm721.overloaded.network;

import com.cjm721.overloaded.client.gui.MultiArmorGuiScreen;
import net.minecraft.client.Minecraft;

public class OverloadedGuiHandler {
  public static void openMultiArmorGUI() {
    Minecraft.getInstance().setScreen(new MultiArmorGuiScreen());
  }
}
