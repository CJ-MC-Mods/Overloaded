package com.cjm721.overloaded.client.render.dynamic.general;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.resource.BlockResourcePack;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.client.render.dynamic.ImageUtil.getTextureInputStream;

@OnlyIn(Dist.CLIENT)
public class ResizeableTextureGenerator {

  private static final List<ResizableTexture> toCreateTextures = new ArrayList<>();

  public static void addToTextureQueue(ResizableTexture location) {
    synchronized (toCreateTextures) {
      toCreateTextures.add(location);
    }
  }

  @SubscribeEvent
  public void texturePre(@Nonnull TextureStitchEvent.Pre event) {
    if (!event.getMap().getBasePath().equals("textures")) {
      return;
    }

    event.addSprite(new ResourceLocation(MODID, "item/multi_right_arm"));
    event.addSprite(new ResourceLocation(MODID, "item/multi_left_arm"));
    event.addSprite(new ResourceLocation(MODID, "item/multi_leg"));
    event.addSprite(new ResourceLocation(MODID, "item/multi_helmet"));
    event.addSprite(new ResourceLocation(MODID, "item/multi_body"));
    event.addSprite(new ResourceLocation(MODID, "item/multi_belt"));
    event.addSprite(new ResourceLocation(MODID, "item/multi_boot"));
//    event.getMap().func_215256_a(Minecraft.getInstance().getResourceManager(),
//        ImmutableSet.of(
//            new ResourceLocation(MODID, "item/multi_helmet"),
//            new ResourceLocation(MODID, "item/multi_body.png"),
//            new ResourceLocation(MODID, "textures/item/multi_leg"),
//            new ResourceLocation(MODID, "textures/item/multi_right_arm.png")
//            ));
//    ExecutorService exectors = Executors.newCachedThreadPool();
    //    Minecraft.getInstance()
    //        .getTextureManager()
    //        .loadTexture(
    //            new ResourceLocation(MODID, "item/multi_helmet"),
    //            new SimpleTexture(new ResourceLocation(MODID, "item/multi_helmet")));

    //    Minecraft.getInstance()
    //        .getTextureManager()
    //        .loadTexture(
    //            new ResourceLocation(MODID, "item/multi_body.png"),
    //            new SimpleTexture(new ResourceLocation(MODID, "item/multi_body.png")));

    //    Minecraft.getInstance()
    //        .getTextureManager()
    //        .loadTexture(
    //            new ResourceLocation(MODID, "textures/item/multi_leg"),
    //            new SimpleTexture(new ResourceLocation(MODID, "textures/item/multi_leg")));

//    Minecraft.getInstance()
//        .getTextureManager()
//        .loadAsync(new ResourceLocation(MODID, "textures/item/multi_right_arm.png"), exectors).join();
    //    Minecraft.getInstance()
    //        .getTextureManager()
    //        .loadTexture(
    //            ,
    //            new SimpleTexture(new ResourceLocation(MODID,
    // "textures/item/multi_right_arm.png")));

    synchronized (toCreateTextures) {
      for (ResizableTexture resizableTexture : toCreateTextures) {
        BufferedImage image = null;
        try {
          image = ImageIO.read(getTextureInputStream(resizableTexture.originalTexture));
        } catch (IOException e) {
          e.printStackTrace();
        }

        if (image == null) continue;

        image = ImageUtil.scaleDownToWidth(image, resizableTexture.resizeToWidth);

        BlockResourcePack.INSTANCE.addImage(resizableTexture.generatedName, image);

        event.getMap().getSprite(cleanForSprite(resizableTexture.generatedName));
      }
    }
  }

  @Nonnull
  private ResourceLocation cleanForSprite(@Nonnull ResourceLocation location) {
    String path = location.getPath();

    if (path.startsWith("textures/")) {
      return new ResourceLocation(location.getNamespace(), path.substring(9).replace(".png", ""));
    }
    return location;
  }

  public static class ResizableTexture {
    final ResourceLocation originalTexture;
    final ResourceLocation generatedName;
    final int resizeToWidth;

    public ResizableTexture(
        ResourceLocation originalTexture, ResourceLocation generatedName, int resizeToWidth) {
      this.originalTexture = originalTexture;
      this.generatedName = generatedName;
      this.resizeToWidth = resizeToWidth;
    }
  }
}
