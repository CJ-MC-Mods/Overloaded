package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.item.functional.ItemMultiTool;
import com.cjm721.overloaded.item.functional.ItemRailGun;
import com.cjm721.overloaded.item.functional.ItemRayGun;
import com.cjm721.overloaded.item.functional.armor.ItemMultiHelmet;
import com.cjm721.overloaded.network.handler.ContainerDataHandler;
import com.cjm721.overloaded.network.handler.KeyBindPressedHandler;
import com.cjm721.overloaded.network.handler.NoClipUpdateHandler;
import com.cjm721.overloaded.network.handler.PlayerMessageHandler;
import com.cjm721.overloaded.network.packets.*;
import com.cjm721.overloaded.storage.itemwrapper.IntEnergyWrapper;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.functional.TileInstantFurnace;
import com.cjm721.overloaded.tile.functional.TileItemInterface;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.List;
import java.util.Optional;

import static com.cjm721.overloaded.capabilities.CapabilityHyperEnergy.BLOCK_HYPER_ENERGY_HANDLER;
import static com.cjm721.overloaded.capabilities.CapabilityHyperFluid.BLOCK_HYPER_FLUID_HANDLER;
import static com.cjm721.overloaded.capabilities.CapabilityHyperItem.BLOCK_HYPER_ITEM_HANDLER;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Overloaded.MODID)
public class CommonProxy {

  @SubscribeEvent
  public static void register(final RegisterPayloadHandlersEvent event) {
    // Sets the current network version
    final PayloadRegistrar registrar = event.registrar("1");

    registrar.playToClient(
        ContainerDataMessage.TYPE,
        ContainerDataMessage.STREAM_CODEC,
        ContainerDataHandler::clientSide);
    registrar.playToServer(
        KeyBindPressedMessage.TYPE,
        KeyBindPressedMessage.STREAM_CODEC,
        KeyBindPressedHandler::accept);
    registrar.playToServer(
        LeftClickBlockMessage.TYPE,
        LeftClickBlockMessage.STREAM_CODEC,
        new PlayerMessageHandler<>(ItemMultiTool::leftClickOnBlockServer)::accept);
    registrar.playToServer(
        RightClickBlockMessage.TYPE,
        RightClickBlockMessage.STREAM_CODEC,
        new PlayerMessageHandler<>(ItemMultiTool::rightClickWithItem)::accept);
    registrar.playToServer(
        RayGunMessage.TYPE,
        RayGunMessage.STREAM_CODEC,
        new PlayerMessageHandler<>(ItemRayGun::handleMessage)::accept);
    registrar.playToServer(
        MultiArmorSettingsMessage.TYPE,
        MultiArmorSettingsMessage.STREAM_CODEC,
        new PlayerMessageHandler<>(ItemMultiHelmet::updateSettings)::accept);
    registrar.playToServer(
        RailGunFireMessage.TYPE,
        RailGunFireMessage.STREAM_CODEC,
        new PlayerMessageHandler<>(ItemRailGun::handleFireMessage)::accept);
    registrar.playToServer(
        RailGunSettingsMessage.TYPE,
        RailGunSettingsMessage.STREAM_CODEC,
        new PlayerMessageHandler<>(ItemRailGun::handleSettingsMessage)::accept);
    registrar.playToServer(
        NoClipStatusMessage.TYPE, NoClipStatusMessage.STREAM_CODEC, NoClipUpdateHandler::accept);
  }

  @SubscribeEvent
  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerBlockEntity(
        Capabilities.EnergyStorage.BLOCK,
        ModTiles.almostInfiniteCapacitor.get(),
        (entity, side) -> entity.getStorage());
    event.registerBlockEntity(
        BLOCK_HYPER_ENERGY_HANDLER,
        ModTiles.almostInfiniteCapacitor.get(),
        (entity, side) -> entity.getStorage());
    event.registerBlockEntity(
        BLOCK_HYPER_ENERGY_HANDLER,
        ModTiles.trueInfiniteCapacitor.get(),
        (entity, side) -> entity.getStorage());

    event.registerBlockEntity(
        Capabilities.ItemHandler.BLOCK,
        ModTiles.almostInfiniteBarrel.get(),
        (entity, side) -> entity.getStorage());
    event.registerBlockEntity(
        BLOCK_HYPER_ITEM_HANDLER,
        ModTiles.almostInfiniteBarrel.get(),
        (entity, side) -> entity.getStorage());
    event.registerBlockEntity(
        BLOCK_HYPER_ITEM_HANDLER,
        ModTiles.trueInfiniteBarrel.get(),
        (entity, side) -> entity.getStorage());

    event.registerBlockEntity(
        Capabilities.FluidHandler.BLOCK,
        ModTiles.almostInfiniteTank.get(),
        (entity, side) -> entity.getStorage());
    event.registerBlockEntity(
        BLOCK_HYPER_FLUID_HANDLER,
        ModTiles.almostInfiniteTank.get(),
        (entity, side) -> entity.getStorage());
    event.registerBlockEntity(
        BLOCK_HYPER_FLUID_HANDLER,
        ModTiles.trueInfiniteTank.get(),
        (entity, side) -> entity.getStorage());

    event.registerBlockEntity(
        Capabilities.FluidHandler.BLOCK,
        ModTiles.infiniteWaterSource.get(),
        (entity, side) -> entity);

    event.registerBlockEntity(
        Capabilities.ItemHandler.BLOCK,
        ModTiles.instantFurnace.get(),
        TileInstantFurnace::getItemCapability);
    event.registerBlockEntity(
        Capabilities.EnergyStorage.BLOCK,
        ModTiles.instantFurnace.get(),
        (entity, side) -> entity.getProcessingStorage());

    event.registerBlockEntity(
        Capabilities.ItemHandler.BLOCK,
        ModTiles.itemInterface.get(),
        TileItemInterface::getItemCapability);

    event.registerItem(
        Capabilities.EnergyStorage.ITEM,
        (item, voidContext) -> new IntEnergyWrapper(item),
        ModItems.customHelmet.get(),
        ModItems.customChestplate.get(),
        ModItems.customLeggins.get(),
        ModItems.customBoots.get(),
        ModItems.railgun.get(),
        ModItems.rayGun.get());
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void registerProxyCapabilities(RegisterCapabilitiesEvent event) {
    List<ItemCapability<?, ?>> itemCaps = ItemCapability.getAll();
    for (BlockCapability<?, ?> cap : BlockCapability.getAllProxyable()) {
      Optional<ItemCapability<?, ?>> itemCap =
          itemCaps.stream()
              .filter(ic -> ic.typeClass().equals(cap.typeClass()) && ic.name().equals(cap.name()))
              .findAny();
      if (itemCap.isEmpty()) {
        continue;
      }

      Overloaded.logger.info(
          "Registering Item Interface Proxy Capability: {}", itemCap.get().name());
      event.registerBlockEntity(
          cap,
          ModTiles.itemInterface.get(),
          (block, context) -> block.getStoredItem().getCapability(cast(itemCap.get())));
    }

    List<EntityCapability<?, ?>> entityCaps = EntityCapability.getAll();

    for (BlockCapability<?, ?> cap : BlockCapability.getAllProxyable()) {
      Optional<EntityCapability<?, ?>> entityCap =
          entityCaps.stream()
              .filter(ic -> ic.typeClass().equals(cap.typeClass()) && ic.name().equals(cap.name()))
              .findAny();
      if (entityCap.isEmpty()) {
        continue;
      }

      Overloaded.logger.info(
          "Registering Player Interface Proxy Capability: {}", entityCap.get().name());
      event.registerBlockEntity(
          cap,
          ModTiles.playerInterface.get(),
          (block, context) -> {
            Optional<Player> playerOp = block.getPlayer();
            if (playerOp.isEmpty()) {
              return null;
            }
            Player player = playerOp.get();
            return player.getCapability(cast(entityCap.get()));
          });
    }
  }

  /**
   * Special method to get around casting ItemCapability\<?,?\> to a specific
   * ItemCapability\<T,Void\> based on the type from BlockCapability
   */
  @SuppressWarnings("unchecked")
  private static <T> ItemCapability<T, Void> cast(ItemCapability<?, ?> cap) {
    return (ItemCapability<T, Void>) cap;
  }

  @SuppressWarnings("unchecked")
  private static <T> EntityCapability<T, Void> cast(EntityCapability<?, ?> cap) {
    return (EntityCapability<T, Void>) cap;
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
  //    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Fluid.class,
  // this::registerFluids);
  //    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class,
  // this::registerItems);
  //    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(BlockEntityType.class,
  // this::registerTileEntity);
  //    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class,
  // this::registerContainers);
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
