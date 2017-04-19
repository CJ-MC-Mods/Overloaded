package com.cjm721.overloaded.client;

import com.cjm721.overloaded.client.render.block.compressed.CompressedModelLoader;
import com.cjm721.overloaded.client.resource.OverloadedCustomResourceManager;
import com.cjm721.overloaded.common.CommonProxy;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

//        OverloadedCustomResourceManager customResourceManager = new OverloadedCustomResourceManager();
//        ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(customResourceManager);

//        Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("overloaded", "testCobble"), ))

        SimpleTexture texture = new SimpleTexture(new ResourceLocation("overloaded", "dynamic/textures/icon.png"));
        try {
            texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        OBJLoader.INSTANCE.addDomain(MODID);

//        ModelLoaderRegistry.registerLoader();
        new CompressedModelLoader();
        ModBlocks.registerModels();
        ModItems.registerModels();

        MinecraftForge.EVENT_BUS.register(ModItems.distanceBreaker);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }
}
