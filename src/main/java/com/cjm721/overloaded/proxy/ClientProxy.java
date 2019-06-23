package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.item.RenderMultiToolAssist;
import com.cjm721.overloaded.client.resource.BlockResourcePack;
import com.cjm721.overloaded.item.ModItems;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.cjm721.overloaded.Overloaded.MODID;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

  public KeyBinding noClipKeybind;

  @Override
  public void commonSetup(FMLCommonSetupEvent event) {
    super.commonSetup(event);

    OBJLoader.INSTANCE.addDomain(MODID);
    MinecraftForge.EVENT_BUS.register(new ResizeableTextureGenerator());

    BlockResourcePack.INSTANCE.addDomain("overloaded");
    BlockResourcePack.INSTANCE.inject();

    //    if (Overloaded.cachedConfig.specialConfig.noClipRenderFix)
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

    //    noClipKeybind =
    //        new KeyBinding("overloaded.key.noclip", Keyboard.KEY_V,
    // "overloaded.cat.key.multiarmor");
    //    ClientRegistry.registerKeyBinding(noClipKeybind);

    MinecraftForge.EVENT_BUS.register(new RenderMultiToolAssist());
  }

  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event) {
    ModBlocks.registerModels();
    ModItems.registerModels();
  }
}
