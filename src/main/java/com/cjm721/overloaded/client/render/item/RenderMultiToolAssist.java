package com.cjm721.overloaded.client.render.item;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.AssistMode;
import com.cjm721.overloaded.util.PlayerInteractionUtil;
import com.cjm721.overloaded.util.RenderUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

@OnlyIn(Dist.CLIENT)
public class RenderMultiToolAssist {

  @SubscribeEvent
  public void onMouseEvent(InputEvent.MouseInputEvent event) {
    ClientPlayerEntity player = Minecraft.getInstance().player;
    //        if (event.getDwheel() != 0 && player != null && player.isSneaking()) {
    //            ItemStack stack = player.getHeldItemMainhand();
    //            if (player.isSneaking() && !stack.isEmpty() && stack.getItem() ==
    // ModItems.multiTool) {
    //                changeHelpMode(event.getDwheel());
    //                player.sendStatusMessage(new StringTextComponent("Assist Mode: " +
    // getAssistMode().getName()), true);
    //                event.setCanceled(true);
    //            }
    //        }
  }

  private static void changeHelpMode(int dwheel) {
    AssistMode[] values = AssistMode.values();
    int mode =
        (OverloadedConfig.INSTANCE.multiToolConfig.assistMode + Integer.signum(dwheel))
            % values.length;
    if (mode < 0) mode += values.length;

    OverloadedConfig.INSTANCE.multiToolConfig.assistMode = mode;
    //        ConfigManager.sync(MODID, Config.Type.INSTANCE);
  }

  @Nonnull
  public static AssistMode getAssistMode() {
    AssistMode[] values = AssistMode.values();
    int mode = OverloadedConfig.INSTANCE.multiToolConfig.assistMode;

    for (AssistMode assistMode : values) {
      if (assistMode.getMode() == mode) {
        return assistMode;
      }
    }
    // Invalid Config Entry so causing an update;
    changeHelpMode(0);
    return AssistMode.NONE;
  }

  @SubscribeEvent
  public void renderWorldLastEvent(RenderWorldLastEvent event) {
    PlayerEntity player = Minecraft.getInstance().player;
    if (player.getHeldItemMainhand().getItem() != ModItems.multiTool) return;

    BlockRayTraceResult result =
        PlayerInteractionUtil.getBlockPlayerLookingAtClient(player, event.getPartialTicks());
    if (result == null) return;

    ItemStack stack = ModItems.multiTool.getSelectedBlockItemStack(player.getHeldItemMainhand());

    BlockState state;
    if (stack.getItem() instanceof BlockItem) {
      state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
    } else {
      state = Blocks.COBBLESTONE.getDefaultState();
    }

    switch (getAssistMode()) {
      case PLACE_PREVIEW:
        if (!stack.isEmpty()) renderBlockPreview(event, player, stack, result, state);
        break;
      case REMOVE_PREVIEW:
        renderRemovePreview(event, player, result);
        break;
      case BOTH_PREVIEW:
        if (!stack.isEmpty()) renderBlockPreview(event, player, stack, result, state);
        renderRemovePreview(event, player, result);
        break;
    }
  }

  @OnlyIn(Dist.CLIENT)
  private void renderRemovePreview(
      RenderWorldLastEvent event, PlayerEntity player, RayTraceResult result) {
    try {
      IModel model =
          ModelLoaderRegistry.getModel(new ResourceLocation(MODID, "block/remove_preview"));
      //      IBakedModel bakeModel =
      //          model.bake(
      //              TRSRTransformation.identity(),
      //              location ->
      //
      // Minecraft.getInstance().getTextureMap().getAtlasSprite(location.toString()),
      //              null,
      //              DefaultVertexFormats.ITEM);
      //
      //      BlockPos toRenderAt = result.getBlockPos();
      //
      //      final float partialTicks = event.getPartialTicks();
      //      final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) *
      // partialTicks;
      //      final double y = player.lastTickPosY + (player.posY - player.lastTickPosY) *
      // partialTicks;
      //      final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) *
      // partialTicks;
      //
      //      GlStateManager.pushMatrix();
      //      GlStateManager.translated(
      //          toRenderAt.getX() - x, toRenderAt.getY() - y, toRenderAt.getZ() - z);
      //      RenderUtil.renderGhostModel(
      //          bakeModel, Blocks.COBBLESTONE.getDefaultState(), player.getEntityWorld(),
      // toRenderAt);
      //      GlStateManager.popMatrix();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @OnlyIn(Dist.CLIENT)
  private void renderBlockPreview(
      RenderWorldLastEvent event,
      PlayerEntity player,
      ItemStack stack,
      BlockRayTraceResult result,
      BlockState state) {
    BlockPos toRenderAt = result.getPos().add(result.getFace().getDirectionVec());

    final float partialTicks = event.getPartialTicks();
    final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
    final double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
    final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

    GlStateManager.pushMatrix();
    GlStateManager.translated(toRenderAt.getX() - x, toRenderAt.getY() - y, toRenderAt.getZ() - z);
    RenderUtil.renderGhostModel(stack, state, player.getEntityWorld(), toRenderAt);
    GlStateManager.popMatrix();
  }
}
