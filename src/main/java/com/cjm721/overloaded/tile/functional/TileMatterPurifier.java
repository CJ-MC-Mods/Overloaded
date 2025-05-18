package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.Direction;
import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.util.LazyOptional;
import net.neoforged.energy.EnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.neoforged.energy.CapabilityEnergy.ENERGY;
import static net.neoforged.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileMatterPurifier extends BlockEntity implements ITickableTileEntity, IItemHandler {

//  private final FluidTank fluidStorage;
  private EnergyStorage energyStorage;
  private ItemStack stack;

  public TileMatterPurifier(BlockPos pos, BlockState blockState) {
    super(ModTiles.matterPurifier, pos, blockState);
//    fluidStorage = new FluidTank(Integer.MAX_VALUE);
    energyStorage = new EnergyStorage(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    stack = ItemStack.EMPTY;
  }

  @Override
  public void tick() {
    if (this.getLevel().isClientSide) {
      return;
    }

    if (stack.isEmpty()) return;

    float hardness = ((BlockItem) stack.getItem()).getBlock().defaultBlockState().getDestroySpeed(null, null);
    if (hardness <= 0) return;

    float ecFloat =
        OverloadedConfig.INSTANCE.purifierConfig.energyPerOperation
            + OverloadedConfig.INSTANCE.purifierConfig.energyPerHardness * hardness;
    int energyCost = Math.round(ecFloat);

    if (energyStorage.extractEnergy(energyCost, true) != energyCost) {
      return;
    }

    int createdFluid = Math.round(hardness);
//    FluidStack fluidStack = new FluidStack(ModFluids.pureMatterSource, createdFluid);
//    int storedFluid = fluidStorage.fill(fluidStack, false);
//
//    if (storedFluid != createdFluid) {
//      return;
//    }
//
//    stack.shrink(1);
//    if (stack.getCount() == 0) stack = ItemStack.EMPTY;
//
//    fluidStorage.fill(fluidStack, true);
//    energyStorage.extractEnergy(energyCost, false);
//    markDirty();
  }

  @Override
  public void load(@Nonnull BlockState state, @Nonnull CompoundTag compound) {
    super.load(state, compound);
//    fluidStorage.readFromNBT((CompoundTag) compound.get("Fluid"));
    energyStorage =
        new EnergyStorage(compound.getInt("Energy"), Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  @Override
  @Nonnull
  public CompoundTag save(CompoundTag compound) {
    CompoundTag fluid = new CompoundTag();

//    fluidStorage.writeToNBT(fluid);

    compound.put("Fluid", fluid);
    compound.putInt("Energy", energyStorage.getEnergyStored());

    return super.save(compound);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
//    if (capability == FLUID_HANDLER_CAPABILITY) return LazyOptional.of(() -> fluidStorage).cast();
    if (capability == ENERGY) return LazyOptional.of(() -> energyStorage).cast();
    if (capability == ITEM_HANDLER_CAPABILITY) return LazyOptional.of(() -> this).cast();

    return super.getCapability(capability, facing);
  }

  @Override
  public int getSlots() {
    return 1;
  }

  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {
    return stack;
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (this.stack.isEmpty()) {
      if (!simulate) this.stack = stack;

      return ItemStack.EMPTY;
    }

    if (ItemHandlerHelper.canItemStacksStack(this.stack, stack)) {
      int maxSize = this.stack.getMaxStackSize();
      int toTake = Math.min(maxSize - this.stack.getCount(), stack.getCount());

      if (!simulate) {
        this.stack.setCount(this.stack.getCount() + toTake);
      }

      ItemStack toReturn = stack.copy();
      toReturn.setCount(stack.getCount() - toTake);
      return toReturn;
    }

    return stack;
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    return ItemStack.EMPTY;
  }

  @Override
  public int getSlotLimit(int slot) {
    return this.stack.getMaxStackSize();
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return true;
  }
}
