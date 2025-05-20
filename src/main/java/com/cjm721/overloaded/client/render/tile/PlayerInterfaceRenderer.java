package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.cjm721.overloaded.tile.functional.TilePlayerInterface;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

import static com.cjm721.overloaded.client.render.tile.ItemInterfaceRenderer.renderItem;

@OnlyIn(Dist.CLIENT)
public class PlayerInterfaceRenderer implements BlockEntityRenderer<TilePlayerInterface> {

  private UUID uuidCache;
  private ItemStack stackCache;

  public PlayerInterfaceRenderer(BlockEntityRenderDispatcher p_i226006_1_) {}

  @Override
  public void render(
      @Nonnull TilePlayerInterface te,
      float v,
      @Nonnull PoseStack matrixStack,
      @Nonnull MultiBufferSource bufferSource,
      int packedLight,
      int packedOverlay) {
    UUID uuid = te.getPlacer();

    if (uuid == null) return;

    Player player = te.getLevel().getPlayerByUUID(uuid);

    if (player == null) {
      if (!uuid.equals(uuidCache)) {
        uuidCache = uuid;
        stackCache.set(
            DataComponents.PROFILE,
            new ResolvableProfile(Optional.empty(), Optional.of(uuid), new PropertyMap()));
      }
      renderItem(stackCache, te, matrixStack, bufferSource, packedLight, packedOverlay);
      return;
    }
    renderPlayer(te, player, matrixStack, bufferSource, packedLight);
  }

  public static class Provider implements BlockEntityRendererProvider<TilePlayerInterface> {
    @Override
    public BlockEntityRenderer create(BlockEntityRendererProvider.Context context) {
      return new PlayerInterfaceRenderer(context.getBlockEntityRenderDispatcher());
    }
  }

  private void renderPlayer(
      TilePlayerInterface te,
      Player player,
      PoseStack matrixStack,
      MultiBufferSource bufferSource,
      int packedLight) {
    matrixStack.pushPose();

    matrixStack.translate(0.5, 0.32, 0.5);
    matrixStack.scale(.2f, .2f, .2f);

    matrixStack.pushPose();
    //      long angle = (System.currentTimeMillis() / 10) % 360;
    // matrixStack.mulPose(new Quaternion(Vector3f.YN, angle, true));
    Minecraft.getInstance().getEntityRenderDispatcher().setRenderShadow(false);
    Minecraft.getInstance()
        .getEntityRenderDispatcher()
        .render(
            player,
            0,
            0,
            0,
            0,
            Minecraft.getInstance().getTimer().getGameTimeDeltaTicks(),
            matrixStack,
            bufferSource,
            packedLight);
    matrixStack.popPose();

    matrixStack.popPose();
  }
}
