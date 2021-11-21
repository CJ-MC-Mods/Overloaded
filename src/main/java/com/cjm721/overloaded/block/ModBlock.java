package com.cjm721.overloaded.block;

import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class ModBlock extends Block implements IModRegistrable {
  public static Properties getDefaultProperties() {
    return Properties.of(Material.STONE).strength(3);
  }

  protected ModBlock(@Nonnull Properties properties) {
    super(properties);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public abstract void registerModel();
}
