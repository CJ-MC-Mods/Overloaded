package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileCreativeGeneratorFE extends TileEntity
    implements ITickableTileEntity, IEnergyStorage {

  public TileCreativeGeneratorFE() {
    super(ModTiles.creativeGeneratorFE);
  }

  @Override
  public void tick() {
    if (getWorld().isRemote) return;

    BlockPos pos = this.getPos();
    for (Direction facing : Direction.values()) {
      TileEntity te = world.getTileEntity(pos.add(facing.getDirectionVec()));

      if (te == null) continue;

      te.getCapability(ENERGY, facing.getOpposite())
          .ifPresent(s -> s.receiveEnergy(Integer.MAX_VALUE, false));
//          .ifPresent(s -> s.receiveEnergy(1000000, false));
    }
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == ENERGY) {
      cap.orEmpty(cap, LazyOptional.of(() -> (T) this));
    }
    return super.getCapability(cap, side);
  }

  /**
   * Adds energy to the fluidStorage. Returns quantity of energy that was accepted.
   *
   * @param maxReceive Maximum amount of energy to be inserted.
   * @param simulate If TRUE, the insertion will only be simulated.
   * @return Amount of energy that was (or would have been, if simulated) accepted by the
   *     fluidStorage.
   */
  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    return 0;
  }

  /**
   * Removes energy from the fluidStorage. Returns quantity of energy that was removed.
   *
   * @param maxExtract Maximum amount of energy to be extracted.
   * @param simulate If TRUE, the extraction will only be simulated.
   * @return Amount of energy that was (or would have been, if simulated) extracted from the
   *     fluidStorage.
   */
  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    return maxExtract;
  }

  /** Returns the amount of energy currently stored. */
  @Override
  public int getEnergyStored() {
    return Integer.MAX_VALUE;
  }

  /** Returns the maximum amount of energy that can be stored. */
  @Override
  public int getMaxEnergyStored() {
    return Integer.MAX_VALUE;
  }

  /**
   * Returns if this fluidStorage can have energy extracted. If this is false, then any calls to
   * extractEnergy will return 0.
   */
  @Override
  public boolean canExtract() {
    return true;
  }

  /**
   * Used to determine if this fluidStorage can receive energy. If this is false, then any calls to
   * receiveEnergy will return 0.
   */
  @Override
  public boolean canReceive() {
    return false;
  }
}
