package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.block.tile.TilePlayerInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class PlayerInterfaceRenderer extends TileEntityRenderer<TilePlayerInterface> {

    @Override
    public void render(TilePlayerInterface te, double x, double y, double z, float partialTicks, int alpha) {
        GlStateManager.pushLightingAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translated(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderPlayer(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private UUID uuidCache;
    private ItemStack stackCache;

    private void renderPlayer(TilePlayerInterface te) {
        UUID uuid = te.getPlacer();

        if (uuid == null)
            return;

        EntityPlayer player = te.getWorld().getPlayerEntityByUUID(uuid);

        if (player == null) {
            if (uuid.equals(uuidCache)) {
                renderItem(stackCache);
            } else {
                uuidCache = uuid;
                NBTTagCompound tag = new NBTTagCompound();
                tag.putString("SkullOwner", uuid.toString());
                stackCache = new ItemStack(Items.PLAYER_HEAD, 1, tag);
                renderItem(stackCache);
            }
            return;
        }

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();

        GlStateManager.translated(.5, .3, .5);
        GlStateManager.scaled(.2f, .2f, .2f);
        long angle = (System.currentTimeMillis() / 10) % 360;
        GlStateManager.rotated(angle, 0, 1, 0);

        Minecraft.getInstance().getRenderManager().renderEntity(player, 0, 0, 0, 0, 1, false);

        GlStateManager.popMatrix();
    }

    private void renderItem(ItemStack stack) {
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();

        GlStateManager.translated(.5, .65, .5);
        GlStateManager.scalef(.5f, .5f, .5f);
        long angle = (System.currentTimeMillis() / 10) % 360;
        GlStateManager.rotated(angle, 0, 1, 0);

        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

        GlStateManager.popMatrix();
    }
}
