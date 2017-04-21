package com.cjm721.overloaded.client;

import com.cjm721.overloaded.client.render.block.compressed.CompressedBlockAssets;
import com.cjm721.overloaded.client.resource.CompressedResourcePack;
import com.cjm721.overloaded.common.CommonProxy;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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


//        ModelLoaderRegistry.registerLoader(new CompressedModelLoader());

        MinecraftForge.EVENT_BUS.register(ModItems.distanceBreaker);
        MinecraftForge.EVENT_BUS.register(new CompressedBlockAssets());

        ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(CompressedResourcePack.INSTANCE);

        ModBlocks.registerModels();
        ModItems.registerModels();

        CompressedResourcePack.INSTANCE.addDomain("overloaded");
        CompressedResourcePack.INSTANCE.inject();
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
}
