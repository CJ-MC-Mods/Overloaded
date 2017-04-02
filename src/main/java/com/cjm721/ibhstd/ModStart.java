package com.cjm721.ibhstd;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Created by CJ on 4/2/2017.
 */
@Mod(modid = ModStart.MODID, version = ModStart.VERSION)
public class ModStart {

    public static final String MODID = "ibhstd";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        System.out.println("DIRT BLOCK >> "+ Blocks.DIRT.getUnlocalizedName());
    }
}
