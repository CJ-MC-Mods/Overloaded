package com.cjm721.overloaded.client.render.item;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.AssistMode;
import com.cjm721.overloaded.util.PlayerInteractionUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL14;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderMultiToolAssist {

  @SubscribeEvent
  public static void onMouseEvent(InputEvent.MouseInputEvent event) {
    ClientPlayerEntity player = Minecraft.getInstance().player;
    //            if (event.getDwheel() != 0 && player != null && player.isSneaking()) {
    //                ItemStack stack = player.getHeldItemMainhand();
    //                if (player.isSneaking() && !stack.isEmpty() && stack.getItem() ==
    //     ModItems.multiTool) {
    //                    changeHelpMode(event.getDwheel());
    //                    player.sendStatusMessage(new StringTextComponent("Assist Mode: " +
    //     getAssistMode().getName()), true);
    //                    event.setCanceled(true);
    //                }
    //            }
  }

  @SubscribeEvent
  public static void onKeyEvent(InputEvent.KeyInputEvent event) {}

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
  public static void renderWorldLastEvent(RenderWorldLastEvent event) {
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
        if (!stack.isEmpty()) renderBlockPreview(result, state);
        break;
      case REMOVE_PREVIEW:
        renderRemovePreview(event, player, result);
        break;
      case BOTH_PREVIEW:
        if (!stack.isEmpty()) renderBlockPreview(result, state);
        renderRemovePreview(event, player, result);
        break;
    }
  }

  private static void renderRemovePreview(
      RenderWorldLastEvent event, PlayerEntity player, BlockRayTraceResult result) {
    //    try {
    //      IModel model =
    //          ModelLoaderRegistry.getModel(new ResourceLocation(MODID, "block/remove_preview"));
    //      IBakedModel bakeModel =
    //          model.bake(
    //              new ModelBakery(
    //                  Minecraft.getInstance().getResourceManager(),
    //                  Minecraft.getInstance().getTextureMap(),
    //                  Minecraft.getInstance().getProfiler()),
    //              ModelLoader.defaultTextureGetter(),
    //              new ISprite() {},
    //              DefaultVertexFormats.ITEM);
    //
    //      BlockPos toRenderAt = result.getPos();
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
    //    } catch (Exception e) {
    //      e.printStackTrace();
    //    }
  }

  private static void renderBlockPreview(
      BlockRayTraceResult result, BlockState state) {
    IBakedModel model =
        Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(state);
    BlockPos toRenderAt = result.getPos().add(result.getFace().getDirectionVec());

    ActiveRenderInfo camera = Minecraft.getInstance().getRenderManager().info;
    if(camera == null) {
      return;
    }

    final double x = camera.getProjectedView().getX();
    final double y = camera.getProjectedView().getY();
    final double z = camera.getProjectedView().getZ() - 1;

    GlStateManager.pushMatrix();
    GlStateManager.translated(toRenderAt.getX() - x, toRenderAt.getY() - y, toRenderAt.getZ() - z);
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.CONSTANT_COLOR);
    GL14.glBlendColor(1f,1f,1f,0.5f);

    GlStateManager.pushMatrix();
    GlStateManager.colorMask(false,false,false,false);
    Minecraft.getInstance()
        .getBlockRendererDispatcher()
        .getBlockModelRenderer()
        .renderModelBrightness(model, state, 1.0F, false);
    GlStateManager.popMatrix();

    GlStateManager.pushMatrix();
    GlStateManager.colorMask(true,true,true,true);
    Minecraft.getInstance()
        .getBlockRendererDispatcher()
        .getBlockModelRenderer()
        .renderModelBrightness(model, state, 1.0F, false);
    GlStateManager.popMatrix();

    GlStateManager.disableBlend();
    GlStateManager.popMatrix();
  }
}
