package com.cjm721.ibhstd.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

/**
 * Created by CJ on 4/5/2017.
 */
public class TileInfiniteBarrel extends TileEntity implements IInventory {

    long storedAmount;
    ItemStack storedItem;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(storedItem != null)
            compound.setTag("Item", storedItem.serializeNBT());
        compound.setLong("Count", storedAmount);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        storedItem = compound.hasKey("Item") ? ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item")) : null;
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
        if(slot == 0) {
            return storedItem;
        }
        return null;
    }

//    private void updateStoredItem() {
//        if(storedItem == null)
//            return;
//
//        if(storedItem.stackSize < storedItem.getMaxStackSize() && storedAmount > storedItem.stackSize) {
//            storedAmount += storedItem.stackSize;
//            storedItem.stackSize = (int) Math.min(storedAmount, (long)storedItem.getMaxStackSize());
//            storedAmount -= storedItem.stackSize;
//            this.markDirty();
//        }
//    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        System.out.format("Remove Stack of size %d from Slot: %d\n", count, index);
        if(storedItem == null)
            return null;
        if(index == 0) {
            ItemStack toReturn = storedItem.copy();
            toReturn.stackSize = Math.min(count, storedItem.stackSize);
            storedItem.stackSize -= toReturn.stackSize;

            updateStoredItem();
            this.markDirty();
            return toReturn;
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
            ItemStack toReturn = storedItem.copy();
            storedItem.stackSize = 0;
            updateStoredItem();
            this.markDirty();
            return toReturn;
        }
        return null;
    }

    private void updateStoredItem() {
        if(storedItem.stackSize < storedItem.getMaxStackSize()) {
            storedAmount += storedItem.stackSize;
            storedItem.stackSize = (int) Math.min(storedItem.getMaxStackSize(), storedAmount);
            storedAmount -= storedItem.stackSize;
        }

        if(storedItem.stackSize == 0) {
            storedItem = null;
        }
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        if(storedItem != null && !storedItem.isItemEqual(stack)) {
            // TODO Send Error to console and report what was lost and where
            // Instead of crashing
            throw new RuntimeException("Something tried to place an invalid item in me");
        }
        storedItem = stack;

        if(index != 0) {
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

        if(storedItem.isItemEqual(stack)) {
            if(index == 0 && storedAmount >= storedItem.getMaxStackSize()) {
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
