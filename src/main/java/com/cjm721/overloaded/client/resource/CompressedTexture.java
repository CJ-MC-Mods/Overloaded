package com.cjm721.overloaded.client.resource;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by CJ on 4/18/2017.
 */
public class CompressedTexture extends AbstractTexture {

    @Override
    public void loadTexture(@Nonnull IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        try
        {
            BufferedImage bufferedimage = TextureUtil.readBufferedImage(new FileInputStream(new File("C:\\Users\\CJ\\Pictures\\Camera Roll\\IMG_4450.JPG")));
            boolean flag = false;
            boolean flag1 = false;

            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedimage, flag, flag1);
        }
        finally
        {
            //IOUtils.closeQuietly((Closeable)iresource);
        }
    }
}
