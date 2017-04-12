package com.cjm721.overloaded.common.block.tile;

import com.cjm721.overloaded.common.storage.item.LongItemStorage;
import com.cjm721.overloaded.common.util.CapabilityHyperItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

/**
 * Created by CJ on 4/7/2017.
 */
public class TileInfiniteBarrel extends TileEntity  {

    LongItemStorage itemStorage;


    public TileInfiniteBarrel() {
        itemStorage = new LongItemStorage();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        return itemStorage.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        itemStorage.readFromNBT(compound);
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityHyperItem.HYPER_ITEM_HANDLER)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
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
