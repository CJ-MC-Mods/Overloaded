package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.network.container.InstantFurnaceContainer;
import com.cjm721.overloaded.storage.item.ProcessingItemStorage;
import com.cjm721.overloaded.tile.ModTiles;
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

public class TileInstanceFurnace extends LockableTileEntity {

  @Nonnull private final ProcessingItemStorage itemStorage;
  @Nonnull private final LazyOptional<?> capability;

  public TileInstanceFurnace() {
    super(ModTiles.instantFurnace);
    itemStorage = new ProcessingItemStorage(9,9);
    capability = LazyOptional.of(() -> itemStorage);
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
      markDirty();
      return capability.cast();
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

  /**
   * Don't rename this method to canInteractWith due to conflicts with Container
   *
   * @param player
   */
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
    if(compound.contains("Storage")) {
      itemStorage.deserializeNBT((CompoundNBT) compound.get("Storage"));
    }
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    CompoundNBT storage = itemStorage.serializeNBT();
    compound.put("Storage", storage);
    return super.write(compound);
  }
}
