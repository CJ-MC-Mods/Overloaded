package com.cjm721.overloaded;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.handler.ConfigSyncHandler;
import com.cjm721.overloaded.proxy.ClientProxy;
import com.cjm721.overloaded.proxy.CommonProxy;
import com.cjm721.overloaded.proxy.ServerProxy;
import com.google.gson.Gson;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static com.cjm721.overloaded.config.ForgeOverloadedConfigHolder.overloadedConfig;

@Mod(value = Overloaded.MODID)
public class Overloaded {

    public static Overloaded instance;

    public static final String MODID = "overloaded";
    static final String VERSION = "${mod_version}";

    private static final String PROXY_CLIENT = "com.cjm721.overloaded.proxy.ClientProxy";
    private static final String PROXY_SERVER = "com.cjm721.overloaded.proxy.ServerProxy";

    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public static File configFolder;
    public static Logger logger;

    public static OverloadedConfig cachedConfig;

    public Overloaded() {
        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFingerprintException);
    }

    private void preInit(final FMLCommonSetupEvent event) {
        Overloaded.logger = event.getModLog();
        configFolder = new File(event.getModConfigurationDirectory(), "overloaded/");
        configFolder.mkdir();
        ConfigSyncHandler.INSTANCE.updateConfig();
        proxy.preInit(event);
    }

    private void onFingerprintException(FMLFingerprintViolationEvent event) {
        FMLLog.log.warn("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been tampered with. This version will NOT be supported by the cjm721!");
    }
}
