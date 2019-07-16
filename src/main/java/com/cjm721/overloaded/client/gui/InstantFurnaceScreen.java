package com.cjm721.overloaded.client.gui;

import com.cjm721.overloaded.network.container.InstantFurnaceContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class InstantFurnaceScreen extends ContainerScreen<InstantFurnaceContainer> implements IHasContainer<InstantFurnaceContainer> {

  public InstantFurnaceScreen(InstantFurnaceContainer container, PlayerInventory playerInventory, ITextComponent name) {
    super(container, playerInventory, name);
  }

  @Override
  public void render(int mouseX, int mouseY, float partialTicks) {
    super.render(mouseX, mouseY, partialTicks);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    renderBackground();
  }
}
