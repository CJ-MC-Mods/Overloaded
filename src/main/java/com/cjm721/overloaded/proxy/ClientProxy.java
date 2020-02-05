package com.cjm721.overloaded.proxy;

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
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
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

  private static final ImmutableMap<String, String> itemModels =
      ImmutableMap.<String, String>builder()
          .put("models/item/multi_tool.obj", "multi_tool")
          .put("models/item/railgun.obj", "railgun")
          .put("models/item/ray_gun.obj", "ray_gun")
          .put("models/item/linkingcard.obj", "linking_card")
          .put("models/item/settings_editor.obj", "settings_editor")
          .put("models/item/energy_core.obj", "energy_core")
          .put("models/item/fluid_core.obj", "fluid_core")
          .put("models/item/item_core.obj", "item_core")
          .build();

  private static final ImmutableMap<String, String> armorItemModels =
      ImmutableMap.<String, String>builder()
          .put("models/item/armor/multi_belt.obj", "multi_belt")
          .put("models/item/armor/multi_body.obj", "multi_body")
          .put("models/item/armor/multi_helmet.obj", "multi_helmet")
          .put("models/item/armor/multi_left_arm.obj", "multi_left_arm")
          .put("models/item/armor/multi_left_boot.obj", "multi_left_boot")
          .put("models/item/armor/multi_left_leg.obj", "multi_left_leg")
          .put("models/item/armor/multi_right_arm.obj", "multi_right_arm")
          .put("models/item/armor/multi_right_boot.obj", "multi_right_boot")
          .put("models/item/armor/multi_right_leg.obj", "multi_right_leg")
          .build();

  private static final ImmutableSet<String> objBlockModels =
      ImmutableSet.<String>builder()
          .add("almost_infinite_barrel")
          .add("almost_infinite_tank")
          .add("almost_infinite_capacitor")
          .add("true_infinite_barrel")
          .add("true_infinite_tank")
          .add("true_infinite_capacitor")
          .add("hyper_energy_receiver")
          .add("hyper_energy_sender")
          .add("hyper_fluid_sender")
          .add("hyper_fluid_receiver")
          .add("hyper_item_sender")
          .add("hyper_item_receiver")
          .add("infinite_water_source")
          .add("creative_generator")
          .add("player_interface")
          .add("item_interface")
          .build();

  @Override
  public void commonSetup(FMLCommonSetupEvent event) {
    super.commonSetup(event);

    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    FMLJavaModLoadingContext.get().getModEventBus().register(new ResizeableTextureGenerator());
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::registerModels);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::modelBakeEvent);

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

    ModItems.registerModels();
  }

  @SubscribeEvent
  public static void modelBakeEvent(ModelBakeEvent event) {
    bakeModelAndPut(
        new ResourceLocation(MODID, "block/remove_preview"),
        new ModelResourceLocation(MODID + ":remove_preview", ""),
        event);

    for (Map.Entry<String, String> entry : itemModels.entrySet()) {
      bakeOBJModelAndPut(
          new ResourceLocation(MODID, entry.getKey()),
          new ModelResourceLocation(MODID + ":" + entry.getValue(), "inventory"),
          event,
          DefaultVertexFormats.POSITION);
    }

    for (Map.Entry<String, String> entry : armorItemModels.entrySet()) {
      bakeOBJModelAndPut(
          new ResourceLocation(MODID, entry.getKey()),
          new ModelResourceLocation(MODID + ":" + entry.getValue(), "armor"),
          event,
          DefaultVertexFormats.POSITION);
    }

    for (String entry : objBlockModels) {
      setItemModelToBlock(entry, event);
    }

    ModelRenderOBJ.BAKERY = event.getModelLoader();

    ModItems.customHelmet.getArmorModel(null, null, null, null);
    ModItems.customLeggins.getArmorModel(null, null, null, null);
    ModItems.customBoots.getArmorModel(null, null, null, null);

    ModelRenderOBJ.BAKERY = null;
  }

  private static void bakeModelAndPut(
      ResourceLocation raw, ResourceLocation baked, ModelBakeEvent event) {
    IUnbakedModel unbakedModel = event.getModelLoader().getUnbakedModel(raw);

    //    IBakedModel bakedModel =
    //        unbakedModel.bake(
    //            event.getModelLoader(),
    //            ModelLoader.defaultTextureGetter(),
    //            ModelRotation.X0_Y0,
    //            baked);
    //
    //    event.getModelRegistry().put(baked, bakedModel);
  }

  private static void bakeOBJModelAndPut(
      ResourceLocation raw, ResourceLocation baked, ModelBakeEvent event, VertexFormat format) {
    try {
      //      OBJModel unbakedModel = OBJLoader.INSTANCE.loadModel(raw, true, false, false, false);
      //      IBakedModel bakedModel =
      //          unbakedModel.bake(
      //              event.getModelLoader(),
      //              ModelLoader.defaultTextureGetter(),
      //              ModelRotation.X0_Y0,
      //              baked);

      //      event.getModelRegistry().put(baked, bakedModel);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void setItemModelToBlock(String resource, ModelBakeEvent event) {
    try {
      String location = MODID + ":" + resource;
      IBakedModel blockModel =
          event.getModelRegistry().get(new ModelResourceLocation(location, ""));

      event.getModelRegistry().put(new ModelResourceLocation(location, "inventory"), blockModel);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
