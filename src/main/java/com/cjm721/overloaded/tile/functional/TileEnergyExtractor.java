package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.energy.IEnergyStorage;

import static com.cjm721.overloaded.capabilities.CapabilityHyperEnergy.BLOCK_HYPER_ENERGY_HANDLER;
import static net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.BLOCK;

public class TileEnergyExtractor extends AbstractTileEntityFaceable {

  public TileEnergyExtractor(BlockPos pos, BlockState blockState) {
    super(ModTiles.energyExtractor.get(), pos, blockState);
  }




  public static <T extends BlockEntity> void tick(Level level, BlockPos me, BlockState blockState, T t) {
  if (level.isClientSide) {
      return;
    }

    Direction facing = blockState.getValue(BlockStateProperties.FACING);
    BlockEntity frontTE = level.getBlockEntity(me.offset(facing.getNormal()));

    if (frontTE == null) {
      return;
    }

    IHyperHandlerEnergy optionalStorage = level.getCapability(BLOCK_HYPER_ENERGY_HANDLER, frontTE.getBlockPos(), facing.getOpposite());

    if (optionalStorage == null) {
      return;
    }

      for (Direction direction : Direction.values()) {
      if (direction == facing) continue;

      BlockEntity te = level.getBlockEntity(me.offset(direction.getNormal()));
      if (te == null) continue;

      IEnergyStorage optionalReceiver =
              level.getCapability(BLOCK, te.getBlockPos(), direction.getOpposite());

      if (optionalReceiver == null)
        return;

      LongEnergyStack energy = optionalStorage.take(new LongEnergyStack(Long.MAX_VALUE), false);
      if (energy.getAmount() == 0L) return;
      if (!optionalReceiver.canReceive()) return;

      int acceptedAmount =
              optionalReceiver.receiveEnergy((int) Math.min(energy.getAmount(), Integer.MAX_VALUE), true);
      if (acceptedAmount != 0) {
        LongEnergyStack actualTaken = optionalStorage.take(new LongEnergyStack(acceptedAmount), true);
        optionalReceiver.receiveEnergy(
            (int) Math.min(actualTaken.getAmount(), Integer.MAX_VALUE), false);
      }
    }
  }
}
