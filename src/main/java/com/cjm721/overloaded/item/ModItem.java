package com.cjm721.overloaded.item;

import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.world.item.Item;

import javax.annotation.Nonnull;

public abstract class ModItem extends Item implements IModRegistrable {
  protected ModItem(@Nonnull Properties properties) {
    super(properties);
  }
}
