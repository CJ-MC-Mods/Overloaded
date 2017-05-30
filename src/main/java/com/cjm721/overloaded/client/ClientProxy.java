package com.cjm721.overloaded.client;

import com.cjm721.overloaded.client.render.block.compressed.CompressedBlockAssets;
import com.cjm721.overloaded.client.render.entity.ArmorSecondarySpritesRegister;
import com.cjm721.overloaded.client.resource.CompressedResourcePack;
import com.cjm721.overloaded.common.CommonProxy;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.cjm721.overloaded.Overloaded.MODID;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        OBJLoader.INSTANCE.addDomain(MODID);
        MinecraftForge.EVENT_BUS.register(new CompressedBlockAssets());
        MinecraftForge.EVENT_BUS.register(new ArmorSecondarySpritesRegister());

        CompressedResourcePack.INSTANCE.addDomain("overloaded");
        CompressedResourcePack.INSTANCE.inject();

        ModBlocks.registerModels();
        ModItems.registerModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);


    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
