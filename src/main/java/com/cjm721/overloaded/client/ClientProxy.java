package com.cjm721.overloaded.client;

import com.cjm721.overloaded.client.render.block.compressed.CompressedModelLoader;
import com.cjm721.overloaded.client.resource.CompressedResoucePack;
import com.cjm721.overloaded.common.CommonProxy;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.item.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

//        OverloadedCustomResourceManager customResourceManager = new OverloadedCustomResourceManager();
//        ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(customResourceManager);

//        Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("overloaded", "testCobble"), ))


        //SimpleTexture texture = new SimpleTexture();
        OBJLoader.INSTANCE.addDomain(MODID);


        ModelLoaderRegistry.registerLoader(new CompressedModelLoader());

        MinecraftForge.EVENT_BUS.register(ModItems.distanceBreaker);
        MinecraftForge.EVENT_BUS.register(this);


        ModBlocks.registerModels();
        ModItems.registerModels();

        CompressedResoucePack.INSTANCE.addDomain("overloaded");
        CompressedResoucePack.INSTANCE.inject();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

//        Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries().add(new ResourcePackRepository.Entry(new File("")));

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

//        BufferedImage image = null;
//        try {
//            image = TextureUtil.readBufferedImage(new FileInputStream(new File("C:\\Users\\CJ\\Pictures\\Camera Roll\\IMG_4450.JPG")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        DynamicTexture texture = new DynamicTexture(image);
//        ResourceLocation rl = new ResourceLocation("overloaded", "textures/dynamic/logo.png");
//        boolean result = Minecraft.getMinecraft().getTextureManager().loadTexture(rl, texture);
//        Minecraft.getMinecraft().getTextureManager().bindTexture(rl);
    }

    @SubscribeEvent
    public void texturePre(TextureStitchEvent.Post event) {
        for(EnumFacing facing: EnumFacing.values()) {
            IBlockState state =Blocks.COBBLESTONE.getDefaultState();
//            TextureAtlasSprite sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state).getQuads(state,facing,0).get(0).getSprite();
//
//            sprite.getFrameTextureData(0);
        }
        BufferedImage image = null;

        try {
//            TextureAtlasSprite sprite = event.getMap().getTextureExtry("minecraft:blocks/cobblestone");

            image = TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(
                    new ResourceLocation("minecraft", "textures/blocks/cobblestone.png")).getInputStream());
//            image = TextureUtil.readBufferedImage(new FileInputStream(new File("C:\\Users\\CJ\\Desktop\\temp.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        int scale = 16;
        WritableRaster raster = image.getColorModel().createCompatibleWritableRaster(image.getWidth()*scale,image.getHeight()*scale);
        int[] pixels = image.getData().getPixels(0,0,image.getWidth(), image.getHeight(), (int[])null);

        for(int x = 0; x < scale; x++) {
            for(int y = 0; y < scale; y++) {
                raster.setPixels(x*image.getWidth(),y*image.getHeight(),image.getWidth(),image.getHeight(),pixels);
            }
        }
        BufferedImage compressedImage = new BufferedImage(image.getColorModel(), raster, true, null);

        ResourceLocation rl = new ResourceLocation("overloaded", "textures/dynamic/logo.png");
        CompressedResoucePack.INSTANCE.addImage(rl, compressedImage);
        event.getMap().registerSprite(new ResourceLocation("overloaded", "dynamic/logo"));
    }
}
