package com.cjm721.ibhstd;

import com.cjm721.ibhstd.common.block.ModBlocks;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by CJ on 4/2/2017.
 */
@Mod(modid = ModStart.MODID, version = ModStart.VERSION)
public class ModStart {

    public static final String MODID = "ibhstd";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Loader.isModLoaded("botania");

        ModBlocks.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        ModBlocks.addRecipes();
    }
}
