package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;

public class ItemInterfaceRenderer extends TileEntityRenderer<TileItemInterface> {

  @Override
  public void render(
      TileItemInterface te, double x, double y, double z, float partialTicks, int destroyStage) {
    GlStateManager.pushLightingAttributes();
    GlStateManager.pushMatrix();

    GlStateManager.translated(x, y, z);
    GlStateManager.disableRescaleNormal();

    renderItem(te);

    GlStateManager.popMatrix();
    GlStateManager.popAttributes();
  }

  private void renderItem(TileItemInterface te) {
    ItemStack stack = te.getStoredItem();

    if (stack.isEmpty()) return;

    RenderHelper.enableStandardItemLighting();
    GlStateManager.enableLighting();
    GlStateManager.pushMatrix();

    GlStateManager.translated(.5, .5, .5);
    GlStateManager.scalef(.5f, .5f, .5f);
    long angle = (System.currentTimeMillis() / 10) % 360;
    GlStateManager.rotated(angle, 0, 1, 0);

    Minecraft.getInstance()
        .getItemRenderer()
        .renderItem(stack, ItemCameraTransforms.TransformType.NONE);

    GlStateManager.popMatrix();
  }
}
