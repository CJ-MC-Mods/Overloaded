package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ItemInterfaceRenderer implements BlockEntityRenderer<TileItemInterface> {

  public ItemInterfaceRenderer(BlockEntityRenderDispatcher p_i226006_1_) {}


  @Override
  public void render(@Nonnull TileItemInterface te, float partialTick,PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
    ItemStack stack = te.getStoredItem();

    if (stack.isEmpty()) return;

    matrixStack.pushPose();
    matrixStack.translate(0.5,0.32,0.5);

    matrixStack.pushPose();
    long angle = (System.currentTimeMillis() / 10) % 360;
//    matrixStack.mulPose(new Quaternion(Vector3f.YN, angle, true));

//    RenderSystem.enableLighting();
//    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemCameraTransforms.TransformType.GROUND, combinedLightIn,0, matrixStack, iRenderTypeBuffer);
//    RenderSystem.disableLighting();
    matrixStack.popPose();
    matrixStack.popPose();
  }
}
