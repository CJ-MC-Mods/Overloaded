package com.cjm721.overloaded.storage.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class SubsetItemHandlerWrapper implements IItemHandler {

  private final IItemHandler baseHandler;
  private final int offset;
  private final int length;

  public SubsetItemHandlerWrapper(@Nonnull IItemHandler handler, int offset, int length) {
    this.baseHandler = handler;
    this.offset = offset;
    this.length = length;
  }

  @Override
  public int getSlots() {
    return length;
  }

  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {
    if (slot < 0 || slot >= length) {
      return ItemStack.EMPTY;
    }
    
    return baseHandler.getStackInSlot(offset + slot);
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (slot < 0 || slot >= length) {
      return stack;
    }

    return baseHandler.insertItem(offset + slot, stack, simulate);
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    if (slot < 0 || slot >= length) {
      return ItemStack.EMPTY;
    }

    return baseHandler.extractItem(offset + slot, amount, simulate);
  }

  @Override
  public int getSlotLimit(int slot) {
    return baseHandler.getSlotLimit(offset + slot);
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return baseHandler.isItemValid(offset + slot, stack);
  }
}
