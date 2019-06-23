package com.cjm721.overloaded;

import com.cjm721.overloaded.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class OverloadedItemGroups {

  public static final ItemGroup TECH =
      new ItemGroup("Overloaded_Tech") {
        @Override
        public ItemStack createIcon() {
          return new ItemStack(ModBlocks.creativeGenerator);
        }
      };
}
