package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.item.RenderMultiToolAssist;
import com.cjm721.overloaded.client.render.tile.ItemInterfaceRenderer;
import com.cjm721.overloaded.client.render.tile.PlayerInterfaceRenderer;
import com.cjm721.overloaded.client.resource.BlockResourcePack;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.cjm721.overloaded.tile.functional.TilePlayerInterface;
import com.cjm721.overloaded.util.ScrollEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

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
            if (!MinecraftForge.EVENT_BUS
                .post(new ScrollEvent(xoffset, yoffset))) {
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
    try {
      IUnbakedModel removePreview =
          event
              .getModelLoader()
              .getUnbakedModel(new ResourceLocation(MODID, "block/remove_preview"));

      IBakedModel removePreviewBaked =
          removePreview.bake(
              event.getModelLoader(),
              ModelLoader.defaultTextureGetter(),
              new ISprite() {},
              DefaultVertexFormats.BLOCK);

      event
          .getModelRegistry()
          .put(new ModelResourceLocation(MODID + ":remove_preview", ""), removePreviewBaked);
    } catch (Exception e) {
      e.printStackTrace();
    }

    ModItems.customHelmet.getArmorModel(null, null, null, null);
    ModItems.customChestplate.getArmorModel(null, null, null, null);
    ModItems.customLeggins.getArmorModel(null, null, null, null);
    ModItems.customBoots.getArmorModel(null, null, null, null);
  }
}
