package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.world.LightType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ItemInterfaceRenderer extends TileEntityRenderer<TileItemInterface> {

  public ItemInterfaceRenderer(TileEntityRendererDispatcher p_i226006_1_) {
    super(p_i226006_1_);
  }

  @Override
  public void render(@Nonnull TileItemInterface te, float v,@Nonnull  MatrixStack matrixStack,@Nonnull  IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
    ItemStack stack = te.getStoredItem();

    if (stack.isEmpty()) return;

    matrixStack.push();
    matrixStack.translate(0.5,0.32,0.5);

    matrixStack.push();
    long angle = (System.currentTimeMillis() / 10) % 360;
    matrixStack.rotate(new Quaternion(Vector3f.YN, angle, true));

    RenderSystem.enableLighting();
    Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, te.getWorld().getLight(te.getPos()) * 16,0, matrixStack, iRenderTypeBuffer);
    RenderSystem.disableLighting();
    matrixStack.pop();
    matrixStack.pop();
  }
}
