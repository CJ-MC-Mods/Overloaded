package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

import static com.cjm721.overloaded.capabilities.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.BLOCK;

public class TileEnergyExtractor extends AbstractTileEntityFaceable {

  public TileEnergyExtractor(BlockPos pos, BlockState blockState) {
    super(ModTiles.energyExtractor.get(), pos, blockState);
  }

  public void tick() {
    if (getLevel().isClientSide) {
      return;
    }

    BlockPos me = this.getBlockPos();
    BlockEntity frontTE = getLevel().getBlockEntity(me.offset(getFacing().getNormal()));

    if (frontTE == null) {
      return;
    }

    IHyperHandlerEnergy optionalStorage = getLevel().getCapability(HYPER_ENERGY_HANDLER, frontTE.getBlockPos(), getFacing().getOpposite());

    if (optionalStorage == null) {
      return;
    }

      for (Direction facing : Direction.values()) {
      if (facing == getFacing()) continue;

      BlockEntity te = level.getBlockEntity(me.offset(facing.getNormal()));
      if (te == null) continue;

      IEnergyStorage optionalReceiver =
              level.getCapability(BLOCK, te.getBlockPos(), facing.getOpposite());

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
