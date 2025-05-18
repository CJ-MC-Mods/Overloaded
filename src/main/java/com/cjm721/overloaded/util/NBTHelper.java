package com.cjm721.overloaded.util;

import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class NBTHelper {

  @Nonnull
  public static ListTag serializeItems(List<ItemStack> list) {
    ListTag listnbt = new ListTag();
    for (ItemStack stack : list) {
//      listnbt.add(stack.save());
    }

    return listnbt;
  }

  @Nonnull
  public static List<ItemStack> deserializeItems(ListTag tags) {
    List<ItemStack> toReturn = new ArrayList<>();
//    for (INBT tag : tags) {
//      toReturn.add(ItemStack.of(((CompoundNBT) tag)));
//    }

    return toReturn;
  }
}
