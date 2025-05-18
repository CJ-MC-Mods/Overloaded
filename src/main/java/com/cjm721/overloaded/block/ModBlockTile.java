package com.cjm721.overloaded.block;

import net.minecraft.world.level.block.EntityBlock;

import javax.annotation.Nonnull;

public abstract class ModBlockTile extends ModBlock implements EntityBlock {
  protected ModBlockTile(@Nonnull Properties properties) {
    super(properties);
  }
}
