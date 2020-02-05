package com.cjm721.overloaded.util;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

public class RenderUtil {
  @OnlyIn(Dist.CLIENT)
  private static void renderModel(
      final IBakedModel model,
      final BlockState state,
      final World worldObj,
      final BlockPos blockPos,
      final int alpha) {
    final Tessellator tessellator = Tessellator.getInstance();
    final BufferBuilder worldRenderer = tessellator.getBuffer();
    worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

    try {
      for (final Direction Direction : Direction.values()) {
        renderQuads(
            alpha, worldRenderer, model.getQuads(null, Direction, new Random()), state, worldObj, blockPos);
      }
      renderQuads(alpha, worldRenderer, model.getQuads(null, null, new Random()), state, worldObj, blockPos);
      tessellator.draw();
    } catch (UnsupportedOperationException ignored) {
    }
  }

  @OnlyIn(Dist.CLIENT)
  private static void renderQuads(
      final int alpha,
      final BufferBuilder renderer,
      final List<BakedQuad> quads,
      final BlockState state,
      final World worldObj,
      final BlockPos blockPos) {
    for (BakedQuad bakedquad : quads) {
      final int color =
          bakedquad.getTintIndex() == -1
              ? alpha | 0xffffff
              : getTint(alpha, bakedquad.getTintIndex(), state, worldObj, blockPos);
//      net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(
//          renderer, bakedquad, color);
    }
  }

  @OnlyIn(Dist.CLIENT)
  private static int getTint(
      final int alpha,
      final int tintIndex,
      final BlockState state,
      final World worldObj,
      final BlockPos blockPos) {
    return alpha
        | Minecraft.getInstance().getBlockColors().getColor(state, worldObj, blockPos, tintIndex);
  }

  @OnlyIn(Dist.CLIENT)
  public static void renderGhostModel(
      final ItemStack itemStack,
      final BlockState state,
      final World worldObj,
      final BlockPos blockPos) {
    IBakedModel model =
        Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(itemStack);

    renderGhostModel(model, state, worldObj, blockPos);
  }

  @OnlyIn(Dist.CLIENT)
  public static void renderGhostModel(
      final IBakedModel model,
      final BlockState state,
      final World worldObj,
      final BlockPos blockPos) {
    final int alpha = 0xaa000000;
//    GlStateManager.bindTexture(Minecraft.getInstance().getTextureMap().getGlTextureId());
    GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);

    GlStateManager.enableBlend();
    //        GlStateManager.enableTexture2D();
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    GlStateManager.colorMask(false, false, false, false);

    renderModel(model, state, worldObj, blockPos, alpha);
    GlStateManager.colorMask(true, true, true, true);
    GlStateManager.depthFunc(GL11.GL_LEQUAL);
    renderModel(model, state, worldObj, blockPos, alpha);

    GlStateManager.disableBlend();
  }
}
