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
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.LinkedList;
import java.util.List;

import static net.minecraftforge.fml.network.NetworkRegistry.newSimpleChannel;

public class CommonProxy {
  public SimpleChannel networkWrapper;

  public static final List<Block> blocksToRegister = new LinkedList<>();
  public static final List<Item> itemToRegister = new LinkedList<>();

  public void commonSetup(FMLCommonSetupEvent event) {
    CapabilityHyperItem.register();
    CapabilityHyperEnergy.register();
    CapabilityHyperFluid.register();
    CapabilityGenericDataStorage.register();

    networkWrapper =
        newSimpleChannel(
            ResourceLocation.create("overloaded_network", '_'), () -> "1.0", v -> true, v -> true);

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

    MinecraftForge.EVENT_BUS.register(new ArmorEventHandler());
  }

  public void registerEvents() {
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Fluid.class, this::registerFluids);
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, this::registerBlocks);
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, this::registerTileEntity);
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, this::registerContainers);
  }

  private void registerFluids(RegistryEvent.Register<Fluid> event) {
    ModFluids.init(event.getRegistry());
  }

  private void registerBlocks(RegistryEvent.Register<Block> event) {
    ModBlocks.init(event.getRegistry());
  }

  private void registerItems(RegistryEvent.Register<Item> event) {
    ModItems.init();

    event.getRegistry().registerAll(itemToRegister.toArray(new Item[0]));
  }

  private void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
    ModTiles.init(event.getRegistry());
  }

  private void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
    ModContainers.init(event.getRegistry());
  }


}
