package com.cjm721.overloaded;

import com.cjm721.overloaded.block.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Comparator;

public class OverloadedCreativeTabs {
    // Not in use yet for 1.12
//    public static CreativeTabs COMPRESSED_BLOCKS = new CreativeTabs("Overloaded_Compressed") {
//        @Override
//        public ItemStack getTabIconItem() {
//            return new ItemStack(Blocks.COBBLESTONE);
//        }
//
//        @Override
//        public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> p_78018_1_) {
//            super.displayAllRelevantItems(p_78018_1_);
//
//            p_78018_1_.sort(Comparator.comparing(a -> a.getItem().getRegistryName().getResourcePath()));
//        }
//    };

    public static CreativeTabs TECH = new CreativeTabs("Overloaded_Tech") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.basicGenerator);
        }


    };
}
