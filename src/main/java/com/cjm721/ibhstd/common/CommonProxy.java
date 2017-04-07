package com.cjm721.ibhstd.common;

import com.cjm721.ibhstd.common.block.ModBlocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by CJ on 4/6/2017.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {
        ModBlocks.addRecipes();
    }
}
