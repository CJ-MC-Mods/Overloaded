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
import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.cjm721.overloaded.tile.functional.TilePlayerInterface;
import com.cjm721.overloaded.util.ScrollEvent;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

import javax.annotation.Nonnull;

import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

  public KeyBinding noClipKeybind;
  public KeyBinding railGun100x;

  private static final ImmutableMap<String,String> itemModels = ImmutableMap.<String,String>builder()
      .put("models/item/multi_tool.obj", "multi_tool")
      .put("models/item/railgun.obj", "railgun")
      .put("models/item/ray_gun.obj", "ray_gun")
      .put("models/item/linkingcard.obj", "linking_card")
      .put("models/item/settings_editor.obj", "settings_editor")
      .put("models/item/energy_core.obj", "energy_core")
      .put("models/item/fluid_core.obj", "fluid_core")
      .put("models/item/item_core.obj", "item_core")
      .build();
  private static final ImmutableMap<String,String> objBlockModels = ImmutableMap.<String,String>builder()
      .put("models/block/hyper_energy_receiver.obj", "hyper_energy_receiver")
      .put("models/block/hyper_energy_sender.obj", "hyper_energy_sender")
      .put("models/block/hyper_fluid_sender.obj", "hyper_fluid_sender")
      .put("models/block/hyper_fluid_receiver.obj", "hyper_fluid_receiver")
      .put("models/block/hyper_item_sender.obj", "hyper_item_sender")
      .put("models/block/hyper_item_receiver.obj", "hyper_item_receiver")
      .put("models/block/infinite_water_source.obj", "infinite_water_source")
      .put("models/block/creative_generator.obj", "creative_generator")
      .build();

  private static final ImmutableMap<String,String> objModelBlockOnly = ImmutableMap.<String,String>builder()
      .put("models/block/player_interface.obj", "player_interface")
      .put("models/block/item_interface.obj", "item_interface")
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
    ClientRegistry.bindTileEntitySpecialRenderer(
        TileItemInterface.class, new ItemInterfaceRenderer());

    ClientRegistry.bindTileEntitySpecialRenderer(
        TilePlayerInterface.class, new PlayerInterfaceRenderer());

    ScreenManager.registerFactory(ModContainers.INSTANT_FURNACE, InstantFurnaceScreen::new);

    GLFWScrollCallback oldScroll =
        GLFW.glfwSetScrollCallback(
            Minecraft.getInstance().mainWindow.getHandle(),
            new GLFWScrollCallback() {
              @Override
              public void invoke(long window, double xoffset, double yoffset) {
                // Dummy one until can figure out how to get current instance without replacing
                // current one.
              }
            });

    GLFW.glfwSetScrollCallback(
        Minecraft.getInstance().mainWindow.getHandle(),
        new GLFWScrollCallback() {
          @Override
          public void invoke(long window, double xoffset, double yoffset) {
            if (!MinecraftForge.EVENT_BUS.post(new ScrollEvent(xoffset, yoffset))) {
              oldScroll.invoke(window, xoffset, yoffset);
            }
          }
        });
  }

  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event) {
    OBJLoader.INSTANCE.addDomain(MODID);
    OBJLoader.INSTANCE.onResourceManagerReload(Minecraft.getInstance().getResourceManager());
    BlockResourcePack.INSTANCE.addDomain(MODID);
    BlockResourcePack.INSTANCE.inject();

    ModItems.registerModels();
  }

  @SubscribeEvent
  public static void modelBakeEvent(ModelBakeEvent event) {
    bakeModelAndPut(
        new ResourceLocation(MODID, "block/remove_preview"),
        new ModelResourceLocation(MODID + ":remove_preview", ""),
        event);

    for(Map.Entry<String,String> entry : itemModels.entrySet()){
      bakeOBJModelAndPut(
          new ResourceLocation(MODID, entry.getKey()),
          new ModelResourceLocation(MODID + ":" + entry.getValue(), "inventory"),
          event,
          DefaultVertexFormats.ITEM
      );
    }

    for(Map.Entry<String,String> entry : objBlockModels.entrySet()){
      bakeOBJModelAndPutBlock(
          new ResourceLocation(MODID, entry.getKey()),
          entry.getValue(),
          event
      );
    }

    for(Map.Entry<String,String> entry : objModelBlockOnly.entrySet()){
      bakeOBJModelAndPut(
          new ResourceLocation(MODID, entry.getKey()),
          new ModelResourceLocation(MODID + ":" + entry.getValue(), ""),
          event,
          DefaultVertexFormats.ITEM
      );
    }


    ModelRenderOBJ.BAKERY = event.getModelLoader();

    ModItems.customHelmet.getArmorModel(null, null, null, null);
    ModItems.customChestplate.getArmorModel(null, null, null, null);
    ModItems.customLeggins.getArmorModel(null, null, null, null);
    ModItems.customBoots.getArmorModel(null, null, null, null);

    ModelRenderOBJ.BAKERY = null;
  }

  private static void bakeModelAndPut(
      ResourceLocation raw, ResourceLocation baked, ModelBakeEvent event) {
    IUnbakedModel unbakedModel =
        event.getModelLoader().getUnbakedModel(raw);

    IBakedModel bakedModel =
        unbakedModel.bake(
            event.getModelLoader(),
            ModelLoader.defaultTextureGetter(),
            new ISprite() {},
            DefaultVertexFormats.BLOCK);

    event
        .getModelRegistry()
        .put(baked, bakedModel);
  }

  private static void bakeOBJModelAndPut(
      ResourceLocation raw, ResourceLocation baked, ModelBakeEvent event, VertexFormat format) {
    try {
    IUnbakedModel unbakedModel = OBJLoader.INSTANCE.loadModel(raw);
    IBakedModel bakedModel =
        unbakedModel.bake(
            event.getModelLoader(),
            ModelLoader.defaultTextureGetter(),
            new ISprite() {},
            format);

    event
        .getModelRegistry()
        .put(baked, bakedModel);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void bakeOBJModelAndPutBlock(
      ResourceLocation raw, String resource, ModelBakeEvent event) {
    try {
      IUnbakedModel unbakedModel = OBJLoader.INSTANCE.loadModel(raw);
      IBakedModel bakedModel =
          unbakedModel.bake(
              event.getModelLoader(),
              ModelLoader.defaultTextureGetter(),
              new ISprite() {},
              DefaultVertexFormats.ITEM);

      event
          .getModelRegistry()
          .put(new ModelResourceLocation(MODID + ":" + resource, ""), bakedModel);

      OBJModel.OBJBakedModel handModel =
          (OBJModel.OBJBakedModel) unbakedModel.bake(
              event.getModelLoader(),
              ModelLoader.defaultTextureGetter(),
              new ISprite() {
                @Override
                public boolean isUvLock() {
                  return true;
                }
              },
              DefaultVertexFormats.ITEM);

      event
          .getModelRegistry()
          .put(new ModelResourceLocation(MODID + ":" + resource, "inventory"), handModel);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
