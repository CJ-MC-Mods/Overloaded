package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.config.syncer.ConfigSyncEventHandler;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.network.handler.ConfigSyncHandler;
import com.cjm721.overloaded.network.handler.KeyBindPressedHandler;
import com.cjm721.overloaded.network.handler.NoClipUpdateHandler;
import com.cjm721.overloaded.network.handler.PlayerMessageHandler;
import com.cjm721.overloaded.network.packets.*;
import com.cjm721.overloaded.storage.GenericDataStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.CapabilityHyperEnergy;
import com.cjm721.overloaded.util.CapabilityHyperFluid;
import com.cjm721.overloaded.util.CapabilityHyperItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.LinkedList;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;
import static net.minecraftforge.fml.network.NetworkRegistry.newSimpleChannel;

@Mod.EventBusSubscriber(modid = Overloaded.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

  public SimpleChannel networkWrapper;

  public static final List<Block> blocksToRegister = new LinkedList<>();
  public static final List<Item> itemToRegister = new LinkedList<>();
  public static Fluid pureMatter;

  public void commonSetup(FMLCommonSetupEvent event) {
    FMLJavaModLoadingContext.get().getModEventBus().register(this);

    createFluids();

    CapabilityHyperItem.register();
    CapabilityHyperEnergy.register();
    CapabilityHyperFluid.register();
    GenericDataStorage.register();
    //    OreDictionary.registerOre("blockNetherStar", ModBlocks.netherStarBlock);

    networkWrapper =
        newSimpleChannel(
            ResourceLocation.create("overloaded_network", '_'), () -> "1.0", v -> true, v -> true);

    int dis = 0;
    networkWrapper.<LeftClickBlockMessage>registerMessage(
        dis++,
        LeftClickBlockMessage.class,
        LeftClickBlockMessage::toBytes,
        LeftClickBlockMessage::fromBytes,
        new PlayerMessageHandler<>(ModItems.multiTool::leftClickOnBlockServer));

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

    networkWrapper.registerMessage(dis++,
        NoClipStatusMessage.class,
        NoClipStatusMessage::toBytes,
        NoClipStatusMessage::fromBytes,
        new NoClipUpdateHandler());

//    networkWrapper.registerMessage(dis++,
//        ConfigSyncMessage.class,
//        ConfigSyncHandler.INSTANCE);

    MinecraftForge.EVENT_BUS.register(ModItems.multiTool);
    MinecraftForge.EVENT_BUS.register(ModItems.railgun);
    MinecraftForge.EVENT_BUS.register(new ArmorEventHandler());
    MinecraftForge.EVENT_BUS.register(new ConfigSyncEventHandler());

//    NetworkRegistry.INSTANCE.registerGuiHandler(Overloaded.instance, new OverloadedGuiHandler());
  }

  private void createFluids() {
    String textureName = "blocks/pure_matter";
    ResourceLocation still = new ResourceLocation(MODID, textureName + "_still");
    //      ResourceLocation flowing = new ResourceLocation(MODID,textureName + "_flow");

    pureMatter =
        new Fluid("pure_matter", still, still)
            .setDensity(3000)
            .setViscosity(6000)
            .setRarity(Rarity.EPIC);
//    if (!FluidRegistry.registerFluid(pureMatter)) {
//      pureMatter = FluidRegistry.getFluid("pure_matter");
//    }
  }

  @SubscribeEvent
  public static void registerBlocks(RegistryEvent.Register<Block> event) {
    ModBlocks.init(event.getRegistry());
  }

  @SubscribeEvent
  public static void registerItems(RegistryEvent.Register<Item> event) {
    ModItems.init(event.getRegistry());

    event.getRegistry().registerAll(itemToRegister.toArray(new Item[0]));
  }

  @SubscribeEvent
  public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
    ModTiles.init(event.getRegistry());
  }
}
