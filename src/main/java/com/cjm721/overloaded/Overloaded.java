package com.cjm721.overloaded;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.network.container.ModContainers;
import com.cjm721.overloaded.proxy.CommonProxy;
import com.cjm721.overloaded.tile.ModTiles;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Overloaded.MODID)
public class Overloaded {

  public static Overloaded instance;

  public static final String MODID = "overloaded";

  public static final Logger logger = LogUtils.getLogger();


  public Overloaded(IEventBus modEventBus, ModContainer modContainer) {
    instance = this;
//    if (FMLEnvironment.dist == Dist.CLIENT) {
//      proxy = new ClientProxy();
//    } else {
//      proxy = new ServerProxy();
//    }
    modEventBus.addListener(this::commonSetup);

//    NeoForge.EVENT_BUS.register(proxy);

    modContainer.registerConfig(ModConfig.Type.COMMON, OverloadedConfig.INSTANCE.getConfig(ModConfig.Type.COMMON));
    modContainer.registerConfig(ModConfig.Type.SERVER, OverloadedConfig.INSTANCE.getConfig(ModConfig.Type.SERVER));
    modContainer.registerConfig(ModConfig.Type.CLIENT, OverloadedConfig.INSTANCE.getConfig(ModConfig.Type.CLIENT));

    ModBlocks.BLOCKS.register(modEventBus);
    ModItems.ITEMS.register(modEventBus);
    OverloadedItemGroups.CREATIVE_MODE_TABS.register(modEventBus);
    ModContainers.MENUS.register(modEventBus);
    ModTiles.BLOCK_ENTITY_TYPES.register(modEventBus);

//    proxy.registerEvents();
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    logger.atInfo().log("AVC Entries: {}}", ModBlocks.BLOCKS.getEntries());
//    proxy.commonSetup(event);
  }
}
