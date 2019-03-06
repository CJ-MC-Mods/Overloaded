package com.cjm721.overloaded.client.render.dynamic;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@SideOnly(Side.CLIENT)
public class ImageUtil {
    public static BufferedImage scaleDownToWidth(@Nonnull BufferedImage original, int width) {
        double scale = original.getWidth() / (double) width;

        if (scale <= 1) {
            return original;
        }

        AffineTransform at = new AffineTransform();
        at.scale(1 / scale, 1 / scale);

        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        return scaleOp.filter(original, null);
    }

    public static InputStream getTextureInputStream(ResourceLocation location) throws IOException {
        return Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
    }

    public static void registerDynamicTexture(@Nonnull ResourceLocation location, int resolution) {
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                location,
                new ResourceLocation(location.getNamespace(),
                        location.getPath().replaceFirst("textures", "textures/dynamic")),
                resolution));
    }
}
