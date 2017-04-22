package com.cjm721.overloaded.common.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModItem extends Item {

    public ModItem() {
        ModItems.addToSecondaryInit(this);
    }

    @SideOnly(Side.CLIENT)
    public abstract void registerModel();

    public abstract void registerRecipe();
}
