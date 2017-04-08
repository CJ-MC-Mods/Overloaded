package com.cjm721.ibhstd.common;

import com.cjm721.ibhstd.common.block.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import static com.cjm721.ibhstd.IBHSTD.MODID;

/**
 * Created by CJ on 4/2/2017.
 */
public class IBHSTDCreativeTabs {
    public static CreativeTabs COMPRESSED_BLOCKS = new CreativeTabs("IBHSTD_Compressed") {
        @Override
        public Item getTabIconItem() {
            return Item.getByNameOrId(("minecraft:stone"));
        }
    };

    public static CreativeTabs ENERGY_BLOCKS = new CreativeTabs("IBHSTD_Energy") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.basicGenerator);
        }
    };

    public static CreativeTabs UTILITY = new CreativeTabs("IBHSTD_UTILITY") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.infiniteBarrel);
        }
    };
}
