package com.cjm721.overloaded.item.crafting;

import com.cjm721.overloaded.item.ModItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ItemFluidCore extends ModItem {

  public ItemFluidCore(Properties properties) {
    super(properties.stacksTo(64));
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
//    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/fluid_core.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }
}