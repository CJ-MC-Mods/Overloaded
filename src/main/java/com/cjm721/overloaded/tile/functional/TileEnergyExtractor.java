package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.storage.LongEnergyStack;
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

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileEnergyExtractor extends AbstractTileEntityFaceable implements ITickableTileEntity {

  public TileEnergyExtractor() {
    super(ModTiles.energyExtractor);
  }

  @Override
  public void tick() {
    if (getWorld().isRemote) {
      return;
    }

    BlockPos me = this.getPos();
    TileEntity frontTE = getWorld().getTileEntity(me.add(getFacing().getDirectionVec()));

    LazyOptional<IHyperHandlerEnergy> optionalStorage =
        frontTE.getCapability(HYPER_ENERGY_HANDLER, getFacing().getOpposite());

    if (!optionalStorage.isPresent()) {
      return;
    }
    IHyperHandlerEnergy storage = optionalStorage.orElse(null);

    for (Direction facing : Direction.values()) {
      LongEnergyStack energy = storage.take(new LongEnergyStack(Long.MAX_VALUE), false);
      if (energy.getAmount() == 0L) return;

      if (facing == getFacing()) continue;

      TileEntity te = world.getTileEntity(me.add(facing.getDirectionVec()));
      if (te == null) continue;

      LazyOptional<IEnergyStorage> optionalReceiver =
          te.getCapability(ENERGY, facing.getOpposite());
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
      return ENERGY.orEmpty(ENERGY, LazyOptional.of(ForgeEnergyZero::new)).cast();
    }
    return super.getCapability(cap, side);
  }
}
