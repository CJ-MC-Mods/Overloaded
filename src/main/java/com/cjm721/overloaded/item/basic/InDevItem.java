package com.cjm721.overloaded.item.basic;

import com.cjm721.overloaded.item.ModItem;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.item.Item.Properties;

public class InDevItem extends ModItem {

  public InDevItem(String name) {
    super(new Properties());
    setRegistryName(name);
    //        setTranslationKey(name);
    //        setCreativeTab(OverloadedCreativeTabs.TECH);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);
  }
}
