package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.tile.functional.TilePlayerInterface;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class PlayerInterfaceRenderer extends TileEntityRenderer<TilePlayerInterface> {

  public PlayerInterfaceRenderer(TileEntityRendererDispatcher p_i226006_1_) {
    super(p_i226006_1_);
  }

  @Override
  public void render(@Nonnull TilePlayerInterface te, float v,@Nonnull  MatrixStack matrixStack, @Nonnull IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
    GlStateManager.pushLightingAttributes();
    GlStateManager.pushMatrix();

    GlStateManager.translated(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
    GlStateManager.disableRescaleNormal();

    renderPlayer(te);

    GlStateManager.popMatrix();
    GlStateManager.popAttributes();
  }

  private UUID uuidCache;
  private ItemStack stackCache;

  private void renderPlayer(TilePlayerInterface te) {
    UUID uuid = te.getPlacer();

    if (uuid == null) return;

    PlayerEntity player = te.getWorld().getPlayerByUuid(uuid);

    if (player == null) {
      if (uuid.equals(uuidCache)) {
        renderItem(stackCache);
      } else {
        uuidCache = uuid;
        CompoundNBT tag = new CompoundNBT();
        tag.putString("SkullOwner", uuid.toString());
        stackCache = new ItemStack(Items.PLAYER_HEAD, 1, tag);
        renderItem(stackCache);
      }
      return;
    }

    RenderHelper.enableStandardItemLighting();
    GlStateManager.enableLighting();
    GlStateManager.pushMatrix();

    GlStateManager.translated(.5, .3, .5);
    GlStateManager.scaled(.2f, .2f, .2f);
    long angle = (System.currentTimeMillis() / 10) % 360;
    GlStateManager.rotatef(angle, 0, 1, 0);

    Minecraft.getInstance().getRenderManager().setRenderShadow(false);
//    Minecraft.getInstance().getRenderManager().renderEntityStatic( player, 0, 0, 0, 0, 1, false);

    GlStateManager.popMatrix();
  }

  private void renderItem(ItemStack stack) {
    RenderHelper.enableStandardItemLighting();
    GlStateManager.enableLighting();
    GlStateManager.pushMatrix();

    GlStateManager.translated(.5, .65, .5);
    GlStateManager.scalef(.5f, .5f, .5f);
    long angle = (System.currentTimeMillis() / 10) % 360;
    GlStateManager.rotatef(angle, 0, 1, 0);

//    Minecraft.getInstance()
//        .getItemRenderer()
//        .renderItem(stack, ItemCameraTransforms.TransformType.NONE);

    GlStateManager.popMatrix();
  }
}
