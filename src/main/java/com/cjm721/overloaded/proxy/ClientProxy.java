package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.entity.RenderMultiBoots;
import com.cjm721.overloaded.client.render.entity.RenderMultiChestplate;
import com.cjm721.overloaded.client.render.entity.RenderMultiHelmet;
import com.cjm721.overloaded.client.render.entity.RenderMultiLeggings;
import com.cjm721.overloaded.client.render.item.RenderMultiToolAssist;
import com.cjm721.overloaded.client.resource.BlockResourcePack;
import com.cjm721.overloaded.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.cjm721.overloaded.Overloaded.MODID;

@OnlyIn(Dist.CLIENT)
//@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientProxy extends CommonProxy {

  public KeyBinding noClipKeybind;

  @Override
  public void commonSetup(FMLCommonSetupEvent event) {
    super.commonSetup(event);

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

  @SubscribeEvent
  public static void textureStitch(TextureStitchEvent.Pre event) {
//    TextureAtlasSprite result = event.getMap().getSprite(new ResourceLocation(MODID, "item/multi_helmet"));
//    System.out.println(result);

    event.getMap().getSprite(new ResourceLocation(MODID, "item/multi_helmet"));
    event.getMap().getSprite(new ResourceLocation(MODID, "textures/item/multi_helmet"));
    event.getMap().getSprite(new ResourceLocation(MODID, "item/multi_helmet.png"));
    event.getMap().getSprite(new ResourceLocation(MODID, "textures/item/multi_helmet.png"));
  }

  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event) {
//    ModBlocks.registerModels();
    ModItems.registerModels();
  }

  @SubscribeEvent
  public static void modelBakeEvent(ModelBakeEvent event) {
//      Minecraft.getInstance().getTextureMap().loadTexture(new ResourceLocation(MODID, "item/multi_helmet"), new SimpleTexture(new ResourceLocation(MODID, "textures/item/multi_helmet.png")));
//    Minecraft.getInstance().getTextureMap().loadTexture();
    ModItems.customHelmet.getArmorModel(null,null,null,null);
    ModItems.customChestplate.getArmorModel(null,null,null,null);
    ModItems.customLeggins.getArmorModel(null,null,null,null);
    ModItems.customBoots.getArmorModel(null,null,null,null);
  }
}
