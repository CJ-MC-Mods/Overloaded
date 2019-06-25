package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class TileInfiniteWaterSource extends TileEntity implements IFluidHandler {

  //  private static final IFluidTankProperties[] fluidTankProperties =
  //      new IFluidTankProperties[] {
  //        new FluidTankProperties(
  //            FluidRegistry.getFluidStack("water", Integer.MAX_VALUE), Integer.MAX_VALUE, false,
  // true)
  //      };

  public TileInfiniteWaterSource() {
    super(ModTiles.infiniteWaterSource);
  }

  @Override
  public IFluidTankProperties[] getTankProperties() {
    return new IFluidTankProperties[0]; // fluidTankProperties;
  }

  @Override
  public int fill(FluidStack resource, boolean doFill) {
    return 0;
  }

  @Nullable
  @Override
  public FluidStack drain(@Nonnull FluidStack resource, boolean doDrain) {
    //    if (resource.isFluidEqual(FluidRegistry.getFluidStack("water", 0)))
    //      return drain(resource.amount, doDrain);
    return new FluidStack(resource.getFluid(), 0);
  }

  @Nullable
  @Override
  public FluidStack drain(int maxDrain, boolean doDrain) {
    //    return FluidRegistry.getFluidStack("water", maxDrain);
    return null;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
    if (capability == FLUID_HANDLER_CAPABILITY) {
      return LazyOptional.of(() -> this).cast();
    }
    return super.getCapability(capability, facing);
  }
}
