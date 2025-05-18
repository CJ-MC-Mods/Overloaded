package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public abstract class AbstractTileHyperStorage<T extends IHyperHandler> extends BlockEntity implements IDataUpdate {

  public AbstractTileHyperStorage(BlockEntityType<?> BlockEntityTypeIn, BlockPos pos, BlockState state) {
    super(BlockEntityTypeIn, pos, state);
  }

  @Nonnull
  public abstract T getStorage();
}
