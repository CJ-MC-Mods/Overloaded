package com.cjm721.overloaded;

import com.cjm721.overloaded.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by CJ on 4/2/2017.
 */
@Mod(modid = OVERLOADED.MODID, version = OVERLOADED.VERSION)
public class OVERLOADED {

    @Mod.Instance(OVERLOADED.MODID)
    public static OVERLOADED instance;

    public static final String MODID = "overloaded";
    public static final String VERSION = "${mod_version}";

    public static final String PROXY_CLIENT = "com.cjm721.overloaded.client.ClientProxy";
    public static final String PROXY_SERVER = "com.cjm721.overloaded.common.CommonProxy";

    @SidedProxy(clientSide = OVERLOADED.PROXY_CLIENT, serverSide = OVERLOADED.PROXY_SERVER)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
}
