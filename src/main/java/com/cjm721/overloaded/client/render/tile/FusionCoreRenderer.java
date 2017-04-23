package com.cjm721.overloaded.client.render.tile;


import com.cjm721.overloaded.common.block.tile.TileFusionCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class FusionCoreRenderer extends TileEntitySpecialRenderer<TileFusionCore> {

    private IModel model;
    private IBakedModel bakedModel;

    @Nonnull
    private IBakedModel getBakedModel() {
        // Since we cannot bake in preInit() we do lazy baking of the model as soon as we need it
        // for rendering
        if (bakedModel == null) {
            try {
                model = ModelLoaderRegistry.getModel(new ResourceLocation(MODID, "block/sphere.obj"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM,
                    location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
        }
        return bakedModel;
    }

    @Override
    public void renderTileEntityAt(TileFusionCore te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x,y,z);
        GlStateManager.disableRescaleNormal();

        renderCore(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private boolean growing = true;
    private float scale = 1;

    private void renderCore(TileFusionCore te) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(.5, 0, .5);
        long angle = (System.currentTimeMillis() / 10) % 360;
        GlStateManager.rotate(angle, 0, 1, 0);

        if(growing) {
            scale += 0.0001;

            if(scale >= 2)
                growing = false;
        } else {
            scale -= 0.0001;

            if(scale <= 0.5)
                growing = true;
        }

        GlStateManager.scale(scale, scale, scale);

        RenderHelper.disableStandardItemLighting();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        World world = te.getWorld();
        // Translate back to local view coordinates so that we can do the acual rendering here
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
                world,
                getBakedModel(),
                world.getBlockState(te.getPos()),
                te.getPos(),
                Tessellator.getInstance().getBuffer(),
                false);
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
//        RenderHelper.enableStandardItemLighting();
//        GlStateManager.enableLighting();
//        GlStateManager.pushMatrix();
//
//        GlStateManager.translate(.5, .3, .5);
//        GlStateManager.scale(.2f,.2f,.2f);
//        long angle = (System.currentTimeMillis() / 10) % 360;
//        GlStateManager.rotate(angle, 0, 1, 0);
//
//        Minecraft.getMinecraft().getRenderManager().doRenderEntity(player, 0, 0, 0, 0, 1, false);
//
//        GlStateManager.popMatrix();
    }
}
