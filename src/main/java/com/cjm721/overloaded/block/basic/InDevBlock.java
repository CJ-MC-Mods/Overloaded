package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.client.renderer.model.ModelResourceLocation;

import javax.annotation.Nonnull;

public class InDevBlock extends ModBlock {

  @Nonnull private final String name;

  public InDevBlock(@Nonnull String name) {
    super(getDefaultProperties());
    this.name = name;
    setRegistryName(name);
  }

  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
  }
}
