package com.cjm721.overloaded.block.tile;

import com.cjm721.overloaded.block.ModBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileTeamLoader extends TileEntity {
  public TileTeamLoader() {
    super(TileEntityType.Builder.create(TileTeamLoader::new, ModBlocks.teamLoader).build(null));
  }
}
