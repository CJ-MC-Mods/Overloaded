package com.cjm721.overloaded.client.render.item;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.AssistMode;
import com.cjm721.overloaded.util.PlayerInteractionUtil;
import com.cjm721.overloaded.util.RenderUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

@OnlyIn(Dist.CLIENT)
public class RenderMultiToolAssist {

    @SubscribeEvent
    public void onMouseEvent(MouseEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (event.getDwheel() != 0 && player != null && player.isSneaking()) {
            ItemStack stack = player.getHeldItemMainhand();
            if (player.isSneaking() && !stack.isEmpty() && stack.getItem() == ModItems.multiTool) {
                changeHelpMode(event.getDwheel());
                player.sendStatusMessage(new TextComponentString("Assist Mode: " + getAssistMode().getName()), true);
                event.setCanceled(true);
            }
        }
    }

    private static void changeHelpMode(int dwheel) {
        AssistMode[] values = AssistMode.values();
        int mode = (Overloaded.cachedConfig.multiToolConfig.assistMode + Integer.signum(dwheel)) % values.length;
        if (mode < 0)
            mode += values.length;

        Overloaded.cachedConfig.multiToolConfig.assistMode = mode;
        ConfigManager.sync(MODID, Config.Type.INSTANCE);
    }

    @Nonnull
    public static AssistMode getAssistMode() {
        AssistMode[] values = AssistMode.values();
        int mode = Overloaded.cachedConfig.multiToolConfig.assistMode;

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
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player.getHeldItemMainhand().getItem() != ModItems.multiTool)
            return;

        RayTraceResult result = PlayerInteractionUtil.getBlockPlayerLookingAtClient(player, event.getPartialTicks());
        if (result == null) return;

        ItemStack stack = ModItems.multiTool.getSelectedBlockItemStack(player.getHeldItemMainhand());

        IBlockState state;
        if (stack.getItem() instanceof ItemBlock) {
            state = ((ItemBlock) stack.getItem()).getBlock().getDefaultState();
        } else {
            state = Blocks.COBBLESTONE.getDefaultState();
        }

        switch (getAssistMode()) {
            case PLACE_PREVIEW:
                if (!stack.isEmpty())
                    renderBlockPreview(event, player, stack, result, state);
                break;
            case REMOVE_PREVIEW:
                renderRemovePreview(event, player, result);
                break;
            case BOTH_PREVIEW:
                if (!stack.isEmpty())
                    renderBlockPreview(event, player, stack, result, state);
                renderRemovePreview(event, player, result);
                break;
        }

    }

    @OnlyIn(Dist.CLIENT)
    private void renderRemovePreview(RenderWorldLastEvent event, EntityPlayer player, RayTraceResult result) {
        try {
            IModel model = ModelLoaderRegistry.getModel(new ResourceLocation(MODID, "block/remove_preview"));
            IBakedModel bakeModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));

            BlockPos toRenderAt = result.getBlockPos();

            final float partialTicks = event.getPartialTicks();
            final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
            final double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
            final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

            GlStateManager.pushMatrix();
            GlStateManager.translate(toRenderAt.getX() - x, toRenderAt.getY() - y, toRenderAt.getZ() - z);
            RenderUtil.renderGhostModel(bakeModel, Blocks.COBBLESTONE.getDefaultState(), player.getEntityWorld(), toRenderAt);
            GlStateManager.popMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void renderBlockPreview(RenderWorldLastEvent event, EntityPlayer player, ItemStack stack, RayTraceResult result, IBlockState state) {
        BlockPos toRenderAt = result.getBlockPos().add(result.sideHit.getDirectionVec());

        final float partialTicks = event.getPartialTicks();
        final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        final double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        GlStateManager.pushMatrix();
        GlStateManager.translate(toRenderAt.getX() - x, toRenderAt.getY() - y, toRenderAt.getZ() - z);
        RenderUtil.renderGhostModel(stack, state, player.getEntityWorld(), toRenderAt);
        GlStateManager.popMatrix();
    }
}

