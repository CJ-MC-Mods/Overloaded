package com.cjm721.overloaded.common;

import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.util.CapabilityHyperEnergy;
import com.cjm721.overloaded.common.util.CapabilityHyperFluid;
import com.cjm721.overloaded.common.util.CapabilityHyperItem;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.init();
        ModItems.init();

        CapabilityHyperItem.register();
        CapabilityHyperEnergy.register();
        CapabilityHyperFluid.register();
    }

    public void init(FMLInitializationEvent event) {
        ModBlocks.addRecipes();
    }
}
