package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

import static net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.BLOCK;

public class TileCreativeGeneratorFE extends BlockEntity
    implements IEnergyStorage {

  public TileCreativeGeneratorFE(BlockPos pos, BlockState state) {
    super(ModTiles.creativeGeneratorFE.get(), pos, state);
  }

  public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState blockState, T t) {
    if (level.isClientSide) return;

    for (Direction facing : Direction.values()) {
      IEnergyStorage cap = level.getCapability(BLOCK, pos.offset(facing.getNormal()), facing.getOpposite());

      if (cap == null) continue;
      cap.receiveEnergy(Integer.MAX_VALUE, false);
    }
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
