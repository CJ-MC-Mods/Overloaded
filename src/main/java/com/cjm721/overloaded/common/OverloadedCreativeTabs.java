package com.cjm721.overloaded.common;

import com.cjm721.overloaded.common.block.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class OverloadedCreativeTabs {
    public static CreativeTabs COMPRESSED_BLOCKS = new CreativeTabs("Overloaded_Compressed") {
        @Override
        @Nonnull
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.COBBLESTONE);
        }
    };

    public static CreativeTabs TECH = new CreativeTabs("Overloaded_Tech") {
        @Override
        @Nonnull
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.basicGenerator);
        }
    };
}
