package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage;
import com.cjm721.overloaded.capabilities.CapabilityHyperEnergy;
import com.cjm721.overloaded.capabilities.CapabilityHyperFluid;
import com.cjm721.overloaded.capabilities.CapabilityHyperItem;
import com.cjm721.overloaded.fluid.ModFluids;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.item.functional.ItemMultiTool;
import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.network.container.ModContainers;
import com.cjm721.overloaded.network.handler.ContainerDataHandler;
import com.cjm721.overloaded.network.handler.KeyBindPressedHandler;
import com.cjm721.overloaded.network.handler.NoClipUpdateHandler;
import com.cjm721.overloaded.network.handler.PlayerMessageHandler;
import com.cjm721.overloaded.network.packets.*;
import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.world.level.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.common.neoforged;
import net.neoforged.event.RegistryEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.network.simple.SimpleChannel;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.LinkedList;
import java.util.List;

import static net.neoforged.fml.network.NetworkRegistry.newSimpleChannel;

public class CommonProxy {
  public SimpleChannel networkWrapper;

  public static final List<Block> blocksToRegister = new LinkedList<>();
  public static final List<Item> itemToRegister = new LinkedList<>();

  @SubscribeEvent
  public static void register(final RegisterPayloadHandlersEvent event) {
    // Sets the current network version
    final PayloadRegistrar registrar = event.registrar("1");

    registrar.commonToClient(ContainerDataMessage.TYPE, ContainerDataMessage.STREAM_CODEC, ContainerDataHandler::clientSide);
  }

  public void commonSetup(FMLCommonSetupEvent event) {
    CapabilityHyperItem.register();
    CapabilityHyperEnergy.register();
    CapabilityHyperFluid.register();
    CapabilityGenericDataStorage.register();

    networkWrapper =
        newSimpleChannel(
            ResourceLocation.of("overloaded_network", '_'), () -> "1.0", v -> true, v -> true);

    int dis = 0;
    networkWrapper.registerMessage(
        dis++,
        LeftClickBlockMessage.class,
        LeftClickBlockMessage::toBytes,
        LeftClickBlockMessage::fromBytes,
        new PlayerMessageHandler<>(ItemMultiTool::leftClickOnBlockServer));

    networkWrapper.registerMessage(
        dis++,
        RightClickBlockMessage.class,
        RightClickBlockMessage::toBytes,
        RightClickBlockMessage::fromBytes,
        new PlayerMessageHandler<>(ModItems.multiTool::rightClickWithItem));
    networkWrapper.registerMessage(
        dis++,
        RayGunMessage.class,
        RayGunMessage::toBytes,
        RayGunMessage::fromBytes,
        new PlayerMessageHandler<>(ModItems.rayGun::handleMessage));
    networkWrapper.registerMessage(
        dis++,
        MultiArmorSettingsMessage.class,
        MultiArmorSettingsMessage::toBytes,
        MultiArmorSettingsMessage::fromBytes,
        new PlayerMessageHandler<>(ModItems.customHelmet::updateSettings));
    networkWrapper.registerMessage(
        dis++,
        RailGunFireMessage.class,
        RailGunFireMessage::toBytes,
        RailGunFireMessage::fromBytes,
        new PlayerMessageHandler<>(ModItems.railgun::handleFireMessage));
    networkWrapper.registerMessage(
        dis++,
        RailGunSettingsMessage.class,
        RailGunSettingsMessage::toBytes,
        RailGunSettingsMessage::fromBytes,
        new PlayerMessageHandler<>(ModItems.railgun::handleSettingsMessage));

    networkWrapper.registerMessage(
        dis++,
        KeyBindPressedMessage.class,
        KeyBindPressedMessage::toBytes,
        KeyBindPressedMessage::fromBytes,
        new KeyBindPressedHandler());

    networkWrapper.registerMessage(
        dis++,
        NoClipStatusMessage.class,
        NoClipStatusMessage::toBytes,
        NoClipStatusMessage::fromBytes,
        new NoClipUpdateHandler());

    networkWrapper.registerMessage(
        dis++,
        ContainerDataMessage.class,
        ContainerDataMessage::toBytes,
        ContainerDataMessage::fromBytes,
        new ContainerDataHandler());

    neoforged.EVENT_BUS.register(new ArmorEventHandler());
  }

  public void registerEvents() {
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Fluid.class, this::registerFluids);
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(BlockEntityType.class, this::registerTileEntity);
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, this::registerContainers);
  }

  private void registerFluids(RegistryEvent.Register<Fluid> event) {
    ModFluids.init(event.getRegistry());
  }


  private void registerItems(RegistryEvent.Register<Item> event) {
    ModItems.init();

    event.getRegistry().registerAll(itemToRegister.toArray(new Item[0]));
  }

  private void registerTileEntity(RegistryEvent.Register<BlockEntityType<?>> event) {
    ModTiles.init(event.getRegistry());
  }

  private void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
    ModContainers.init(event.getRegistry());
  }


}
