package com.cjm721.overloaded.common;

import com.cjm721.overloaded.common.block.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by CJ on 4/2/2017.
 */
public class OverloadedCreativeTabs {
    public static CreativeTabs COMPRESSED_BLOCKS = new CreativeTabs("Overloaded_Compressed") {
        @Override
        public Item getTabIconItem() {
            return Item.getByNameOrId(("minecraft:stone"));
        }
    };

    public static CreativeTabs ENERGY_BLOCKS = new CreativeTabs("Overloaded_Energy") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.basicGenerator);
        }
    };

    public static CreativeTabs UTILITY = new CreativeTabs("Overloaded_Utility") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.infiniteBarrel);
        }
    };
}
