package com.cjm721.overloaded.item;

import com.cjm721.overloaded.OverloadedItemGroups;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

import net.minecraft.item.Item.Properties;

public abstract class ModItem extends Item implements IModRegistrable {
  protected ModItem(@Nonnull Properties properties) {
    super(properties.tab(OverloadedItemGroups.TECH));
  }
}
