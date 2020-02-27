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
  public void render(@Nonnull TilePlayerInterface te, float v, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
    UUID uuid = te.getPlacer();

    if (uuid == null) return;

    PlayerEntity player = te.getWorld().getPlayerByUuid(uuid);

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

    renderPlayer(te, player, matrixStack, iRenderTypeBuffer);
  }

  private void renderPlayer(TilePlayerInterface te, PlayerEntity player, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer) {
    matrixStack.push();

    matrixStack.translate(0.5, 0.32, 0.5);
    matrixStack.scale(.2f, .2f, .2f);

    matrixStack.push();
    long angle = (System.currentTimeMillis() / 10) % 360;
    matrixStack.rotate(new Quaternion(Vector3f.YN, angle, true));
    Minecraft.getInstance().getRenderManager().renderEntityStatic(player, 0, 0, 0, 0, Minecraft.getInstance().getRenderPartialTicks(),
        matrixStack,
        iRenderTypeBuffer,
        te.getWorld().getLightFor(LightType.BLOCK, te.getPos()) * 16);
    matrixStack.pop();

    matrixStack.pop();
  }

  private void renderItem(TilePlayerInterface te, ItemStack stack, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer) {
    matrixStack.push();
    matrixStack.translate(0.5, 0.32, 0.5);

    matrixStack.push();
    long angle = (System.currentTimeMillis() / 10) % 360;
    matrixStack.rotate(new Quaternion(Vector3f.YN, angle, true));

    RenderSystem.enableLighting();
    Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, te.getWorld().getLightFor(LightType.BLOCK, te.getPos()) * 16, 0, matrixStack, iRenderTypeBuffer);
    RenderSystem.disableLighting();
    matrixStack.pop();

    matrixStack.pop();
  }
}
