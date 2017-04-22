package com.cjm721.overloaded.common;

import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.network.handler.MultiToolLeftClickHandler;
import com.cjm721.overloaded.common.network.handler.MultiToolRightClickHandler;
import com.cjm721.overloaded.common.network.packets.MultiToolLeftClickMessage;
import com.cjm721.overloaded.common.network.packets.MultiToolRightClickMessage;
import com.cjm721.overloaded.common.util.CapabilityHyperEnergy;
import com.cjm721.overloaded.common.util.CapabilityHyperFluid;
import com.cjm721.overloaded.common.util.CapabilityHyperItem;
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
