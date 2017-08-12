package com.cjm721.overloaded.client.render.dynamic.general;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.resource.BlockResourcePack;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.cjm721.overloaded.client.render.dynamic.ImageUtil.getTextureInputStream;

@SideOnly(Side.CLIENT)
public class ResizeableTextureGenerator {

    private static List<ResizableTexture> toCreateTextures = new ArrayList<>();

    public static void addToTextureQueue(ResizableTexture location) {
        toCreateTextures.add(location);
    }

    @SubscribeEvent
    public void texturePre(@Nonnull TextureStitchEvent.Pre event) {
        for(ResizableTexture resizableTexture: toCreateTextures) {
            BufferedImage image = null;
            try {
                image = TextureUtil.readBufferedImage(getTextureInputStream(resizableTexture.originalTexture));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(image == null) continue;

            image = ImageUtil.scaleDownToWidth(image, resizableTexture.resizeToWidth);

            BlockResourcePack.INSTANCE.addImage(resizableTexture.generatedName, image);

            event.getMap().registerSprite(cleanForSprite(resizableTexture.generatedName));
        }
    }

    @Nonnull
    private ResourceLocation cleanForSprite(@Nonnull ResourceLocation location) {
        String path = location.getResourcePath();

        if(path.startsWith("textures/")) {
            return new ResourceLocation(location.getResourceDomain(), path.substring(9).replace(".png",""));
        }
        return location;
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
