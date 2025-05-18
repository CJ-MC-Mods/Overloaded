package com.cjm721.overloaded;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(Overloaded.MODID)
public class Overloaded {

  public static Overloaded instance;

  public static final String MODID = "overloaded";

//  public static final CommonProxy proxy =
//      DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

  public static final Logger logger = LogUtils.getLogger();

  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
  public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MODID);

  public Overloaded(IEventBus modEventBus, ModContainer modContainer) {
    instance = this;
    modEventBus.addListener(this::commonSetup);

    NeoForge.EVENT_BUS.register(this);

    modContainer.registerConfig(ModConfig.Type.COMMON, OverloadedConfig.INSTANCE.getConfig(ModConfig.Type.COMMON));
    modContainer.registerConfig(ModConfig.Type.SERVER, OverloadedConfig.INSTANCE.getConfig(ModConfig.Type.SERVER));
    modContainer.registerConfig(ModConfig.Type.CLIENT, OverloadedConfig.INSTANCE.getConfig(ModConfig.Type.CLIENT));

//    proxy.registerEvents();
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
//    proxy.commonSetup(event);
  }
}
