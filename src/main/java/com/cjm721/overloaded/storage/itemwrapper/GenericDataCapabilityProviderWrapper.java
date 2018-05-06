package com.cjm721.overloaded.storage.itemwrapper;

import com.cjm721.overloaded.storage.GenericDataCapabilityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class GenericDataCapabilityProviderWrapper extends GenericDataCapabilityProvider {
    private static final String NBT_TAG = "overloaded:generic_data";


    private final ItemStack stack;

    public GenericDataCapabilityProviderWrapper(ItemStack stack, @Nullable NBTTagCompound baseNBT) {
        this.stack = stack;

        NBTTagCompound itemNBT = this.stack.getTagCompound();
        if (itemNBT == null) {
            this.stack.setTagCompound(new NBTTagCompound());
        }
    }

    @Override
    public void suggestUpdate() {
        NBTTagCompound itemNBT = this.stack.getTagCompound();

        if(itemNBT.hasKey(NBT_TAG)) {
            this.readNBT(GENERIC_DATA_STORAGE,this, null, this.stack.getTagCompound().getTag(NBT_TAG));
        }
    }

    @Override
    public void suggestSave() {
        NBTTagCompound data = this.writeNBT(GENERIC_DATA_STORAGE, this, null);
        this.stack.getTagCompound().setTag(NBT_TAG, data);
    }
}
