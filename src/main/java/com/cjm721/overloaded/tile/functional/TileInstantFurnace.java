package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.network.container.InstantFurnaceContainer;
import com.cjm721.overloaded.storage.crafting.EnergyInventoryBasedRecipeProcessor;
import com.cjm721.overloaded.storage.crafting.FurnaceProcessor;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullFunction;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileInstantFurnace extends LockableTileEntity implements IDataUpdate {

  @Nonnull private final FurnaceProcessor processingStorage;
  @Nonnull private final LazyOptional<FurnaceProcessor> capability;

  public TileInstantFurnace() {
    super(ModTiles.instantFurnace);

    processingStorage = new FurnaceProcessor(this::getLevel, Integer.MAX_VALUE, 9, this);
    capability = LazyOptional.of(() -> processingStorage);
  }

  @Override
  @Nonnull
  protected ITextComponent getDefaultName() {
    return new StringTextComponent("Instant Furnace");
  }

  @Override
  @Nonnull
  protected Container createMenu(int id, @Nonnull PlayerInventory playerInventory) {
    return new InstantFurnaceContainer(id, playerInventory, this);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if ( cap == ENERGY) {
      return capability.cast();
    }

    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      if (side == null) {
        side = Direction.NORTH;
      }
      switch (side) {
        case UP:
          return capability.lazyMap(FurnaceProcessor::inputIItemHandler).cast();
        case NORTH:
        case EAST:
        case SOUTH:
        case WEST:
          return capability.cast();
        case DOWN:
          return capability.lazyMap(FurnaceProcessor::outputIItemHandler).cast();
      }
    }

    return super.getCapability(cap, side);
  }

  @Override
  public int getContainerSize() {
    return processingStorage.getSlots();
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  @Nonnull
  public ItemStack getItem(int index) {
    return processingStorage.getStackInSlot(index);
  }

  @Override
  @Nonnull
  public ItemStack removeItem(int index, int count) {
    return processingStorage.extractItem(index, count, false);
  }

  @Override
  @Nonnull
  public ItemStack removeItemNoUpdate(int index) {
    return processingStorage.extractItem(index, Integer.MAX_VALUE, false);
  }

  @Override
  public void setItem(int index, @Nonnull ItemStack stack) {
    processingStorage.setItem(index, stack);
  }

  @Override
  public boolean stillValid(@Nonnull PlayerEntity player) {
    // TODO Do I want to make sure the player is nearby?
    return true;
  }

  @Override
  public void clearContent() {
    throw new RuntimeException("clear is called");
  }

  @Override
  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
    super.load(state, compound);
    if (compound.contains("Processor")) {
      processingStorage.deserializeNBT((CompoundNBT) compound.get("Processor"));
    }
  }

  @Override
  @Nonnull
  public CompoundNBT save(CompoundNBT compound) {
    compound.put("Processor", processingStorage.serializeNBT());
    return super.save(compound);
  }

  @Override
  public void dataUpdated() {
    setChanged();
  }
}
