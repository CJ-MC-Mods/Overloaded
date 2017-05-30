package com.cjm721.overloaded.client.render.tile;


import com.cjm721.overloaded.common.block.tile.TileFusionCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
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
                model = ModelLoaderRegistry.getModel(new ResourceLocation(MODID, "block/sun.obj"));
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

        renderCore(te,0,1, 11f, new ResourceLocation("overloaded", "textures/blocks/test.png"));

//        renderCore(te,90,-1, 1, new ResourceLocation("overloaded", "textures/blocks/sun/light_yellow.png"));
//        renderCore(te,0,1,0.98f, new ResourceLocation("overloaded", "textures/blocks/sun/yellow.png"));
//        renderCore(te,0,-1, 0.99f, new ResourceLocation("overloaded", "textures/blocks/sun/red.png"));
//        renderCore(te,90,1, 0.97f, new ResourceLocation("overloaded", "textures/blocks/sun/orange.png"));

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private boolean growing = true;
    private float scale = 1;

    private void renderCore(TileFusionCore te, long offset, float direction, float scale, ResourceLocation texture) {
        GlStateManager.pushAttrib();

        GlStateManager.pushMatrix();

        GlStateManager.translate(0.5, 0.5, 0.5);
        long angle = ((System.currentTimeMillis() / 50) + offset) % 360;
        GlStateManager.rotate(angle, 0, direction, 0);

        GlStateManager.scale(scale, scale, scale);

        GlStateManager.disableLighting();
        this.bindTexture(texture);
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }


        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_DST_ALPHA);
        GlStateManager.enableBlend();
        //GlStateManager.disableDepth();

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

        //GlStateManager.enableDepth();
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}

