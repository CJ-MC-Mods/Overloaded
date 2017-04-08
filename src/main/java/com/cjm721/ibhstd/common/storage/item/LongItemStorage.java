package com.cjm721.ibhstd.common.storage.item;

import com.cjm721.ibhstd.common.storage.INBTConvertable;
import com.cjm721.ibhstd.common.util.NumberUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

import static com.cjm721.ibhstd.common.util.ItemUtil.itemsAreEqual;
import static com.cjm721.ibhstd.common.util.NumberUtil.addToMax;

/**
 * Created by CJ on 4/8/2017.
 */
public class LongItemStorage implements IItemHandler, INBTConvertable {
    long storedAmount;
    ItemStack storedItem;

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
        if(storedItem != null)
            storedItem.stackSize = (int) Math.min(Integer.MAX_VALUE,storedAmount);
        return storedItem;
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
        if(storedItem == null) {
            if(!simulate) {
                storedItem = stack;
                storedAmount = stack.stackSize;
            }
            return null;
        }

        if(itemsAreEqual(storedItem,stack)) {
            NumberUtil.AddReturn<Long> result = addToMax(storedAmount, stack.stackSize);
            if(!simulate) {
                storedAmount = result.result;
            }
            if(result.overflow.intValue() == 0) {
                return null;
            }
            ItemStack toReturn = stack.copy();
            toReturn.stackSize = result.overflow.intValue();
            return toReturn;
        }

        return stack;
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
        if(storedItem == null)
            return null;

        ItemStack itemStack = storedItem.copy();
        itemStack.stackSize = (int) Math.min(amount, storedAmount);

        if(!simulate) {
            storedAmount -= itemStack.stackSize;
            if(storedAmount == 0L)
                storedItem = null;
        }

        return itemStack;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if(storedItem != null)
            compound.setTag("Item", storedItem.serializeNBT());
        compound.setLong("Count", storedAmount);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storedItem = compound.hasKey("Item") ? ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item")) : null;
        storedAmount = compound.hasKey("Count") ? compound.getLong("Count") : 0L;
    }

    public ItemStack getStoredItem() {
        return storedItem;
    }

    public long getStoredAmount() {
        return storedAmount;
    }
}
