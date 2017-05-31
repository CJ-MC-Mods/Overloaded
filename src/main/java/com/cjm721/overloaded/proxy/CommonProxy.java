package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.network.handler.MultiToolLeftClickHandler;
import com.cjm721.overloaded.network.handler.MultiToolRightClickHandler;
import com.cjm721.overloaded.network.packets.MultiToolLeftClickMessage;
import com.cjm721.overloaded.network.packets.MultiToolRightClickMessage;
import com.cjm721.overloaded.util.CapabilityHyperEnergy;
import com.cjm721.overloaded.util.CapabilityHyperFluid;
import com.cjm721.overloaded.util.CapabilityHyperItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public SimpleNetworkWrapper networkWrapper;

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.init();
        ModItems.init();

        CapabilityHyperItem.register();
        CapabilityHyperEnergy.register();
        CapabilityHyperFluid.register();

        MinecraftForge.EVENT_BUS.register(ModItems.itemMultiTool);
    }

    public void init(FMLInitializationEvent event) {
        ModBlocks.addRecipes();
        ModItems.registerRecipes();

        networkWrapper = new SimpleNetworkWrapper("overloaded");
        networkWrapper.registerMessage(MultiToolLeftClickHandler.class, MultiToolLeftClickMessage.class, 0, Side.SERVER);
        networkWrapper.registerMessage(MultiToolRightClickHandler.class, MultiToolRightClickMessage.class, 1, Side.SERVER);
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
