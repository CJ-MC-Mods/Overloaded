package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.client.render.tile.ItemInterfaceRenderer;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.network.menu.ModMenus;
import com.cjm721.overloaded.client.gui.InstantFurnaceScreen;
import com.cjm721.overloaded.tile.ModTiles;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

import static com.cjm721.overloaded.Overloaded.MODID;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientProxy {

  public static KeyMapping noClipKeybind;
  public static KeyMapping railGun100x;

  @SubscribeEvent
  public static void commonSetup(FMLCommonSetupEvent event) {

    noClipKeybind =
        new KeyMapping(
            "overloaded.key.noclip",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "overloaded.cat.key");
    railGun100x =
        new KeyMapping(
            "overloaded.key.railgun100x",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_CONTROL,
            "overloaded.cat.key");
    //    ClientRegistry.registerKeyBinding(noClipKeybind);
    //    ClientRegistry.registerKeyBinding(railGun100x);

    //    EVENT_BUS.register(new RenderMultiToolAssist());
    //    EVENT_BUS.register(ModItems.railgun);
  }

  @SubscribeEvent
  public static void clientSetup(FMLClientSetupEvent event) {
    //    ClientRegistry.bindTileEntityRenderer(ModTiles.itemInterface, ItemInterfaceRenderer::new);
    BlockEntityRenderers.register(
        ModTiles.itemInterface.get(), new ItemInterfaceRenderer.Provider());
    //    ClientRegistry.bindTileEntityRenderer(ModTiles.playerInterface,
    // PlayerInterfaceRenderer::new);
    //
    //
    ItemBlockRenderTypes.setRenderLayer(ModBlocks.itemInterface.get(), RenderType.translucent());
    ItemBlockRenderTypes.setRenderLayer(ModBlocks.playerInterface.get(), RenderType.translucent());
  }

  @SubscribeEvent
  public static void registerScreens(RegisterMenuScreensEvent event) {
    event.register(ModMenus.INSTANT_FURNACE.get(), InstantFurnaceScreen::new);
  }

  private void registerModels(ModelEvent.RegisterAdditional event) {
    //    OBJLoader.INSTANCE.addDomain(MODID);
    //    OBJLoader.INSTANCE.onResourceManagerReload(Minecraft.getInstance().getResourceManager());
    //    BlockResourcePack.INSTANCE.addDomain(MODID);
    //    BlockResourcePack.INSTANCE.inject();

    //    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_helmet"));
    //    ModelLoader.addSpecialModel(new ResourceLocation(MODID,
    // "item/armor/multi_chestplate_body"));
    //    ModelLoader.addSpecialModel(new ResourceLocation(MODID,
    // "item/armor/multi_chestplate_leftarm"));
    //    ModelLoader.addSpecialModel(new ResourceLocation(MODID,
    // "item/armor/multi_chestplate_rightarm"));
    //    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_belt"));
    //    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_left_leg"));
    //    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_right_leg"));
    //    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_left_boot"));
    //    ModelLoader.addSpecialModel(new ResourceLocation(MODID, "item/armor/multi_right_boot"));
    ModItems.registerModels();
  }

  private void modelBakeEvent(ModelEvent.RegisterAdditional event) {
    bakeModelAndPut(
        ModelResourceLocation.standalone(
            ResourceLocation.fromNamespaceAndPath(MODID, "block/remove_preview")),
        event);
  }

  private static void bakeModelAndPut(
      ModelResourceLocation raw, ModelEvent.RegisterAdditional event) {
    event.register(raw);

    //        IBakedModel bakedModel =
    //            unbakedModel.bake(
    //                event.getModelLoader(),
    //                ModelLoader.defaultTextureGetter(),
    //                ModelRotation.X0_Y0,
    //                baked);
    //
    //        event.getModelRegistry().put(baked, bakedModel);
  }
}
