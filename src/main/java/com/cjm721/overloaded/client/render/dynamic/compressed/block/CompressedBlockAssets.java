package com.cjm721.overloaded.client.render.dynamic.compressed.block;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.resource.BlockResourcePack;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CompressedBlockAssets {

    private static final List<CompressedResourceLocation> toCreateTextures = new ArrayList<>();

    public static void addToTextureQueue(CompressedResourceLocation location) {
        toCreateTextures.add(location);

        generateTexture(location);
    }

    private static String getBlockState(@Nonnull ResourceLocation location) {
        return String.format(
                "{ " +
                        "\"forge_marker\": 1, " +
                        "\"defaults\": { " +
                        "\"model\": \"cube_all\", " +
                        "\"textures\": { " +
                        "\"all\": \"%1$s\" " +
                        "} " +
                        "}," +
                        "\"variants\": { " +
                        "\"normal\": [{ }], " +
                        "\"inventory\": [{ }] " +
                        "}" +
                        "}", getBlocksPath(location));
    }

    private static ResourceLocation getBlocksPath(@Nonnull ResourceLocation base) {
        return new ResourceLocation(base.getResourceDomain(), "blocks/" + base.getResourcePath());
    }

    private static ResourceLocation getTexturePath(@Nonnull ResourceLocation base) {
        return new ResourceLocation(base.getResourceDomain(), "textures/blocks/" + base.getResourcePath() + ".png");
    }

    private static ResourceLocation getJsonPath(@Nonnull ResourceLocation base) {
        return new ResourceLocation(base.getResourceDomain(), "blockstates/" + base.getResourcePath() + ".json");
    }

    @SubscribeEvent
    public void texturePre(TextureStitchEvent.Pre event) {
        for (CompressedResourceLocation locations : toCreateTextures) {
            if (!generateTexture(locations)) return;

            event.getMap().registerSprite(getBlocksPath(locations.compressed));
        }
    }

    private static boolean generateTexture(@Nonnull CompressedResourceLocation locations) {
        String state = getBlockState(locations.compressed);
        BlockResourcePack.INSTANCE.addBlockState(getJsonPath(locations.compressed), state);

        BufferedImage image;
        try {
            image = ImageIO.read(ImageUtil.getTextureInputStream(new ResourceLocation(locations.baseTexture)));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        int scale = 1 + locations.compressionAmount;

        int squareSize = Math.min(image.getWidth(), image.getHeight());

        WritableRaster raster = image.getColorModel().createCompatibleWritableRaster(squareSize * scale, squareSize * scale);

        int[] pixels = image.getData().getPixels(0, 0, squareSize, squareSize, (int[]) null);

        for (int x = 0; x < scale; x++) {
            for (int y = 0; y < scale; y++) {
                raster.setPixels(x * squareSize, y * squareSize, squareSize, squareSize, pixels);
            }
        }

        BufferedImage compressedImage = new BufferedImage(image.getColorModel(), raster, false, null);

        if (compressedImage.getWidth() > OverloadedConfig.compressedConfig.maxTextureWidth) {
            compressedImage = ImageUtil.scaleDownToWidth(compressedImage, OverloadedConfig.compressedConfig.maxTextureWidth);
        }

        ResourceLocation rl = getTexturePath(locations.compressed);
        BlockResourcePack.INSTANCE.addImage(rl, compressedImage);

        return true;
    }

    public static class CompressedResourceLocation {
        final String baseTexture;
        final ResourceLocation compressed;
        final int compressionAmount;

        public CompressedResourceLocation(String baseTexture, @Nonnull ResourceLocation compressed, int compressionAmount) {
            this.baseTexture = baseTexture;
            this.compressed = compressed;
            this.compressionAmount = compressionAmount;
        }
    }

}
