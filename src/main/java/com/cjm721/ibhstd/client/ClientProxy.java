package com.cjm721.ibhstd.client;

import com.cjm721.ibhstd.client.render.block.compressed.CompressedModelLoader;
import com.cjm721.ibhstd.common.CommonProxy;
import com.cjm721.ibhstd.common.block.ModBlocks;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.cjm721.ibhstd.IBHSTD.MODID;

/**
 * Created by CJ on 4/6/2017.
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        OBJLoader.INSTANCE.addDomain(MODID);

        ModelLoaderRegistry.registerLoader(new CompressedModelLoader());
        ModBlocks.registerModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }
}
