package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.item.ModItem;

import net.minecraft.item.Item.Properties;

public class ItemAmountSelector extends ModItem {

  protected ItemAmountSelector() {
    super(new Properties().stacksTo(1));
  }

  @Override
  public void registerModel() {}
}
