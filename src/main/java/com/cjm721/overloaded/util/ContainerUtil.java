package com.cjm721.overloaded.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Methods copied from 1.14.3 \o/ on MMD discord
 */
public final class ContainerUtil {
  public static ItemStack transferStackInSlot(
      final PlayerEntity player, final int index, final Container container) {
    ItemStack itemstack = ItemStack.EMPTY;
    final Slot slot = container.inventorySlots.get(index);
    if ((slot != null) && slot.getHasStack()) {
      final ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();

      final int containerSlots =
          container.inventorySlots.size() - player.inventory.mainInventory.size();
      if (index < containerSlots) {
        if (!mergeItemStack(
            itemstack1, containerSlots, container.inventorySlots.size(), true, container)) {
          return ItemStack.EMPTY;
        }
      } else if (!mergeItemStack(itemstack1, 0, containerSlots, false, container)) {
        return ItemStack.EMPTY;
      }
      if (itemstack1.getCount() == 0) {
        slot.putStack(ItemStack.EMPTY);
      } else {
        slot.onSlotChanged();
      }
      if (itemstack1.getCount() == itemstack.getCount()) {
        return ItemStack.EMPTY;
      }
      slot.onTake(player, itemstack1);
    }
    return itemstack;
  }

  public static boolean mergeItemStack(
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

        final Slot slot = container.inventorySlots.get(i);
        final ItemStack itemstack = slot.getStack();

        if (!itemstack.isEmpty()
            && (itemstack.getItem() == stack.getItem())
            && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
          final int j = itemstack.getCount() + stack.getCount();
          final int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());

          if (j <= maxSize) {
            stack.setCount(0);
            itemstack.setCount(j);
            slot.onSlotChanged();
            flag = true;
          } else if (itemstack.getCount() < maxSize) {
            stack.shrink(maxSize - itemstack.getCount());
            itemstack.setCount(maxSize);
            slot.onSlotChanged();
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

        final Slot slot1 = container.inventorySlots.get(i);
        final ItemStack itemstack1 = slot1.getStack();

        if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
          if (stack.getCount() > slot1.getSlotStackLimit()) {
            slot1.putStack(stack.split(slot1.getSlotStackLimit()));
          } else {
            slot1.putStack(stack.split(stack.getCount()));
          }

          slot1.onSlotChanged();
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
