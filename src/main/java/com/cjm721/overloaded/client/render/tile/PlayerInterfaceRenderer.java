package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.common.block.tile.TileItemInterface;
import com.cjm721.overloaded.common.block.tile.TilePlayerInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.UUID;

/**
 * Created by CJ on 4/23/2017.
 */
public class PlayerInterfaceRenderer extends TileEntitySpecialRenderer<TilePlayerInterface> {

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
    public void renderTileEntityAt(TilePlayerInterface te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x,y,z);
        GlStateManager.disableRescaleNormal();

        renderPlayer(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderPlayer(TilePlayerInterface te) {
        UUID uuid = te.getPlacer();

        if(uuid == null)
            return;

        EntityPlayer player = te.getWorld().getPlayerEntityByUUID(uuid);

        if(player == null)
            return;

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();

        GlStateManager.translate(.5, .2, .5);
        GlStateManager.scale(.2f,.2f,.2f);
        long angle = (System.currentTimeMillis() / 10) % 360;
        GlStateManager.rotate(angle, 0, 1, 0);

        Minecraft.getMinecraft().getRenderManager().doRenderEntity(player, 0, 0, 0, 0, 1, false);

        GlStateManager.popMatrix();
    }
}
