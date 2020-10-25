package com.cjm721.overloaded.client.gui;

import com.cjm721.overloaded.network.container.InstantFurnaceContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class InstantFurnaceScreen extends ContainerScreen<InstantFurnaceContainer>
    implements IHasContainer<InstantFurnaceContainer> {

  private static final ResourceLocation INSTANT_FURNACE_GUI_TEXTURES =
      new ResourceLocation(MODID, "textures/gui/instant_furnace.png");

  public InstantFurnaceScreen(
      InstantFurnaceContainer container, PlayerInventory playerInventory, ITextComponent name) {
    super(container, playerInventory, name);
  }

  @Override
  public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    super.render(matrixStack, mouseX, mouseY, partialTicks);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
    renderBackground(matrixStack);
    this.minecraft.getTextureManager().bindTexture(INSTANT_FURNACE_GUI_TEXTURES);
    int i = this.guiLeft;
    int j = this.guiTop;
    this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

    double percent = this.container.getPowerFromTE() / (double) this.container.getMaxPowerFromTE();

    this.blit(matrixStack, this.guiLeft + 8, this.guiTop + 67, 0, 170, (int) Math.round(percent * 160), 10);

    String text = String.format("%,.2f%%", percent * 100);

    this.font.drawString(matrixStack,
        text,
        this.guiLeft + this.xSize / 2.0f - this.font.getStringWidth(text) / 2.0f,
        this.guiTop + 68,
        0);
  }
}
