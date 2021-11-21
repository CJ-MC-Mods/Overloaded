package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.client.gui.InstantFurnaceScreen;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.item.RenderMultiToolAssist;
import com.cjm721.overloaded.client.render.tile.ItemInterfaceRenderer;
import com.cjm721.overloaded.client.render.tile.PlayerInterfaceRenderer;
import com.cjm721.overloaded.client.resource.BlockResourcePack;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.network.container.ModContainers;
import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.model.ModelRotation;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.cjm721.overloaded.Overloaded.MODID;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

  public KeyBinding noClipKeybind;
  public KeyBinding railGun100x;

  @Override
  public void registerEvents() {
    super.registerEvents();

    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerModels);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::modelBakeEvent);

    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    FMLJavaModLoadingContext.get().getModEventBus().register(new ResizeableTextureGenerator());
  }

  @Override
  public void commonSetup(FMLCommonSetupEvent event) {
    super.commonSetup(event);

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

    ScreenManager.register(ModContainers.INSTANT_FURNACE, InstantFurnaceScreen::new);

    RenderTypeLookup.setRenderLayer(ModBlocks.itemInterface, RenderType.translucent());
    RenderTypeLookup.setRenderLayer(ModBlocks.playerInterface, RenderType.translucent());
  }

  private void registerModels(ModelRegistryEvent event) {
    //    OBJLoader.INSTANCE.addDomain(MODID);
    //    OBJLoader.INSTANCE.onResourceManagerReload(Minecraft.getInstance().getResourceManager());
    BlockResourcePack.INSTANCE.addDomain(MODID);
//    BlockResourcePack.INSTANCE.inject();

    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_helmet"));
    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_chestplate_body"));
    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_chestplate_leftarm"));
    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_chestplate_rightarm"));
    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_belt"));
    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_left_leg"));
    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_right_leg"));
    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_left_boot"));
    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_right_boot"));
    ModItems.registerModels();
  }

  private void modelBakeEvent(ModelBakeEvent event) {
    bakeModelAndPut(
        new ResourceLocation(MODID, "block/remove_preview"),
        new ModelResourceLocation(MODID + ":remove_preview", ""),
        event);
  }

  private static void bakeModelAndPut(
      ResourceLocation raw, ResourceLocation baked, ModelBakeEvent event) {
    IUnbakedModel unbakedModel = event.getModelLoader().getModel(raw);

        IBakedModel bakedModel =
            unbakedModel.bake(
                event.getModelLoader(),
                ModelLoader.defaultTextureGetter(),
                ModelRotation.X0_Y0,
                baked);

        event.getModelRegistry().put(baked, bakedModel);
  }
}
