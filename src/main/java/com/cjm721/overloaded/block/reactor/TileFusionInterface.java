package com.cjm721.overloaded.block.reactor;

import com.cjm721.overloaded.block.ModBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

class TileFusionInterface extends TileEntity {


  public TileFusionInterface() {
    super(
        TileEntityType.Builder.of(TileFusionInterface::new, ModBlocks.fusionInterface)
            .build(null));
  }
}
