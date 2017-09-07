package com.cjm721.overloaded.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public class RenderGlobalSpectator extends RenderGlobal {

    public RenderGlobalSpectator(Minecraft mcIn) {
        super(mcIn);
    }

    @Override
    public void setupTerrain(Entity viewEntity, double partialTicks, @Nonnull ICamera camera, int frameCount, boolean playerSpectator) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        super.setupTerrain(viewEntity, partialTicks, camera, frameCount, player.noClip || player.isSpectator());
    }
}
