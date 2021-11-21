package com.cjm721.overloaded.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class NBTHelper {

  @Nonnull
  public static ListNBT serializeItems(List<ItemStack> list) {
    ListNBT listnbt = new ListNBT();

    for (ItemStack stack : list) {
      listnbt.add(stack.serializeNBT());
    }

    return listnbt;
  }

  @Nonnull
  public static List<ItemStack> deserializeItems(ListNBT tags) {
    List<ItemStack> toReturn = new ArrayList<>();
    for (INBT tag : tags) {
      toReturn.add(ItemStack.of(((CompoundNBT) tag)));
    }

    return toReturn;
  }
}
