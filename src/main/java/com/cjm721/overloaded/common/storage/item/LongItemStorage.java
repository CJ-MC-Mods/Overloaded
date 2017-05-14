package com.cjm721.overloaded.common.storage.item;

import com.cjm721.overloaded.common.storage.INBTConvertible;
import com.cjm721.overloaded.common.storage.LongItemStack;
import com.cjm721.overloaded.common.util.NumberUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.common.util.ItemUtil.itemsAreEqual;
import static com.cjm721.overloaded.common.util.NumberUtil.addToMax;

public class LongItemStorage implements IItemHandler, IHyperHandlerItem, INBTConvertible {

    @Nonnull
    private LongItemStack longItemStack;


    public LongItemStorage() {
        longItemStack = new LongItemStack(null,0);
    }

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
    @Nullable
    public ItemStack getStackInSlot(int slot) {
        if(longItemStack.getItemStack() != null) {
            longItemStack.getItemStack().stackSize = (int) Math.min(Integer.MAX_VALUE,longItemStack.getAmount());
            return longItemStack.getItemStack();
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
    @Nullable
    public ItemStack insertItem(int slot,@Nonnull ItemStack stack, boolean simulate) {
        LongItemStack result = give(new LongItemStack(stack,stack.stackSize), !simulate);

        if(result.getAmount() == 0) {
            return null;
        }

        ItemStack toReturn = stack.copy();
        toReturn.stackSize = (int) result.getAmount();

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
    @Nullable
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        LongItemStack result = take(new LongItemStack(null,amount), !simulate);

        if(result.getAmount() == 0L) {
            return null;
        }

        ItemStack toReturn = result.getItemStack().copy();
        toReturn.stackSize = (int) result.getAmount();

        return toReturn;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if(longItemStack.getItemStack() != null) {
            compound.setTag("Item", longItemStack.getItemStack().serializeNBT());
            compound.setLong("Count", longItemStack.getAmount());
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

    @Override
    @Nonnull
    public LongItemStack status() {
        return longItemStack;
    }

    @Nonnull
    @Override
    public LongItemStack give(@Nonnull LongItemStack stack, boolean doAction) {
        if(longItemStack.getItemStack() == null) {
            if(doAction) {
                longItemStack = new LongItemStack(stack.getItemStack(), stack.getAmount());
            }
            return LongItemStack.EMPTY_STACK;
        }

        if(itemsAreEqual(longItemStack.getItemStack(),stack.getItemStack())) {
            NumberUtil.AddReturn<Long> result = addToMax(longItemStack.getAmount(), stack.getAmount());
            if(doAction) {
                longItemStack.setAmount(result.result);
            }
            return new LongItemStack(stack.getItemStack(), result.overflow);
        }

        return stack;
    }

    @Nonnull
    @Override
    public LongItemStack take(@Nonnull LongItemStack stack, boolean doAction) {
        if(longItemStack.getItemStack() == null)
            return LongItemStack.EMPTY_STACK;

        long result = Math.min(stack.getAmount(), longItemStack.getAmount());
        LongItemStack toReturn = new LongItemStack(longItemStack.getItemStack(), result);
        if(doAction) {
            longItemStack.removeAmount(result);

            if(longItemStack.getAmount() == 0L)
                longItemStack.setItemStack(null);
        }

        return toReturn;
    }
}
