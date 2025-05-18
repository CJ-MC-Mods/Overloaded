package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public abstract class AbstractBlockHyperNode extends ModBlock implements EntityBlock {

  AbstractBlockHyperNode(@Nonnull Properties materialIn) {
    super(materialIn.noOcclusion());
  }

  @Nonnull
  protected abstract String getType();

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
  }
}
