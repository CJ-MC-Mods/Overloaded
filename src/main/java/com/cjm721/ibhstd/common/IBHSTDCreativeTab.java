package com.cjm721.ibhstd.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by CJ on 4/2/2017.
 */
public class IBHSTDCreativeTab extends CreativeTabs {

    public IBHSTDCreativeTab(String label) {
        super(label);
    }

    @Override
    public Item getTabIconItem() {
        return Item.getByNameOrId(("minecraft:stone"));
    }

    static {
        INSTANCE = new IBHSTDCreativeTab("IBHSTD");
    }

    public static CreativeTabs INSTANCE;
}
