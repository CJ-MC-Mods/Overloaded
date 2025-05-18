package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.item.ModItem;

import net.minecraft.world.item.Item.Properties;

public class ItemAmountSelector extends ModItem {

  protected ItemAmountSelector(Properties properties) {
    super(properties.stacksTo(1));
  }

  @Override
  public void registerModel() {}
}
