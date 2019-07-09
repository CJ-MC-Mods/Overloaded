package com.cjm721.overloaded.network;

import com.cjm721.overloaded.client.gui.MultiArmorGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nullable;

public class OverloadedGuiHandler {

  public static final int MULTI_ARMOR = 0;

  @Nullable
  public static Screen openGUI(FMLPlayMessages.OpenContainer openContainer) {
    switch (openContainer.getWindowId()) {
      case MULTI_ARMOR:
//        return new MultiArmorGuiScreen();
    }

    return null;
  }

  public static void openMultiArmorGUI() {
    Minecraft.getInstance().displayGuiScreen(new MultiArmorGuiScreen());
  }
}
