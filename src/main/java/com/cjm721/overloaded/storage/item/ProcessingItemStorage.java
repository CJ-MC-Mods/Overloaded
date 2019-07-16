package com.cjm721.overloaded.storage.item;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class ProcessingItemStorage implements IItemHandler, INBTSerializable<CompoundNBT> {

  private final int inputSlots, outputSlots;
  @Nonnull NonNullList<ItemStack> input;
  @Nonnull NonNullList<ItemStack> output;

  public ProcessingItemStorage(int inputSlots, int outputSlots) {
    this.inputSlots = inputSlots;
    input = NonNullList.withSize(inputSlots, ItemStack.EMPTY);
    this.outputSlots = outputSlots;
    output = NonNullList.withSize(outputSlots, ItemStack.EMPTY);
  }

  @Override
  public int getSlots() {
    return inputSlots + outputSlots;
  }

  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {
    return slot < inputSlots ? input.get(slot) : output.get(slot - inputSlots);
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    return slot < inputSlots
        ? insertItem(input, slot, stack, simulate)
        : insertItem(output, slot - inputSlots, stack, simulate);
  }

  @Nonnull
  private ItemStack insertItem(
      NonNullList<ItemStack> inventory, int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (simulate) {
      return inventory.get(slot);
    } else {
      return inventory.set(slot, stack);
    }
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    if (!simulate) {
      return ItemStackHelper.getAndSplit(
          slot < inputSlots ? input : output, slot < inputSlots ? slot : slot - inputSlots, amount);
    }
    ItemStack toReturn =
        (slot < inputSlots ? input.get(slot) : output.get(slot - inputSlots)).copy();

    toReturn.setCount(Math.min(toReturn.getCount(), amount));
    return toReturn;
  }

  @Override
  public int getSlotLimit(int slot) {
    return slot < inputSlots
        ? input.get(slot).getMaxStackSize()
        : output.get(slot - inputSlots).getMaxStackSize();
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return slot < inputSlots + outputSlots;
  }

  @Override
  public CompoundNBT serializeNBT() {
    CompoundNBT storage = new CompoundNBT();
    CompoundNBT inputNBT = new CompoundNBT();
    CompoundNBT outputNBT = new CompoundNBT();
    ItemStackHelper.saveAllItems(inputNBT, input);
    ItemStackHelper.saveAllItems(outputNBT, output);
    storage.put("Input", inputNBT);
    storage.put("Output", outputNBT);
    return storage;
  }

  @Override
  public void deserializeNBT(CompoundNBT nbt) {
    if(nbt.contains("Input")) {
      ItemStackHelper.loadAllItems(nbt.getCompound("Input"), input);
    }

    if(nbt.contains("Output")) {
      ItemStackHelper.loadAllItems(nbt.getCompound("Output"), output);
    }
  }
}
