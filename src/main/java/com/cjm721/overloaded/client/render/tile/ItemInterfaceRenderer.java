package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ItemInterfaceRenderer extends TileEntityRenderer<TileItemInterface> {

  public ItemInterfaceRenderer(TileEntityRendererDispatcher p_i226006_1_) {
    super(p_i226006_1_);
  }

  @Override
  public void render(@Nonnull TileItemInterface te, float v,@Nonnull  MatrixStack matrixStack,@Nonnull  IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
    GlStateManager.pushLightingAttributes();
    GlStateManager.pushMatrix();

    GlStateManager.translated(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
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
    GlStateManager.scalef(0.25f, 0.25f, 0.25f);
    long angle = (System.currentTimeMillis() / 10) % 360;
    GlStateManager.rotatef(angle, 0, 1, 0);

//    Minecraft.getInstance()
//        .getItemRenderer()
//        .renderItem(stack, ItemCameraTransforms.TransformType.NONE);

    GlStateManager.popMatrix();
  }
}
