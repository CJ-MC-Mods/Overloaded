package com.cjm721.overloaded.common;

import com.cjm721.overloaded.common.block.ModBlocks;
import mcjty.lib.compat.CompatCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class OverloadedCreativeTabs {
    public static CreativeTabs COMPRESSED_BLOCKS = new CompatCreativeTabs("Overloaded_Compressed") {
        @Override
        protected Item getItem() {
            return Item.getItemFromBlock(ModBlocks.compressedCobbleStone.get(0));
        }
    };

    public static CreativeTabs TECH = new CompatCreativeTabs("Overloaded_TECH") {
        @Override
        protected Item getItem() {
            return Item.getItemFromBlock(ModBlocks.basicGenerator);
        }
    };
}
