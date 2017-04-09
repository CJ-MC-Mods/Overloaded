package com.cjm721.overloaded.common.storage.item;

import com.cjm721.overloaded.common.storage.LongItemStack;
import com.cjm721.overloaded.common.util.NumberUtil;
import com.cjm721.overloaded.magic.item.IHyperItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

import static com.cjm721.overloaded.common.util.ItemUtil.itemsAreEqual;
import static com.cjm721.overloaded.common.util.NumberUtil.addToMax;

/**
 * Created by CJ on 4/8/2017.
 */
public class LongItemStorage implements IItemHandler, IHyperItemHandler {

    LongItemStack longItemStack;

    /**
     * Returns the number of slots available
     *
     * @return The number of slots available
     **/
    @Override
    public int getSlots() {
        return 1;
    }

    /**
     * Returns the ItemStack in a given slot.
     * <p>
     * The result's stack size may be greater than the itemstacks max size.
     * <p>
     * If the result is null, then the slot is empty.
     * If the result is not null but the stack size is zero, then it represents
     * an empty slot that will only accept* a specific itemstack.
     * <p>
     * <p/>
     * IMPORTANT: This ItemStack MUST NOT be modified. This method is not for
     * altering an inventories contents. Any implementers who are able to detect
     * modification through this method should throw an exception.
     * <p/>
     * SERIOUSLY: DO NOT MODIFY THE RETURNED ITEMSTACK
     *
     * @param slot Slot to query
     * @return ItemStack in given slot. May be null.
     **/
    @Override
    public ItemStack getStackInSlot(int slot) {
        if(longItemStack != null) {
            longItemStack.itemStack.stackSize = (int) Math.min(Integer.MAX_VALUE,longItemStack.amount);
            return longItemStack.itemStack;
        }
        return null;
    }

    /**
     * Inserts an ItemStack into the given slot and return the remainder.
     * The ItemStack should not be modified in this function!
     * Note: This behaviour is subtly different from IFluidHandlers.fill()
     *
     * @param slot     Slot to insert into.
     * @param stack    ItemStack to insert.
     * @param simulate If true, the insertion is only simulated
     * @return The remaining ItemStack that was not inserted (if the entire stack is accepted, then return null).
     * May be the same as the input ItemStack if unchanged, otherwise a new ItemStack.
     **/
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        LongItemStack result = give(new LongItemStack(stack,stack.stackSize), !simulate);

        if(result.amount == 0) {
            return null;
        }

        ItemStack toReturn = stack.copy();
        toReturn.stackSize = (int) result.amount;

        return toReturn;
    }

    /**
     * Extracts an ItemStack from the given slot. The returned value must be null
     * if nothing is extracted, otherwise it's stack size must not be greater than amount or the
     * itemstacks getMaxStackSize().
     *
     * @param slot     Slot to extract from.
     * @param amount   Amount to extract (may be greater than the current stacks max limit)
     * @param simulate If true, the extraction is only simulated
     * @return ItemStack extracted from the slot, must be null, if nothing can be extracted
     **/
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        LongItemStack result = take(amount, !simulate);

        if(result.amount == 0L) {
            return null;
        }

        ItemStack toReturn = result.itemStack.copy();
        toReturn.stackSize = (int) result.amount;

        return toReturn;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if(longItemStack != null) {
            compound.setTag("Item", longItemStack.itemStack.serializeNBT());
            compound.setLong("Count", longItemStack.amount);
        }

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        ItemStack storedItem = compound.hasKey("Item") ? ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item")) : null;
        if(storedItem != null) {
            long storedAmount = compound.hasKey("Count") ? compound.getLong("Count") : 0L;
            longItemStack = new LongItemStack(storedItem, storedAmount);
        }
    }

    public ItemStack getStoredItem() {
        return longItemStack == null ? null : longItemStack.itemStack;
    }

    public long getStoredAmount() {
        return longItemStack == null ? 0L : longItemStack.amount;
    }

    @Override
    public LongItemStack status() {
        return longItemStack;
    }

    @Override
    public LongItemStack give(LongItemStack stack, boolean doAction) {
        if(longItemStack == null) {
            if(doAction) {
                longItemStack = new LongItemStack(stack.itemStack, stack.amount);
            }
            return LongItemStack.EMPTY_STACK;
        }

        if(itemsAreEqual(longItemStack.itemStack,stack.itemStack)) {
            NumberUtil.AddReturn<Long> result = addToMax(longItemStack.amount, stack.amount);
            if(doAction) {
                longItemStack.amount = result.result;
            }
            return new LongItemStack(stack.itemStack, result.overflow);
        }

        return stack;
    }

    @Override
    public LongItemStack take(long aLong, boolean doAction) {
        if(longItemStack == null)
            return LongItemStack.EMPTY_STACK;

        long result = Math.min(aLong, longItemStack.amount);
        LongItemStack toReturn = new LongItemStack(longItemStack.itemStack, result);
        if(doAction) {
            longItemStack.amount -= result;

            if(longItemStack.amount == 0L)
                longItemStack = null;
        }

        return toReturn;
    }
}
