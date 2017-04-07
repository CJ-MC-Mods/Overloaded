package com.cjm721.ibhstd.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

/**
 * Created by CJ on 4/5/2017.
 */
public class TileInfiniteBarrel extends TileEntity implements IInventory {

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

//    @Override
//    public ItemStack getStoredItemType() {
//        if(storedItem == null)
//            return null;
//        return new ItemStack(storedItem);
//    }
//
//    @Override
//    public void setStoredItemCount(int amount) {
//        this.storedAmount = (long) amount;
//    }
//
//    @Override
//    public void setStoredItemType(ItemStack type, int amount) {
//        this.storedItem = type.getItem();
//        this.storedAmount = (long) amount;
//    }
//
//    @Override
//    public int getMaxStoredCount() {
//        return Integer.MAX_VALUE;
//    }

    @Override
    public int getSizeInventory() {
        return 200;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if(storedItem == null)
            return null;

        if(slot == 0) {
            return new ItemStack(storedItem, (int)Math.min(storedAmount, 64));
        }
        return null;
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        System.out.format("Remove Stack of size %d from Slot: %d\n", count, index);
        if(storedItem == null)
            return null;
        if(index == 0) {
            ItemStack itemStack = new ItemStack(storedItem, (int)Math.min(storedAmount, count));
            storedAmount -= itemStack.stackSize;
            if(storedAmount == 0) {
                storedItem = null;
            }
            this.markDirty();
            return itemStack;
        }
        return null;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        System.out.format("Remove Stack from Slot: %d\n", index);

        if(storedItem == null)
            return null;
        if(index == 0) {
            ItemStack itemStack = new ItemStack(storedItem, (int)Math.min(storedAmount, 64));
            storedAmount -= itemStack.stackSize;
            if(storedAmount == 0) {
                storedItem = null;
            }
            this.markDirty();
            return itemStack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        if(storedItem != null && !storedItem.equals(stack.getItem())) {
            throw new RuntimeException("Something tried to place an invalid item in me");
        }

        storedItem = stack.getItem();

        if(index == 0) {
            storedAmount = stack.stackSize;
        } else {
            storedAmount += stack.stackSize;
        }
        this.markDirty();
        System.out.format("INDEX: %d    STACK: %s\n", index, stack.toString());
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) { }

    @Override
    public void closeInventory(EntityPlayer player) { }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if(storedItem == null)
            return true;

        if(storedItem.equals(stack.getItem())) {
            if(index == 0 && storedAmount >= 64) {
                return false;
            }
            return true;
        }

        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        this.storedAmount = 0;
        this.storedItem = null;
    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
