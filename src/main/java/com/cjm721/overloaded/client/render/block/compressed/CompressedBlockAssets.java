package com.cjm721.overloaded.client.render.block.compressed;

import com.cjm721.overloaded.client.resource.CompressedResourcePack;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CompressedBlockAssets {

    private static List<CompressedResourceLocation> toCreateTextures = new ArrayList<>();

    public static void addToTextureQueue(CompressedResourceLocation location) {
        toCreateTextures.add(location);

        generateTexture(location);
    }

    private static String getBlockState(@Nonnull ResourceLocation location) {
        return String.format("{ \"forge_marker\": 1, \"variants\": { \"normal\": { \"model\": \"cube_all\", \"textures\": { \"all\": \"%1$s\" } }, \"inventory\": { \"model\": \"cube_all\", \"textures\": { \"all\": \"%1$s\" } } } }", getBlocksPath(location));
    }

    private static ResourceLocation getBlocksPath(@Nonnull ResourceLocation base) {
        return new ResourceLocation(base.getResourceDomain(), "blocks/" + base.getResourcePath());
    }

    private static ResourceLocation getTexturePath(@Nonnull ResourceLocation base) {
        return new ResourceLocation(base.getResourceDomain(), "textures/blocks/" + base.getResourcePath() + ".png");
    }

    public static ResourceLocation getJsonPath(@Nonnull ResourceLocation base) {
        return new ResourceLocation(base.getResourceDomain(), "blockstates/" + base.getResourcePath() + ".json");
    }

    private static InputStream getTextureInputStream(ResourceLocation baseLocation) throws IOException {
        return Minecraft.getMinecraft().getResourceManager().getResource(getTexturePath(baseLocation)).getInputStream();
    }

    @SubscribeEvent
    public void texturePre(TextureStitchEvent.Pre event) {
        for(CompressedResourceLocation locations: toCreateTextures) {
            if (!generateTexture(locations)) return;

            event.getMap().registerSprite(getBlocksPath(locations.compressed));
        }
    }

    private static boolean generateTexture(CompressedResourceLocation locations) {
        String state = getBlockState(locations.compressed);
        CompressedResourcePack.INSTANCE.addBlockState(getJsonPath(locations.compressed), state);

        BufferedImage image;
        try {
            image = TextureUtil.readBufferedImage(getTextureInputStream(locations.base));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        int scale = 1 + locations.compressionAmount;

        WritableRaster raster = image.getColorModel().createCompatibleWritableRaster(image.getWidth()*scale,image.getHeight()*scale);
        int[] pixels = image.getData().getPixels(0,0,image.getWidth(), image.getHeight(), (int[])null);

        for(int x = 0; x < scale; x++) {
            for(int y = 0; y < scale; y++) {
                raster.setPixels(x*image.getWidth(),y*image.getHeight(),image.getWidth(),image.getHeight(),pixels);
            }
        }
        BufferedImage compressedImage = new BufferedImage(image.getColorModel(), raster, true, null);

        if(compressedImage.getWidth() > OverloadedConfig.compressedConfig.maxTextureWidth) {
            compressedImage = scaleToWidth(compressedImage, OverloadedConfig.compressedConfig.maxTextureWidth);
        }

        ResourceLocation rl = getTexturePath(locations.compressed);
        CompressedResourcePack.INSTANCE.addImage(rl, compressedImage);

        return true;
    }

    public static class CompressedResourceLocation {
        public ResourceLocation base;
        public ResourceLocation compressed;
        public int compressionAmount;

        public CompressedResourceLocation(@Nonnull ResourceLocation base, @Nonnull ResourceLocation compressed, int compressionAmount) {
            this.base = base;
            this.compressed = compressed;
            this.compressionAmount = compressionAmount;
        }
    }

    private static BufferedImage scaleToWidth(@Nonnull BufferedImage original, int width) {
        double scale = original.getWidth() / (double) width;

        AffineTransform at = new AffineTransform();
        at.scale(1/scale,1/scale);

        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        return scaleOp.filter(original,null);
    }
}
