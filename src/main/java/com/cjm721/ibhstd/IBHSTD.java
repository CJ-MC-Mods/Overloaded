package com.cjm721.ibhstd;

import com.cjm721.ibhstd.common.CommonProxy;
import com.cjm721.ibhstd.common.block.ModBlocks;
import com.cjm721.ibhstd.client.render.block.compressed.CompressedModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by CJ on 4/2/2017.
 */
@Mod(modid = IBHSTD.MODID, version = IBHSTD.VERSION)
public class IBHSTD {

    @Mod.Instance(IBHSTD.MODID)
    public static IBHSTD instance;

    public static final String MODID = "ibhstd";
    public static final String VERSION = "1.0";

    public static final String PROXY_CLIENT = "com.cjm721.ibhstd.client.ClientProxy";
    public static final String PROXY_SERVER = "com.cjm721.ibhstd.common.CommonProxy";

    @SidedProxy(clientSide = IBHSTD.PROXY_CLIENT, serverSide = IBHSTD.PROXY_SERVER)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Loader.isModLoaded("botania");
        ModBlocks.init();

        proxy.preInit(event);
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
}
