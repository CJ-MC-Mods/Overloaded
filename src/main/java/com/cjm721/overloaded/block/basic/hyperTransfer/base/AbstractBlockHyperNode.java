package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public abstract class AbstractBlockHyperNode extends ModBlock {

  AbstractBlockHyperNode(@Nonnull Properties materialIn) {
    super(materialIn.notSolid());
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nonnull
  protected abstract String getType();

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
  }
}
