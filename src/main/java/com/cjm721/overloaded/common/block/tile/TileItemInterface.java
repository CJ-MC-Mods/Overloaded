package com.cjm721.overloaded.common.block.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileItemInterface extends TileEntity implements IItemHandler {

    private ItemStack storedItem;

    public TileItemInterface() {
        storedItem = null;
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        compound.setTag("StoredItem", storedItem.serializeNBT());

        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        storedItem = ItemStack.loadItemStackFromNBT((NBTTagCompound)compound.getTag("StoredItem"));

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
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, @Nonnull SPacketUpdateTileEntity pkt) {
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
    @Nullable
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if(storedItem == null) {
            if(stack.stackSize == 1) {
                if(!simulate) {
                    this.storedItem = stack;
                    updateClient();
                    markDirty();
                }
                return null;
            }

            ItemStack storedCopy = stack.copy();
            storedCopy.stackSize = 1;

            ItemStack returnCopy = stack.copy();

            returnCopy.stackSize = stack.stackSize - 1;

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
        copy.stackSize = Math.min(copy.stackSize, amount);

        if(!simulate) {
            storedItem.stackSize = storedItem.stackSize - copy.stackSize;
            if(storedItem.stackSize == 0) {
                storedItem = null;
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
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if((facing == EnumFacing.UP || facing == EnumFacing.DOWN) && capability == ITEM_HANDLER_CAPABILITY) {
            return true;
        }

        return storedItem.hasCapability(capability,facing) || super.hasCapability(capability, facing);
    }

    @Nonnull
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

    public void breakBlock() {
        if(storedItem != null)
            this.getWorld().spawnEntityInWorld(new EntityItem(this.getWorld(),getPos().getX(), getPos().getY(),getPos().getZ(),storedItem));
    }
}
