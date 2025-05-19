package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockNetherStar extends ModBlock {

  public BlockNetherStar(Properties properties) {
    super(properties.sound(SoundType.METAL).strength(16384));
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location =
//        new ModelResourceLocation(new ResourceLocation(MODID, "nether_star_block"), null);
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
  }
}
