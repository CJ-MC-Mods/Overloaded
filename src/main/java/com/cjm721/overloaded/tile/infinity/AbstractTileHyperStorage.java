package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

public abstract class AbstractTileHyperStorage<T extends IHyperHandler> extends TileEntity implements IDataUpdate {

  public AbstractTileHyperStorage(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  @Nonnull
  public abstract T getStorage();
}
