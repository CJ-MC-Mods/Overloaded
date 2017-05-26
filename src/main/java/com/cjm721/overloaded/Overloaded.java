package com.cjm721.overloaded;

import com.cjm721.overloaded.common.CommonProxy;
import com.cjm721.overloaded.common.config.OverloadedConfig;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Overloaded.MODID, version = Overloaded.VERSION,
        acceptedMinecraftVersions = "1.11.2",
        dependencies = "after:forge@[13.20.0.2266,)",
        useMetadata = true
        )
public class Overloaded {

    @Mod.Instance(Overloaded.MODID)
    public static Overloaded instance;

    public static final String MODID = "overloaded";
    public static final String VERSION = "${mod_version}";

    public static final String PROXY_CLIENT = "com.cjm721.overloaded.client.ClientProxy";
    public static final String PROXY_SERVER = "com.cjm721.overloaded.common.CommonProxy";

    @SidedProxy(clientSide = Overloaded.PROXY_CLIENT, serverSide = Overloaded.PROXY_SERVER)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration configuration = new Configuration(event.getSuggestedConfigurationFile());
        OverloadedConfig.I.init(configuration);

        if(configuration.hasChanged()) {
            configuration.save();
        }

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
