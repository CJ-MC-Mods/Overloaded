package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ItemInterfaceRenderer extends TileEntityRenderer<TileItemInterface> {

  public ItemInterfaceRenderer(TileEntityRendererDispatcher p_i226006_1_) {
    super(p_i226006_1_);
  }

  @Override
  public void render(@Nonnull TileItemInterface te, float v,@Nonnull  MatrixStack matrixStack,@Nonnull  IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
    ItemStack stack = te.getStoredItem();

    if (stack.isEmpty()) return;

    matrixStack.pushPose();
    matrixStack.translate(0.5,0.32,0.5);

    matrixStack.pushPose();
    long angle = (System.currentTimeMillis() / 10) % 360;
    matrixStack.mulPose(new Quaternion(Vector3f.YN, angle, true));

    RenderSystem.enableLighting();
    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemCameraTransforms.TransformType.GROUND, combinedLightIn,0, matrixStack, iRenderTypeBuffer);
    RenderSystem.disableLighting();
    matrixStack.popPose();
    matrixStack.popPose();
  }
}
