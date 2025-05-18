package com.cjm721.overloaded.block;

import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


import javax.annotation.Nonnull;

import static net.minecraft.world.level.block.Blocks.STONE;


public abstract class ModBlock extends Block implements IModRegistrable {
  public static Properties  getDefaultProperties() {
    return Properties.ofFullCopy(STONE).strength(3);
  }

  protected ModBlock(@Nonnull Properties  properties) {
    super(properties);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public abstract void registerModel();
}
