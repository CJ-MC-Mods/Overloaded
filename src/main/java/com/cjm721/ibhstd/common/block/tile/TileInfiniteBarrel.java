package com.cjm721.ibhstd.common.block.tile;

import com.google.common.math.LongMath;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;

/**
 * Created by CJ on 4/5/2017.
 */
public class TileInfiniteBarrel extends TileEntity implements IDeepStorageUnit, IItemHandler {

    long storedAmount;
    Item storedItem;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(storedItem != null)
            compound.setTag("Item", new ItemStack(storedItem).serializeNBT());
        compound.setLong("Count", storedAmount);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        storedItem = compound.hasKey("Item") ? ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item")).getItem() : null;
        storedAmount = compound.hasKey("Count") ? compound.getLong("Count") : 0L;
    }

    @Override
    public ItemStack getStoredItemType() {
        return new ItemStack(storedItem);
    }

    @Override
    public void setStoredItemCount(int amount) {
        this.storedAmount = (long) amount;
    }

    @Override
    public void setStoredItemType(ItemStack type, int amount) {
        this.storedItem = type.getItem();
        this.storedAmount = (long) amount;
    }

    @Override
    public int getMaxStoredCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return new ItemStack(storedItem);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return null;

//        if(storedItem == null) {
//            this.storedItem = stack.getItem();
//            this.storedAmount = stack.stackSize;
//            return null;
//        } else {
//            if(stack.getItem().equals(storedItem)) {
//                long newAmount = storedAmount + stack.stackSize;
//
//                if(newAmount < 0) {
//                    long
//                }
//                if(!simulate) {
//
//                }
//
//
//            } else {
//                return stack;
//            }
//        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return null;
//        if(storedItem == null)
//            return null;
//
//        long pulled = Math.min(storedAmount, amount);
//
//        if(!simulate) {
//            storedAmount = Math.max(storedAmount - amount, 0);
//        }
//        return new ItemStack(storedItem.getItem(), (int)pulled);
    }
}
