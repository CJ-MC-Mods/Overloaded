package com.cjm721.overloaded.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;

public class RenderGlobalSpectator extends WorldRenderer {

  public RenderGlobalSpectator(Minecraft mcIn) {
    super(mcIn, mcIn.renderBuffers());
  }

  //  @Override
  //  public void setupTerrain(
  //      Entity viewEntity,
  //      double partialTicks,
  //      @Nonnull ICamera camera,
  //      int frameCount,
  //      boolean playerSpectator) {
  //    PlayerEntitySP player = Minecraft.getMinecraft().player;
  //    super.setupTerrain(
  //        viewEntity, partialTicks, camera, frameCount, player.noClip || player.isSpectator());
  //  }
}
