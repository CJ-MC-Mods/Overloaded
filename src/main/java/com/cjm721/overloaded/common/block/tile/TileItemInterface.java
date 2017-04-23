package com.cjm721.overloaded.common.block.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileItemInterface extends TileEntity implements IItemHandler {

    private ItemStack storedItem;

    public TileItemInterface() {
        storedItem = ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        compound.setTag("StoredItem", storedItem.serializeNBT());

        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        storedItem = new ItemStack((NBTTagCompound)compound.getTag("StoredItem"));

        super.readFromNBT(compound);
    }


    @Override
    @Nonnull
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);

        return new SPacketUpdateTileEntity(getPos(),1,tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public int getSlots() {
        return 1;
    }
    
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
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
    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if(storedItem.isEmpty()) {
            if(stack.getCount() == 1) {
                if(!simulate) {
                    this.storedItem = stack;
                    updateClient();
                    markDirty();
                }
                return ItemStack.EMPTY;
            }

            ItemStack storedCopy = stack.copy();
            storedCopy.setCount(1);

            ItemStack returnCopy = stack.copy();

            returnCopy.setCount(stack.getCount() - 1);

            if(!simulate) {
                this.storedItem = storedCopy;
                updateClient();
                markDirty();
            }

            return returnCopy;
        }
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack copy = storedItem.copy();
        copy.setCount(Math.min(copy.getCount(), amount));

        if(!simulate) {
            storedItem.setCount(storedItem.getCount() - copy.getCount());
            if(storedItem.getCount() == 0) {
                storedItem = ItemStack.EMPTY;
            }
            markDirty();
            updateClient();
        }

        return copy;
    }

    private void updateClient() {
        IBlockState state = getWorld().getBlockState(getPos());
        getWorld().notifyBlockUpdate(this.getPos(), state, state, 3);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if((facing == EnumFacing.UP || facing == EnumFacing.DOWN) && capability == ITEM_HANDLER_CAPABILITY) {
            return true;
        }

        return storedItem.hasCapability(capability,facing) || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if((facing == EnumFacing.UP || facing == EnumFacing.DOWN) && capability == ITEM_HANDLER_CAPABILITY) {
            return (T) this;
        }

        T t = storedItem.getCapability(capability, facing);

        if(t == null)
            return super.getCapability(capability, facing);
        return t;
    }


    public ItemStack getStoredItem() {
        return storedItem;
    }
}
