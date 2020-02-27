package com.cjm721.overloaded;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.proxy.ClientProxy;
import com.cjm721.overloaded.proxy.CommonProxy;
import com.cjm721.overloaded.proxy.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = Overloaded.MODID)
public class Overloaded {

  public static Overloaded instance;

  public static final String MODID = "overloaded";
  static final String VERSION = "${mod_version}";

  public static final CommonProxy proxy =
      DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

  public static final Logger logger = LogManager.getLogger();

  public Overloaded() {
    instance = this;
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFingerprintException);
    MinecraftForge.EVENT_BUS.register(OverloadedConfig.INSTANCE);

    ModContainer activeContainer = ModLoadingContext.get().getActiveContainer();
    ModConfig commonConfig = new ModConfig(
        ModConfig.Type.COMMON,
        OverloadedConfig.INSTANCE.getConfig(ModConfig.Type.COMMON),
        activeContainer);
    activeContainer.addConfig(commonConfig);
    ModConfig serverConfig = new ModConfig(
        ModConfig.Type.SERVER,
        OverloadedConfig.INSTANCE.getConfig(ModConfig.Type.SERVER),
        activeContainer);
    activeContainer.addConfig(serverConfig);
    ModConfig clientConfig = new ModConfig(
        ModConfig.Type.CLIENT,
        OverloadedConfig.INSTANCE.getConfig(ModConfig.Type.CLIENT),
        activeContainer);
    activeContainer.addConfig(clientConfig);
  }

  private void preInit(final FMLCommonSetupEvent event) {
    proxy.commonSetup(event);
  }

  private void onFingerprintException(FMLFingerprintViolationEvent event) {
    logger.warn(
        "Invalid fingerprint detected! The file "
            + event.getSource().getName()
            + " may have been tampered with. This version will NOT be supported by cjm721!");
  }
}
