package com.cjm721.overloaded.client;

import com.cjm721.overloaded.client.render.block.compressed.CompressedModelLoader;
import com.cjm721.overloaded.common.CommonProxy;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.item.ModItems;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        OBJLoader.INSTANCE.addDomain(MODID);

        ModelLoaderRegistry.registerLoader(new CompressedModelLoader());
        ModBlocks.registerModels();
        ModItems.registerModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }
}
