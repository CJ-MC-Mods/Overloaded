package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockNetherStar extends ModBlock {

  public BlockNetherStar() {
    super(Properties.create(Material.IRON).hardnessAndResistance(16384));
    setRegistryName("nether_star_block");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "nether_star_block"), null);
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
  }
}
