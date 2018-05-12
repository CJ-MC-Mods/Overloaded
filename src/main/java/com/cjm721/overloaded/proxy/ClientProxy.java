package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.client.render.RenderGlobalSpectator;
import com.cjm721.overloaded.client.render.dynamic.compressed.block.CompressedBlockAssets;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.resource.BlockResourcePack;
import com.cjm721.overloaded.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import static com.cjm721.overloaded.Overloaded.MODID;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public KeyBinding noClipKeybind;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        OBJLoader.INSTANCE.addDomain(MODID);
        MinecraftForge.EVENT_BUS.register(new CompressedBlockAssets());
        MinecraftForge.EVENT_BUS.register(new ResizeableTextureGenerator());

        BlockResourcePack.INSTANCE.addDomain("overloaded");
        BlockResourcePack.INSTANCE.inject();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        if (Overloaded.cachedConfig.specialConfig.noClipRenderFix)
            Minecraft.getMinecraft().renderGlobal = new RenderGlobalSpectator(Minecraft.getMinecraft());
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> java.awt.Color.CYAN.getRGB(), ModItems.multiTool, ModItems.customBoots, ModItems.customLeggins, ModItems.customChestplate, ModItems.customHelmet);

        noClipKeybind = new KeyBinding("overloaded.key.noclip", Keyboard.KEY_V, "overloaded.cat.key.multiarmor");
        ClientRegistry.registerKeyBinding(noClipKeybind);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocks.registerModels();
        ModItems.registerModels();
    }
}
