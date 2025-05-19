package com.cjm721.overloaded.storage.item;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.storage.stacks.intint.LongItemStack;
import com.cjm721.overloaded.util.IDataUpdate;
import com.cjm721.overloaded.util.NumberUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.util.NumberUtil.addToMax;

public class LongItemStorage
    implements IItemHandler, IHyperHandlerItem, INBTSerializable<CompoundTag> {

  @Nonnull private final IDataUpdate dataUpdate;
  @Nonnull private LongItemStack longItemStack;

  public LongItemStorage(@Nonnull IDataUpdate dataUpdate) {
    this.dataUpdate = dataUpdate;
    longItemStack = new LongItemStack(ItemStack.EMPTY, 0);
  }

  @Override
  public int getSlots() {
    return OverloadedConfig.INSTANCE.specialConfig.infinityBarrelAdditionalSlot ? 2 : 1;
  }

  @Override
  @Nonnull
  public ItemStack getStackInSlot(int slot) {
    if (slot != 0) {
      return ItemStack.EMPTY;
    }
    if (!longItemStack.getItemStack().isEmpty()) {
      longItemStack
          .getItemStack()
          .setCount((int) Math.min(Integer.MAX_VALUE, longItemStack.getAmount()));
      return longItemStack.getItemStack();
    }
    return ItemStack.EMPTY;
  }

  @Override
  @Nonnull
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    LongItemStack result = give(new LongItemStack(stack, stack.getCount()), !simulate);

    if (result.getAmount() == 0) {
      return ItemStack.EMPTY;
    }

    ItemStack toReturn = stack.copy();
    toReturn.setCount(result.getAmount().intValue());

    return toReturn;
  }

  @Override
  @Nonnull
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    LongItemStack result = take(new LongItemStack(ItemStack.EMPTY, amount), !simulate);

    if (result.getAmount() == 0L) {
      return ItemStack.EMPTY;
    }

    ItemStack toReturn = result.getItemStack().copy();
    toReturn.setCount(result.getAmount().intValue());

    return toReturn;
  }

  @Override
  public int getSlotLimit(int slot) {
    return Integer.MAX_VALUE;
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return true;
  }

  @Override
  public CompoundTag serializeNBT(HolderLookup.Provider provider) {
    CompoundTag compound = new CompoundTag();
    if (!longItemStack.getItemStack().isEmpty()) {
      ItemStack stack = longItemStack.getItemStack();
      stack.setCount(1);
      compound.put("Item", stack.save(provider));
      compound.putLong("Count", longItemStack.getAmount());
    }

    return compound;
  }

  @Override
  public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compound) {
    ItemStack storedItem =
        compound.contains("Item") ? ItemStack.parse(provider, compound.get("Item")).orElse(null) : null;
    if (storedItem != null) {
      long storedAmount = compound.contains("Count") ? compound.getLong("Count") : 0L;
      longItemStack = new LongItemStack(storedItem, storedAmount);
    }
  }

  @Override
  @Nonnull
  public LongItemStack status() {
    return longItemStack;
  }

  @Nonnull
  @Override
  public LongItemStack give(@Nonnull LongItemStack stack, boolean doAction) {
    if (longItemStack.getItemStack().isEmpty()) {
      if (doAction) {
        longItemStack = new LongItemStack(stack.getItemStack(), stack.getAmount());
        dataUpdate.dataUpdated();
      }
      return LongItemStack.EMPTY_STACK;
    }

    if (ItemStack.isSameItemSameComponents(longItemStack.getItemStack(), stack.getItemStack())) {
      NumberUtil.AddReturn<Long> result = addToMax(longItemStack.getAmount(), stack.getAmount());
      if (doAction) {
        longItemStack.setAmount(result.result);
        dataUpdate.dataUpdated();
      }
      return new LongItemStack(stack.getItemStack(), result.overflow);
    }

    return stack;
  }

  @Nonnull
  @Override
  public LongItemStack take(@Nonnull LongItemStack stack, boolean doAction) {
    if (longItemStack.getItemStack().isEmpty()) return LongItemStack.EMPTY_STACK;

    long result = Math.min(stack.getAmount(), longItemStack.getAmount());
    LongItemStack toReturn = new LongItemStack(longItemStack.getItemStack(), result);
    if (doAction) {
      longItemStack.removeAmount(result);

      if (longItemStack.getAmount() == 0L) longItemStack.setItemStack(ItemStack.EMPTY);
      dataUpdate.dataUpdated();
    }

    return toReturn;
  }
}
