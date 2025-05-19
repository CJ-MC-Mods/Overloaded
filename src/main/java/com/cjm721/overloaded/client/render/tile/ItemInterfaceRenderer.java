package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

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
    // TODO add back spinning
//    matrixStack.mulPose(new Quaternionf(0.0, (double)angle, 0.0, 0.0));

//    RenderSystem.enableLighting();
    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, packedLight,packedOverlay, matrixStack, bufferSource, te.getLevel(), 0);
  //  RenderSystem.disableLighting();
    matrixStack.popPose();
    matrixStack.popPose();
  }

  public static class Provider implements BlockEntityRendererProvider<TileItemInterface> {

    @Override
    public BlockEntityRenderer create(Context context) {
      return new ItemInterfaceRenderer(context.getBlockEntityRenderDispatcher());
    }
  }
}
