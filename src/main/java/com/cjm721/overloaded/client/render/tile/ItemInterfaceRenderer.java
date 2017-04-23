package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.common.block.tile.TileItemInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;

public class ItemInterfaceRenderer extends TileEntitySpecialRenderer<TileItemInterface> {

    private IModel model;
    private IBakedModel bakedModel;


    public IBakedModel getBakedModel() {
        if(bakedModel == null){
            try {
                model = ModelLoaderRegistry.getModel(new ResourceLocation("minecraft", "block/glass"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM,
                    location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
        }
        return bakedModel;
    }

    @Override
    public void renderTileEntityAt(TileItemInterface te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x,y,z);
        GlStateManager.disableRescaleNormal();

        renderBlock(te);

        renderItem(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderBlock(TileItemInterface te) {
//        GlStateManager.pushMatrix();
//
//
//
//        GlStateManager.popMatrix();
    }

    private void renderItem(TileItemInterface te) {
        ItemStack stack = te.getStoredItem();

        if(stack.isEmpty())
            return;

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();

        GlStateManager.translate(.5, .5, .5);
        GlStateManager.scale(.5f,.5f,.5f);

        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

        GlStateManager.popMatrix();
    }
}
