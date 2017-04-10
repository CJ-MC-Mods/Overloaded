package com.cjm721.overloaded.common.util;

import com.cjm721.overloaded.common.storage.LongItemStack;
import com.cjm721.overloaded.common.storage.item.IHyperHandlerItem;
import com.cjm721.overloaded.common.storage.item.LongItemStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Created by CJ on 4/8/2017.
 */
public class CapabilityHyperItem {
    @CapabilityInject(IHyperHandlerItem.class)
    public static Capability<IHyperHandlerItem> HYPER_ITEM_HANDLER = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IHyperHandlerItem.class, new Capability.IStorage<IHyperHandlerItem>() {
            @Override
            public NBTBase writeNBT(Capability<IHyperHandlerItem> capability, IHyperHandlerItem instance, EnumFacing side)
            {
                NBTTagCompound tag = new NBTTagCompound();
                LongItemStack stack = instance.status();
                if(stack != null) {
                    tag.setLong("Count", stack.amount);
                    tag.setTag("Item", stack.itemStack.serializeNBT());
                }
                return tag;
            }

            @Override
            public void readNBT(Capability<IHyperHandlerItem> capability, IHyperHandlerItem instance, EnumFacing side, NBTBase nbt)
            {
                NBTTagCompound tag = (NBTTagCompound)nbt;

                if(tag.hasKey("Item")) {
                    LongItemStack stack = new LongItemStack(ItemStack.loadItemStackFromNBT((NBTTagCompound) tag.getTag("Item")), tag.getLong("Count"));
                    instance.give(stack, false);
                }
            }
        }, LongItemStorage.class);
    }
}
