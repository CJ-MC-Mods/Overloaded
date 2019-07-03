package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.item.RenderMultiToolAssist;
import com.cjm721.overloaded.client.render.tile.ItemInterfaceRenderer;
import com.cjm721.overloaded.client.render.tile.PlayerInterfaceRenderer;
import com.cjm721.overloaded.client.resource.BlockResourcePack;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.cjm721.overloaded.tile.functional.TilePlayerInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.cjm721.overloaded.Overloaded.MODID;

@OnlyIn(Dist.CLIENT)
// @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientProxy extends CommonProxy {

  public KeyBinding noClipKeybind;

  @Override
  public void commonSetup(FMLCommonSetupEvent event) {
    super.commonSetup(event);

    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

    OBJLoader.INSTANCE.addDomain(MODID);
    OBJLoader.INSTANCE.onResourceManagerReload(Minecraft.getInstance().getResourceManager());

    //    FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::textureStitch);
    FMLJavaModLoadingContext.get().getModEventBus().register(new ResizeableTextureGenerator());
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::registerModels);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::modelBakeEvent);
    //    MinecraftForge.EVENT_BUS.addListener();
    //    MinecraftForge.EVENT_BUS.addListener();

    BlockResourcePack.INSTANCE.addDomain(MODID);
    BlockResourcePack.INSTANCE.inject();

    //    if (OverloadedConfig.INSTANCE.specialConfig.noClipRenderFix)
    //      Minecraft.getInstance().renderGlobal = new
    // RenderGlobalSpectator(Minecraft.getInstance());
    //    Minecraft.getInstance()
    //        .getItemColors()
    //        .registerItemColorHandler(
    //            (stack, tintIndex) -> java.awt.Color.CYAN.getRGB(),
    //            ModItems.multiTool,
    //            ModItems.customBoots,
    //            ModItems.customLeggins,
    //            ModItems.customChestplate,
    //            ModItems.customHelmet);

    noClipKeybind = new KeyBinding("overloaded.key.noclip", 'v', "overloaded.cat.key.multiarmor");
    ClientRegistry.registerKeyBinding(noClipKeybind);

    MinecraftForge.EVENT_BUS.register(new RenderMultiToolAssist());
    MinecraftForge.EVENT_BUS.register(ModItems.railgun);
  }

  public void clientSetup(FMLClientSetupEvent event) {
    ClientRegistry.bindTileEntitySpecialRenderer(
        TileItemInterface.class, new ItemInterfaceRenderer());

    ClientRegistry.bindTileEntitySpecialRenderer(
        TilePlayerInterface.class, new PlayerInterfaceRenderer());
  }

  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event) {
    //    ModBlocks.registerModels();
    ModItems.registerModels();
  }

  @SubscribeEvent
  public static void modelBakeEvent(ModelBakeEvent event) {
    //      Minecraft.getInstance().getTextureMap().loadTexture(new ResourceLocation(MODID,
    // "item/multi_helmet"), new SimpleTexture(new ResourceLocation(MODID,
    // "textures/item/multi_helmet.png")));
    //    Minecraft.getInstance().getTextureMap().loadTexture();
    ModItems.customHelmet.getArmorModel(null, null, null, null);
    ModItems.customChestplate.getArmorModel(null, null, null, null);
    ModItems.customLeggins.getArmorModel(null, null, null, null);
    ModItems.customBoots.getArmorModel(null, null, null, null);
  }
}
