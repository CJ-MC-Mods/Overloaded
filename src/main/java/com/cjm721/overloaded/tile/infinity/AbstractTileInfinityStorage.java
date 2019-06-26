package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class AbstractTileInfinityStorage extends TileEntity implements IDataUpdate {

  public AbstractTileInfinityStorage(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }
}
