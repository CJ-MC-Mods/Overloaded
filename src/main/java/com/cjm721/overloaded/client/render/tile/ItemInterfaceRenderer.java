package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.common.block.tile.TileItemInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class ItemInterfaceRenderer extends TileEntitySpecialRenderer<TileItemInterface> {

    @Override
    public void renderTileEntityAt(TileItemInterface te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x,y,z);
        GlStateManager.disableRescaleNormal();

        renderItem(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
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
        long angle = (System.currentTimeMillis() / 10) % 360;
        GlStateManager.rotate(angle, 0, 1, 0);

        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

        GlStateManager.popMatrix();
    }
}
