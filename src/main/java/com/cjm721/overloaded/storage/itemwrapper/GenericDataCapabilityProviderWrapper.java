package com.cjm721.overloaded.storage.itemwrapper;

import com.cjm721.overloaded.storage.GenericDataCapabilityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class GenericDataCapabilityProviderWrapper extends GenericDataCapabilityProvider {
    private static final String NBT_TAG = "overloaded:generic_data";

    @Nonnull
    private final ItemStack stack;

    public GenericDataCapabilityProviderWrapper(@Nonnull ItemStack stack) {
        this.stack = stack;

        NBTTagCompound itemNBT = this.stack.getTagCompound();
        if (itemNBT == null) {
            this.stack.setTagCompound(new NBTTagCompound());
        }
    }

    @Override
    public void suggestUpdate() {
        NBTTagCompound itemNBT = this.stack.getTagCompound();

        if (itemNBT != null && itemNBT.hasKey(NBT_TAG)) {
            this.readNBT(GENERIC_DATA_STORAGE, this, null, this.stack.getTagCompound().getTag(NBT_TAG));
        }
    }

    @Override
    public void suggestSave() {
        NBTTagCompound data = this.writeNBT(GENERIC_DATA_STORAGE, this, null);
        NBTTagCompound itemNBT = this.stack.getTagCompound();
        if (itemNBT == null) {
            itemNBT = new NBTTagCompound();
            this.stack.setTagCompound(itemNBT);
        }
        this.stack.getTagCompound().setTag(NBT_TAG, data);
    }
}
