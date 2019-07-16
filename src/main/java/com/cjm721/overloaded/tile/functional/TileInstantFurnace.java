package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.network.container.InstantFurnaceContainer;
import com.cjm721.overloaded.storage.energy.ForgeEnergyDataUpdateWrapper;
import com.cjm721.overloaded.storage.item.ProcessingItemStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
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
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileInstantFurnace extends LockableTileEntity implements IDataUpdate {

  @Nonnull private final ProcessingItemStorage itemStorage;
  @Nonnull private final ForgeEnergyDataUpdateWrapper energyStorage;
  @Nonnull private final LazyOptional<?> itemCapability;
  @Nonnull private final LazyOptional<?> energyCapability;

  public TileInstantFurnace() {
    super(ModTiles.instantFurnace);
    itemStorage = new ProcessingItemStorage(9,9);
    itemCapability = LazyOptional.of(() -> itemStorage);

    energyStorage = new ForgeEnergyDataUpdateWrapper(2000000000, 2000000000, 0, 0, this);
    energyCapability = LazyOptional.of(() -> energyStorage);
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
    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return itemCapability.cast();
    }

    if (cap == ENERGY) {
      return energyCapability.cast();
    }

    return super.getCapability(cap, side);
  }

  @Override
  public int getSizeInventory() {
    return itemStorage.getSlots();
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  @Nonnull
  public ItemStack getStackInSlot(int index) {
    return itemStorage.getStackInSlot(index);
  }

  @Override
  @Nonnull
  public ItemStack decrStackSize(int index, int count) {
    return itemStorage.extractItem(index, count, false);
  }

  @Override
  @Nonnull
  public ItemStack removeStackFromSlot(int index) {
    return itemStorage.extractItem(index, Integer.MAX_VALUE, false);
  }

  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {
    itemStorage.extractItem(index, Integer.MAX_VALUE, false);
    itemStorage.insertItem(index, stack, false);
  }

  @Override
  public boolean isUsableByPlayer(PlayerEntity player) {
    // TODO Do I want to make sure the player is nearby?
    return true;
  }

  @Override
  public void clear() {
    throw new RuntimeException("clear is called");
  }

  @Override
  public void read(CompoundNBT compound) {
    super.read(compound);
    if(compound.contains("ItemStorage")) {
      itemStorage.deserializeNBT((CompoundNBT) compound.get("ItemStorage"));
    }

    energyStorage.setEnergy(compound.getInt("EnergyStored"));
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    compound.put("ItemStorage", itemStorage.serializeNBT());
    compound.putInt("EnergyStored", energyStorage.getEnergyStored());
    return super.write(compound);
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }
}
