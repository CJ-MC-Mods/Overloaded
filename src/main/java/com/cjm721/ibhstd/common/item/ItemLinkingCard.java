package com.cjm721.ibhstd.common.item;

import com.cjm721.ibhstd.common.IBHSTDCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by CJ on 4/9/2017.
 */
public class ItemLinkingCard extends ModItem {

    public ItemLinkingCard() {
        setMaxStackSize(1);
        setRegistryName("ItemLinkingCard");
        setUnlocalizedName("ItemLinkingCard");

        setCreativeTab(IBHSTDCreativeTabs.UTILITY);

        GameRegistry.register(this);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null && tag.hasKey("TYPE")) {
            String type = tag.getString("TYPE");
            int x = tag.getInteger("X");
            int y = tag.getInteger("Y");
            int z = tag.getInteger("Z");
            int worldID = tag.getInteger("WORLD");

            tooltip.add(String.format("Bound to %s at %d:%d,%d,%d", type, worldID, x, y, z));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {

    }
}
