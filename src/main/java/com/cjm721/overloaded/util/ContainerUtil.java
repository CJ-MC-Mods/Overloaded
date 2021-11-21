package com.cjm721.overloaded.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Methods copied from "1.14.3 \o/" on MMD discord
 */
public final class ContainerUtil {
  @Nonnull
  public static ItemStack transferStackInSlot(
      final PlayerEntity player, final int index, final Container container) {
    ItemStack itemstack = ItemStack.EMPTY;
    final Slot slot = container.slots.get(index);
    if ((slot != null) && slot.hasItem()) {
      final ItemStack itemStack1 = slot.getItem();
      itemstack = itemStack1.copy();

      final int containerSlots =
          container.slots.size() - player.inventory.items.size();
      if (index < containerSlots) {
        if (!mergeItemStack(
            itemStack1, containerSlots, container.slots.size(), true, container)) {
          return ItemStack.EMPTY;
        }
      } else if (!mergeItemStack(itemStack1, 0, containerSlots, false, container)) {
        return ItemStack.EMPTY;
      }
      if (itemStack1.getCount() == 0) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }
      if (itemStack1.getCount() == itemstack.getCount()) {
        return ItemStack.EMPTY;
      }
      slot.onTake(player, itemStack1);
    }
    return itemstack;
  }

  private static boolean mergeItemStack(
      final ItemStack stack,
      final int startIndex,
      final int endIndex,
      final boolean reverseDirection,
      final Container container) {
    boolean flag = false;
    int i = startIndex;

    if (reverseDirection) {
      i = endIndex - 1;
    }

    if (stack.isStackable()) {
      while (!stack.isEmpty()) {
        if (reverseDirection) {
          if (i < startIndex) {
            break;
          }
        } else if (i >= endIndex) {
          break;
        }

        final Slot slot = container.slots.get(i);
        final ItemStack itemstack = slot.getItem();

        if (slot.mayPlace(stack)
            && !itemstack.isEmpty()
            && (itemstack.getItem() == stack.getItem())
            && ItemStack.tagMatches(stack, itemstack)) {
          final int j = itemstack.getCount() + stack.getCount();
          final int maxSize = Math.min(slot.getMaxStackSize(), stack.getMaxStackSize());

          if (j <= maxSize) {
            stack.setCount(0);
            itemstack.setCount(j);
            slot.setChanged();
            flag = true;
          } else if (itemstack.getCount() < maxSize) {
            stack.shrink(maxSize - itemstack.getCount());
            itemstack.setCount(maxSize);
            slot.setChanged();
            flag = true;
          }
        }

        if (reverseDirection) {
          --i;
        } else {
          ++i;
        }
      }
    }

    if (!stack.isEmpty()) {
      if (reverseDirection) {
        i = endIndex - 1;
      } else {
        i = startIndex;
      }

      while (true) {
        if (reverseDirection) {
          if (i < startIndex) {
            break;
          }
        } else if (i >= endIndex) {
          break;
        }

        final Slot slot1 = container.slots.get(i);
        final ItemStack itemstack1 = slot1.getItem();

        if (itemstack1.isEmpty() && slot1.mayPlace(stack)) {
          if (stack.getCount() > slot1.getMaxStackSize()) {
            slot1.set(stack.split(slot1.getMaxStackSize()));
          } else {
            slot1.set(stack.split(stack.getCount()));
          }

          slot1.setChanged();
          flag = true;
          break;
        }

        if (reverseDirection) {
          --i;
        } else {
          ++i;
        }
      }
    }

    return flag;
  }
}
