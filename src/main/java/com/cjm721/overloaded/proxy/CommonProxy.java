package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage;
import com.cjm721.overloaded.capabilities.CapabilityHyperEnergy;
import com.cjm721.overloaded.capabilities.CapabilityHyperFluid;
import com.cjm721.overloaded.capabilities.CapabilityHyperItem;
import com.cjm721.overloaded.config.syncer.ConfigSyncEventHandler;
import com.cjm721.overloaded.fluid.ModFluids;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.item.functional.ItemMultiTool;
import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.network.handler.KeyBindPressedHandler;
import com.cjm721.overloaded.network.handler.NoClipUpdateHandler;
import com.cjm721.overloaded.network.handler.PlayerMessageHandler;
import com.cjm721.overloaded.network.packets.*;
import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.LinkedList;
import java.util.List;

import static net.minecraftforge.fml.network.NetworkRegistry.newSimpleChannel;

@Mod.EventBusSubscriber(modid = Overloaded.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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

    MinecraftForge.EVENT_BUS.register(new ConfigSyncEventHandler());
    MinecraftForge.EVENT_BUS.register(new ArmorEventHandler());
  }

  @SubscribeEvent
  public static void registerFluids(RegistryEvent.Register<Fluid> event) {
    ModFluids.init(event.getRegistry());
  }

  @SubscribeEvent
  public static void registerBlocks(RegistryEvent.Register<Block> event) {
    ModBlocks.init(event.getRegistry());
  }

  @SubscribeEvent
  public static void registerItems(RegistryEvent.Register<Item> event) {
    ModItems.init();

    event.getRegistry().registerAll(itemToRegister.toArray(new Item[0]));
  }

  @SubscribeEvent
  public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
    ModTiles.init(event.getRegistry());
  }
}
