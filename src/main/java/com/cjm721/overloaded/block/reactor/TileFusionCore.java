package com.cjm721.overloaded.block.reactor;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.storage.fluid.LongFluidStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class TileFusionCore extends TileEntity {

  private LongFluidStorage fluidStorage;
  private LongEnergyStorage energyStorage;

  public TileFusionCore() {
    super(TileEntityType.Builder.of(TileFusionCore::new, ModBlocks.fusionCore).build(null));
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
    if (facing != null && facing.getAxis().isVertical()) {
      if (capability == FLUID_HANDLER_CAPABILITY) {
        return LazyOptional.of(() -> fluidStorage).cast();
      }
    } else {
      if (capability == ENERGY) {
        return LazyOptional.of(() -> energyStorage).cast();
      }
    }
    return super.getCapability(capability, facing);
  }
}
