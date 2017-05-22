package com.cjm721.overloaded.common.item;

import com.cjm721.overloaded.common.util.IModRegistrable;
import net.minecraft.item.Item;

public abstract class ModItem extends Item implements IModRegistrable {

    public ModItem() {
        ModItems.addToSecondaryInit(this);
    }
}
