package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.tile.functional.TilePlayerInterface;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class PlayerInterfaceRenderer extends TileEntityRenderer<TilePlayerInterface> {

  private UUID uuidCache;
  private ItemStack stackCache;

  public PlayerInterfaceRenderer(TileEntityRendererDispatcher p_i226006_1_) {
    super(p_i226006_1_);
  }

  @Override
  public void render(@Nonnull TilePlayerInterface te, float v, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
    UUID uuid = te.getPlacer();

    if (uuid == null) return;

    PlayerEntity player = te.getLevel().getPlayerByUUID(uuid);

    if (player == null) {
      if (!uuid.equals(uuidCache)) {
        uuidCache = uuid;
        CompoundNBT tag = new CompoundNBT();
        tag.putString("SkullOwner", uuid.toString());
        stackCache = new ItemStack(Items.PLAYER_HEAD, 1, tag);
      }
      renderItem(te, stackCache, matrixStack, iRenderTypeBuffer);
      return;
    }
    renderPlayer(te, player, matrixStack, iRenderTypeBuffer, combinedLightIn);
  }

  private void renderPlayer(TilePlayerInterface te, PlayerEntity player, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int lightLevel) {
    matrixStack.pushPose();

    matrixStack.translate(0.5, 0.32, 0.5);
    matrixStack.scale(.2f, .2f, .2f);

    matrixStack.pushPose();
    long angle = (System.currentTimeMillis() / 10) % 360;
    matrixStack.mulPose(new Quaternion(Vector3f.YN, angle, true));
    Minecraft.getInstance().getEntityRenderDispatcher().setRenderShadow(false);
    Minecraft.getInstance().getEntityRenderDispatcher().render(player, 0, 0, 0, 0, Minecraft.getInstance().getFrameTime(),
        matrixStack,
        iRenderTypeBuffer,
            lightLevel);
      Minecraft.getInstance().getEntityRenderDispatcher().setRenderShadow(false);
    matrixStack.popPose();

    matrixStack.popPose();
  }

  private void renderItem(TilePlayerInterface te, ItemStack stack, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer) {
    matrixStack.pushPose();
    matrixStack.translate(0.5, 0.32, 0.5);

    matrixStack.pushPose();
    long angle = (System.currentTimeMillis() / 10) % 360;
    matrixStack.mulPose(new Quaternion(Vector3f.YN, angle, true));

    RenderSystem.enableLighting();
    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemCameraTransforms.TransformType.GROUND, te.getLevel().getBrightness(LightType.BLOCK, te.getBlockPos()) * 16, 0, matrixStack, iRenderTypeBuffer);
    RenderSystem.disableLighting();
    matrixStack.popPose();

    matrixStack.popPose();
  }
}
