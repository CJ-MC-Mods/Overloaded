package com.cjm721.overloaded.client.render.dynamic.general;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ResizeableTextureGenerator {

    @SubscribeEvent
    public void texturePre(TextureStitchEvent.Pre event) {
    }

    public static class ResizableTexture {
        public final ResourceLocation originalTexture;
        public final ResourceLocation generatedName;
        public final int resizeToWidth;

        public ResizableTexture(ResourceLocation originalTexture, ResourceLocation generatedName, int resizeToWidth) {
            this.originalTexture = originalTexture;
            this.generatedName = generatedName;
            this.resizeToWidth = resizeToWidth;
        }
    }
}
