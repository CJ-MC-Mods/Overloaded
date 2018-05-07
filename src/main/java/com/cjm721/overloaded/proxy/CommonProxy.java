package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.network.OverloadedGuiHandler;
import com.cjm721.overloaded.network.handler.KeyBindPressedHandler;
import com.cjm721.overloaded.network.handler.NoClipUpdateHandler;
import com.cjm721.overloaded.network.handler.PlayerMessageHandler;
import com.cjm721.overloaded.network.packets.*;
import com.cjm721.overloaded.storage.GenericDataStorage;
import com.cjm721.overloaded.util.CapabilityHyperEnergy;
import com.cjm721.overloaded.util.CapabilityHyperFluid;
import com.cjm721.overloaded.util.CapabilityHyperItem;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.LinkedList;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;

@Mod.EventBusSubscriber
public class CommonProxy {

    public SimpleNetworkWrapper networkWrapper;

    public static final List<Block> blocksToRegister = new LinkedList<>();
    public static final List<Item> itemToRegister = new LinkedList<>();
    public static Fluid pureMatter;


    public void preInit(FMLPreInitializationEvent event) {
        createFluids();
        ModBlocks.init();
        ModItems.init();

        CapabilityHyperItem.register();
        CapabilityHyperEnergy.register();
        CapabilityHyperFluid.register();
        GenericDataStorage.register();
    }

    private void createFluids() {
        String textureName = "blocks/pure_matter";
        ResourceLocation still = new ResourceLocation(MODID, textureName + "_still");
//        ResourceLocation flowing = new ResourceLocation(MODID,textureName + "_flow");

        pureMatter = new Fluid("pure_matter", still, still).setDensity(3000).setViscosity(6000).setRarity(EnumRarity.EPIC);
        if (!FluidRegistry.registerFluid(pureMatter)) {
            pureMatter = FluidRegistry.getFluid("pure_matter");
        }
    }

    public void init(FMLInitializationEvent event) {
        ModBlocks.secondaryCompressedInit();
        networkWrapper = new SimpleNetworkWrapper("overloaded");

        int dis = 0;
        networkWrapper.registerMessage(new PlayerMessageHandler<>(ModItems.multiTool::leftClickOnBlockServer), LeftClickBlockMessage.class, dis++, Side.SERVER);
        networkWrapper.registerMessage(new PlayerMessageHandler<>(ModItems.multiTool::rightClickWithItem), RightClickBlockMessage.class, dis++, Side.SERVER);
        networkWrapper.registerMessage(new PlayerMessageHandler<>(ModItems.rayGun::handleMessage), RayGunMessage.class, dis++, Side.SERVER);
        networkWrapper.registerMessage(new PlayerMessageHandler<>(ModItems.customHelmet::updateSettings), MultiArmorSettingsMessage.class, dis++, Side.SERVER);
        networkWrapper.registerMessage(new PlayerMessageHandler<>(ModItems.railgun::handleFireMessage), RailGunFireMessage.class, dis++, Side.SERVER);
        networkWrapper.registerMessage(new PlayerMessageHandler<>(ModItems.railgun::handleSettingsMessage), RailGunSettingsMessage.class, dis++, Side.SERVER);

        networkWrapper.registerMessage(KeyBindPressedHandler.class, KeyBindPressedMessage.class, dis++, Side.SERVER);
        networkWrapper.registerMessage(NoClipUpdateHandler.class, NoClipStatusMessage.class, dis++, Side.CLIENT);

        MinecraftForge.EVENT_BUS.register(ModItems.multiTool);
        MinecraftForge.EVENT_BUS.register(ModItems.railgun);
        MinecraftForge.EVENT_BUS.register(new ArmorEventHandler());

        NetworkRegistry.INSTANCE.registerGuiHandler(Overloaded.instance, new OverloadedGuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for (Block block : blocksToRegister)
            event.getRegistry().register(block);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : itemToRegister)
            event.getRegistry().register(item);
    }

}
