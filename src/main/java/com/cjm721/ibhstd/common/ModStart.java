package com.cjm721.ibhstd.common;

import com.cjm721.ibhstd.common.block.ModBlocks;
import com.cjm721.ibhstd.client.render.block.compressed.CompressedModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by CJ on 4/2/2017.
 */
@Mod(modid = ModStart.MODID, version = ModStart.VERSION)
public class ModStart {

    public static final String MODID = "ibhstd";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Loader.isModLoaded("botania");
        ModBlocks.init();

        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            initClient();
        }
    }

    @SideOnly(Side.CLIENT)
    public void initClient() {
        ModelLoaderRegistry.registerLoader(new CompressedModelLoader());
        ModBlocks.registerModels();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        ModBlocks.addRecipes();

//        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
//            initClient();
//        }
    }
}
