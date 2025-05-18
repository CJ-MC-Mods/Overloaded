package com.cjm721.overloaded.client.gui;

import com.cjm721.overloaded.network.container.InstantFurnaceContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class InstantFurnaceScreen extends AbstractContainerScreen<InstantFurnaceContainer> {

//  private static final ResourceLocation INSTANT_FURNACE_GUI_TEXTURES =
//      new ResourceLocation(MODID, "textures/gui/instant_furnace.png");

  public InstantFurnaceScreen(
      InstantFurnaceContainer container, Inventory playerInventory, Component name) {
    super(container, playerInventory, name);
    this.imageHeight += 24;
    this.inventoryLabelY += 24;
  }

  @Override
  public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
    super.render(matrixStack, mouseX, mouseY, partialTicks);
  }

  @Override
  protected void renderBg(GuiGraphics matrixStack, float partialTicks, int x, int y) {
//    renderBackground(matrixStack, x, y, partialTicks);
//    this.minecraft.getTextureManager().bind(INSTANT_FURNACE_GUI_TEXTURES);
//    int i = this.leftPos;
//    int j = this.topPos;
//    this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
//
//    double percent = this.menu.getPowerFromTE() / (double) this.menu.getMaxPowerFromTE();
//
//    this.blit(matrixStack, this.leftPos + 8, this.topPos + 79, 0, 194, (int) Math.round(percent * 160), 10);
//
//    String text = String.format("%,.2f%%", percent * 100);
//
//    this.font.draw(matrixStack,
//        text,
//        this.leftPos + this.imageWidth / 2.0f - this.font.width(text) / 2.0f,
//        this.topPos + 80,
//        0);
  }
}
