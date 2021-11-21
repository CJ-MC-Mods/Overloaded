package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.ForgeEnergyZero;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
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

import static com.cjm721.overloaded.capabilities.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileEnergyExtractor extends AbstractTileEntityFaceable implements ITickableTileEntity {

  public TileEnergyExtractor() {
    super(ModTiles.energyExtractor);
  }

  @Override
  public void tick() {
    if (getLevel().isClientSide) {
      return;
    }

    BlockPos me = this.getBlockPos();
    TileEntity frontTE = getLevel().getBlockEntity(me.offset(getFacing().getNormal()));

    if (frontTE == null) {
      return;
    }

    LazyOptional<IHyperHandlerEnergy> optionalStorage =
        frontTE.getCapability(HYPER_ENERGY_HANDLER, getFacing().getOpposite());

    if (!optionalStorage.isPresent()) {
      return;
    }
    IHyperHandlerEnergy storage = optionalStorage.orElse(null);

    for (Direction facing : Direction.values()) {
      if (facing == getFacing()) continue;

      TileEntity te = level.getBlockEntity(me.offset(facing.getNormal()));
      if (te == null) continue;

      LazyOptional<IEnergyStorage> optionalReceiver =
          te.getCapability(ENERGY, facing.getOpposite());

      LongEnergyStack energy = storage.take(new LongEnergyStack(Long.MAX_VALUE), false);
      if (energy.getAmount() == 0L) return;

      optionalReceiver.ifPresent(
          receiver -> {
            if (!receiver.canReceive()) return;

            int acceptedAmount =
                receiver.receiveEnergy((int) Math.min(energy.getAmount(), Integer.MAX_VALUE), true);
            if (acceptedAmount != 0) {
              LongEnergyStack actualTaken = storage.take(new LongEnergyStack(acceptedAmount), true);
              receiver.receiveEnergy(
                  (int) Math.min(actualTaken.getAmount(), Integer.MAX_VALUE), false);
            }
          });
    }
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == ENERGY) {
      return LazyOptional.of(ForgeEnergyZero::new).cast();
    }
    return super.getCapability(cap, side);
  }
}
