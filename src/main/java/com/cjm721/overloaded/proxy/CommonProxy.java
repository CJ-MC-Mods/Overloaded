package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.item.functional.ItemMultiTool;
import com.cjm721.overloaded.item.functional.ItemRailGun;
import com.cjm721.overloaded.item.functional.ItemRayGun;
import com.cjm721.overloaded.item.functional.armor.ItemMultiHelmet;
import com.cjm721.overloaded.network.handler.ContainerDataHandler;
import com.cjm721.overloaded.network.handler.KeyBindPressedHandler;
import com.cjm721.overloaded.network.handler.NoClipUpdateHandler;
import com.cjm721.overloaded.network.handler.PlayerMessageHandler;
import com.cjm721.overloaded.network.packets.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Overloaded.MODID)
public class CommonProxy {

  @SubscribeEvent
  public static void register(final RegisterPayloadHandlersEvent event) {
    // Sets the current network version
    final PayloadRegistrar registrar = event.registrar("1");

    registrar.commonToClient(ContainerDataMessage.TYPE, ContainerDataMessage.STREAM_CODEC, ContainerDataHandler::clientSide);
    registrar.commonToServer(KeyBindPressedMessage.TYPE, KeyBindPressedMessage.STREAM_CODEC, KeyBindPressedHandler::accept);
    registrar.commonToServer(LeftClickBlockMessage.TYPE, LeftClickBlockMessage.STREAM_CODEC, new PlayerMessageHandler<>(ItemMultiTool::leftClickOnBlockServer)::accept);
    registrar.commonToServer(RightClickBlockMessage.TYPE, RightClickBlockMessage.STREAM_CODEC, new PlayerMessageHandler<>(ItemMultiTool::rightClickWithItem)::accept);
    registrar.commonToServer(RayGunMessage.TYPE, RayGunMessage.STREAM_CODEC, new PlayerMessageHandler<>(ItemRayGun::handleMessage)::accept);
    registrar.commonToServer(MultiArmorSettingsMessage.TYPE, MultiArmorSettingsMessage.STREAM_CODEC, new PlayerMessageHandler<>(ItemMultiHelmet::updateSettings)::accept);
    registrar.commonToServer(RailGunFireMessage.TYPE, RailGunFireMessage.STREAM_CODEC, new PlayerMessageHandler<>(ItemRailGun::handleFireMessage)::accept);
    registrar.commonToServer(RailGunSettingsMessage.TYPE, RailGunSettingsMessage.STREAM_CODEC, new PlayerMessageHandler<>(ItemRailGun::handleSettingsMessage)::accept);
    registrar.commonToClient(NoClipStatusMessage.TYPE, NoClipStatusMessage.STREAM_CODEC, NoClipUpdateHandler::accept);
  }

//  public void commonSetup(FMLCommonSetupEvent event) {
//    CapabilityHyperItem.register();
//    CapabilityHyperEnergy.register();
//    CapabilityHyperFluid.register();
//    CapabilityGenericDataStorage.register();
//
//    neoforged.EVENT_BUS.register(new ArmorEventHandler());
//  }
//
//  public void registerEvents() {
//    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Fluid.class, this::registerFluids);
//    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
//    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(BlockEntityType.class, this::registerTileEntity);
//    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, this::registerContainers);
//  }
//
//  private void registerFluids(RegistryEvent.Register<Fluid> event) {
//    ModFluids.init(event.getRegistry());
//  }
//
//
//  private void registerItems(RegistryEvent.Register<Item> event) {
//    ModItems.init();
//
//    event.getRegistry().registerAll(itemToRegister.toArray(new Item[0]));
//  }
//
//  private void registerTileEntity(RegistryEvent.Register<BlockEntityType<?>> event) {
//    ModTiles.init(event.getRegistry());
//  }
//
//  private void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
//    ModContainers.init(event.getRegistry());
//  }


}
