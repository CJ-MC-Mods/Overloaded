package com.cjm721.overloaded.client.render.item;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.AssistMode;
import com.cjm721.overloaded.util.BlockItemUseContextPublic;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

import net.minecraft.client.renderer.RenderType.State;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderMultiToolAssist {

  @SubscribeEvent
  public static void onMouseEvent(InputEvent.MouseScrollEvent event) {
    ClientPlayerEntity player = Minecraft.getInstance().player;

    if (event.getScrollDelta() != 0 && player != null && player.isShiftKeyDown()) {
      ItemStack stack = player.getMainHandItem();
      if (player.isShiftKeyDown() && !stack.isEmpty() && stack.getItem() == ModItems.multiTool) {
        changeHelpMode((int) Math.round(event.getScrollDelta()));
        player.displayClientMessage(
            new StringTextComponent("Assist Mode: " + getAssistMode().getName()), true);
        event.setCanceled(true);
      }
    }
  }

  private static void changeHelpMode(int dwheel) {
    AssistMode[] values = AssistMode.values();
    int mode =
        (OverloadedConfig.INSTANCE.multiToolConfig.assistMode + Integer.signum(dwheel))
            % values.length;
    if (mode < 0) mode += values.length;

    OverloadedConfig.INSTANCE.multiToolConfig.assistMode = mode;
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
    float partialTick = Minecraft.getInstance().getFrameTime();
    PlayerEntity player = Minecraft.getInstance().player;
    if (player.getMainHandItem().getItem() != ModItems.multiTool) return;

    RayTraceResult resultPick =
        player.pick(OverloadedConfig.INSTANCE.multiToolConfig.reach, partialTick, false);
    if (resultPick.getType() == RayTraceResult.Type.MISS || !(resultPick instanceof BlockRayTraceResult)) {
      return;
    }

    BlockRayTraceResult result = ((BlockRayTraceResult) resultPick);
    ItemStack stack = ModItems.multiTool.getSelectedBlockItemStack(player.getMainHandItem());

    BlockState state;
    if (stack.getItem() instanceof BlockItem) {
      state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
      state = state.getStateAtViewpoint(player.getCommandSenderWorld(), result.getBlockPos(), player.getEyePosition(partialTick));
      state =
          state
              .getBlock()
              .getStateForPlacement(
                  new BlockItemUseContextPublic(
                      player.getCommandSenderWorld(), player, Hand.MAIN_HAND, stack, result));
    } else {
      state = Blocks.COBBLESTONE.defaultBlockState();
    }

    switch (getAssistMode()) {
      case PLACE_PREVIEW:
        if (!stack.isEmpty() && state != null) renderBlockPreview(event, result, state);
        break;
      case REMOVE_PREVIEW:
        renderRemovePreview(event, result);
        break;
      case BOTH_PREVIEW:
        renderRemovePreview(event, result);
        if (!stack.isEmpty() && state != null) renderBlockPreview(event, result, state);
    }
  }

  private static void renderRemovePreview(RenderWorldLastEvent event, BlockRayTraceResult result) {
    IBakedModel bakeModel =
        Minecraft.getInstance()
            .getModelManager()
            .getModel(new ModelResourceLocation(MODID + ":remove_preview", ""));
    BlockPos toRenderAt = result.getBlockPos();

    renderBlockModel(event, toRenderAt, bakeModel, Blocks.COBBLESTONE.defaultBlockState());
  }

  private static void renderBlockPreview(RenderWorldLastEvent event, BlockRayTraceResult result, @Nonnull BlockState state) {
    IBakedModel model =
        Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
    BlockPos toRenderAt = result.getBlockPos().offset(result.getDirection().getNormal());

    renderBlockModel(event, toRenderAt, model, state);
  }

  private static void renderBlockModel(RenderWorldLastEvent event,
                                       BlockPos toRenderAt, @Nonnull IBakedModel model, @Nonnull BlockState state) {
    ActiveRenderInfo camera = Minecraft.getInstance().gameRenderer.getMainCamera();

    final double x = camera.getPosition().x();
    final double y = camera.getPosition().y();
    final double z = camera.getPosition().z();

    event.getMatrixStack().pushPose();
    event.getMatrixStack().translate(toRenderAt.getX() - x, toRenderAt.getY() - y, toRenderAt.getZ() - z);
    IVertexBuilder buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(GhostRenderType.getInstance());
    Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateBlock(
        Minecraft.getInstance().level,
        model,
        state,
        toRenderAt,
        event.getMatrixStack(),
        buffer,
        false,
        Minecraft.getInstance().level.random,
        0, 0
    );
    event.getMatrixStack().popPose();

    Minecraft.getInstance().renderBuffers().bufferSource().endBatch(GhostRenderType.getInstance());
  }

  public static class GhostRenderType extends RenderType {

    public GhostRenderType(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
      super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, p_i225992_7_, p_i225992_8_);
    }

    public static RenderType getInstance() {
      return create("ghost_model",
          DefaultVertexFormats.BLOCK,
          7,
          262144,
          true,
          true,
          State.builder().setShadeModelState(SMOOTH_SHADE).setLightmapState(NO_LIGHTMAP).setTextureState(BLOCK_SHEET_MIPPED).setTransparencyState(new RenderState.TransparencyState("ghost_transparency", () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            RenderSystem.colorMask(true, true, true, true);
          }, RenderSystem::disableBlend)).createCompositeState(true));
    }
  }
}
