package com.cjm721.overloaded.common.block.tile.infinity;

import com.cjm721.overloaded.common.storage.item.LongItemStorage;
import com.cjm721.overloaded.common.util.CapabilityHyperItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileInfiniteBarrel extends TileEntity  {

    @Nonnull
    private LongItemStorage itemStorage;

    public TileInfiniteBarrel() {
        itemStorage = new LongItemStorage();
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        return itemStorage.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);

        itemStorage.readFromNBT(compound);
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityHyperItem.HYPER_ITEM_HANDLER)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityHyperItem.HYPER_ITEM_HANDLER)
        {
            return (T) itemStorage;
        }
        return super.getCapability(capability, facing);
    }

    public LongItemStorage getStorage() {
        return itemStorage;
    }

}
