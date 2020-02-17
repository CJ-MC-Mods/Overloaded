package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.client.gui.InstantFurnaceScreen;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.entity.ModelRenderOBJ;
import com.cjm721.overloaded.client.render.item.RenderMultiToolAssist;
import com.cjm721.overloaded.client.render.tile.ItemInterfaceRenderer;
import com.cjm721.overloaded.client.render.tile.PlayerInterfaceRenderer;
import com.cjm721.overloaded.client.resource.BlockResourcePack;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.network.container.ModContainers;
import com.cjm721.overloaded.tile.ModTiles;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

  public KeyBinding noClipKeybind;
  public KeyBinding railGun100x;

  @Override
  public void commonSetup(FMLCommonSetupEvent event) {
    super.commonSetup(event);

    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    FMLJavaModLoadingContext.get().getModEventBus().register(new ResizeableTextureGenerator());
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::registerModels);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::modelBakeEvent);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::clientSetupEvent);

    noClipKeybind = new KeyBinding("overloaded.key.noclip", 'v', "overloaded.cat.key");
    railGun100x = new KeyBinding("overloaded.key.railgun100x", 341, "overloaded.cat.key");
    ClientRegistry.registerKeyBinding(noClipKeybind);
    ClientRegistry.registerKeyBinding(railGun100x);

    MinecraftForge.EVENT_BUS.register(new RenderMultiToolAssist());
    MinecraftForge.EVENT_BUS.register(ModItems.railgun);
  }

  public void clientSetup(FMLClientSetupEvent event) {
    ClientRegistry.bindTileEntityRenderer(ModTiles.itemInterface, ItemInterfaceRenderer::new);

    ClientRegistry.bindTileEntityRenderer(ModTiles.playerInterface, PlayerInterfaceRenderer::new);

    ScreenManager.registerFactory(ModContainers.INSTANT_FURNACE, InstantFurnaceScreen::new);
  }

  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event) {
    //    OBJLoader.INSTANCE.addDomain(MODID);
    //    OBJLoader.INSTANCE.onResourceManagerReload(Minecraft.getInstance().getResourceManager());
    BlockResourcePack.INSTANCE.addDomain(MODID);
//    BlockResourcePack.INSTANCE.inject();

    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_chestplate_body"));
    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_chestplate_leftarm"));
    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_chestplate_rightarm"));
    ModItems.registerModels();
  }

  @SubscribeEvent
  public static void modelBakeEvent(ModelBakeEvent event) {
    bakeModelAndPut(
        new ResourceLocation(MODID, "block/remove_preview"),
        new ModelResourceLocation(MODID + ":remove_preview", ""),
        event);
  }

  @SubscribeEvent
  public static void clientSetupEvent(FMLClientSetupEvent event) {
    RenderTypeLookup.setRenderLayer(ModBlocks.itemInterface, RenderType.translucent());
    RenderTypeLookup.setRenderLayer(ModBlocks.playerInterface, RenderType.translucent());
  }

  private static void bakeModelAndPut(
      ResourceLocation raw, ResourceLocation baked, ModelBakeEvent event) {
    IUnbakedModel unbakedModel = event.getModelLoader().getUnbakedModel(raw);

        IBakedModel bakedModel =
            unbakedModel.bakeModel(
                event.getModelLoader(),
                ModelLoader.defaultTextureGetter(),
                ModelRotation.X0_Y0,
                baked);

        event.getModelRegistry().put(baked, bakedModel);
  }
}
