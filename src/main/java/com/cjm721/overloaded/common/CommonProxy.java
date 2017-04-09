package com.cjm721.overloaded.common;

import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.util.CapabilityHyperEnergy;
import com.cjm721.overloaded.common.util.CapabilityHyperItem;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by CJ on 4/6/2017.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.init();
        ModItems.init();

        CapabilityHyperItem.register();
        CapabilityHyperEnergy.register();
    }

    public void init(FMLInitializationEvent event) {
        ModBlocks.addRecipes();
    }
}
