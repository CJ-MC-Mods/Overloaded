package com.cjm721.overloaded.storage.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class FastItemStackQueue implements IItemHandler {

  @Nonnull private final ItemStack[] storage;

  public FastItemStackQueue(int slots) {
    storage = new ItemStack[slots];
  }

  @Override
  public int getSlots() {
    return storage.length;
  }

  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {
    return storage[slot] == null ? ItemStack.EMPTY : storage[slot];
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (storage[slot] == null) {
      if (!simulate) {
        storage[slot] = stack;
      }
      return ItemStack.EMPTY;
    }

    if (ItemHandlerHelper.canItemStacksStack(storage[slot], stack)) {
      int newInternalCount = Math.min(storage[slot].getCount() + stack.getCount(), stack.getMaxStackSize());
      int returnCount = newInternalCount - storage[slot].getCount() - stack.getCount();
      if (!simulate) {
        storage[slot].setCount(newInternalCount);
      }

      if(returnCount == 0) {
        return ItemStack.EMPTY;
      }
      ItemStack returnStack = stack.copy();
      returnStack.setCount(returnCount);
      return returnStack;
    }
    return stack;
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    if(storage[slot] == null) {
      return ItemStack.EMPTY;
    }

    ItemStack internalStack = storage[slot];

    if(internalStack.getCount() <= amount) {
      if (!simulate) {
        storage[slot] = null;
      }
      return internalStack.copy();
    }

    ItemStack toReturn = internalStack.copy();
    toReturn.setCount(amount);

    if (!simulate) {
      internalStack.setCount(internalStack.getCount() - amount);
    }

    return toReturn;
  }

  @Override
  public int getSlotLimit(int slot) {
    return storage[slot] == null ? ItemStack.EMPTY.getMaxStackSize() : storage[slot].getMaxStackSize();
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return storage[slot] == null || ItemHandlerHelper.canItemStacksStack(storage[slot], stack);
  }
}
