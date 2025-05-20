package com.cjm721.overloaded.client.gui;

import com.cjm721.overloaded.network.menu.InstantFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.cjm721.overloaded.Overloaded.MODID;

public class InstantFurnaceScreen extends AbstractContainerScreen<InstantFurnaceMenu> {

  private static final ResourceLocation INSTANT_FURNACE_GUI_TEXTURES =
      ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/instant_furnace.png");

  public InstantFurnaceScreen(
      InstantFurnaceMenu container, Inventory playerInventory, Component name) {
    super(container, playerInventory, name);
    this.imageHeight += 24;
    this.inventoryLabelY += 24;
  }

  @Override
  public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(matrixStack, mouseX, mouseY, partialTicks);
    super.render(matrixStack, mouseX, mouseY, partialTicks);
    this.renderTooltip(matrixStack, mouseX, mouseY);
  }

  @Override
  protected void renderBg(GuiGraphics matrixStack, float partialTicks, int x, int y) {
    //    this.minecraft.getTextureManager().bindForSetup(INSTANT_FURNACE_GUI_TEXTURES);
    int i = this.leftPos;
    int j = this.topPos;

    matrixStack.blit(INSTANT_FURNACE_GUI_TEXTURES, i, j, 0, 0, this.imageWidth, this.imageHeight);

    double percent = this.menu.getPowerFromTE() / (double) this.menu.getMaxPowerFromTE();

    matrixStack.blit(
        INSTANT_FURNACE_GUI_TEXTURES,
        this.leftPos + 8,
        this.topPos + 79,
        0,
        194,
        (int) Math.round(percent * 160),
        10);

    String text = String.format("%,.2f%%", percent * 100);

    matrixStack.drawString(
        this.font, text, this.leftPos + 90 - this.font.width(text) / 2, this.topPos + 80, 0xFFFFFF);
    //    this.font.drawInBatch(text,this.leftPos + this.imageWidth / 2.0f - this.font.width(text) /
    // 2.0f,this.topPos + 80,
    //            0);
    //    this.font.draw(
    //        matrixStack,
    //        text,
    //        this.leftPos + this.imageWidth / 2.0f - this.font.width(text) / 2.0f,
    //        this.topPos + 80,
    //        0);
  }
}
