package com.cjm721.overloaded.block.reactor;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockReactorCasing extends ModBlock {

  public BlockReactorCasing() {
    super(getDefaultProperties());
    setRegistryName("reactor_casing");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
  }
}
