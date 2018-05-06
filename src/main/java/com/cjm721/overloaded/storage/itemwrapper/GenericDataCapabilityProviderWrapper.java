package com.cjm721.overloaded.storage.itemwrapper;

import com.cjm721.overloaded.storage.GenericDataCapabilityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class GenericDataCapabilityProviderWrapper extends GenericDataCapabilityProvider {
    private static final String NBT_TAG = "overloaded:generic_data";


    private final ItemStack stack;

    public GenericDataCapabilityProviderWrapper(ItemStack stack, @Nullable NBTTagCompound nbt) {
        this.stack = stack;

        if (this.stack.getTagCompound() == null) {
            this.stack.setTagCompound(new NBTTagCompound());
        } else if(this.stack.getTagCompound().hasKey(NBT_TAG)){
            this.readNBT(GENERIC_DATA_STORAGE,this,null, this.stack.getTagCompound().getCompoundTag(NBT_TAG));
        }
    }

    @Override
    public void suggestSave() {
        NBTTagCompound data = this.writeNBT(GENERIC_DATA_STORAGE, this, null);
        this.stack.getTagCompound().setTag(NBT_TAG, data);
    }
}
