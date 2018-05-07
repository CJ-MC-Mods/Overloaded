package com.cjm721.overloaded;

import com.cjm721.overloaded.block.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class OverloadedCreativeTabs {

    public static final CreativeTabs COMPRESSED_BLOCKS = new CreativeTabs("Overloaded_Compressed") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Blocks.COBBLESTONE);
        }
    };

    public static final CreativeTabs TECH = new CreativeTabs("Overloaded_Tech") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.basicGenerator);
        }
    };
}
