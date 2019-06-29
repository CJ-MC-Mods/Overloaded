package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.block.basic.AbstractModBlockFacing;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;

abstract class AbstractTileEntityFaceable extends TileEntity {

  AbstractTileEntityFaceable(TileEntityType<?> te) {
    super(te);
  }

  Direction getFacing() {
    return this.getBlockState().get(AbstractModBlockFacing.FACING);
  }
}
