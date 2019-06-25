package com.cjm721.overloaded.tile.functional;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;

public abstract class AbstractTileEntityFaceable extends TileEntity {
  private Direction front;

  public AbstractTileEntityFaceable(TileEntityType<?> te) {
    super(te);
  }

  @Override
  public void read(CompoundNBT compound) {
    super.read(compound);

    this.front = Direction.byIndex(compound.getInt("Front"));
  }

  @Override
  @Nonnull
  public CompoundNBT write(CompoundNBT compound) {
    compound.putInt("Front", this.front.getIndex());

    return super.write(compound);
  }

  public AbstractTileEntityFaceable setFacing(Direction front) {
    this.front = front;
    return this;
  }

  Direction getFacing() {
    return front;
  }
}
