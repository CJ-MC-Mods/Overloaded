package com.cjm721.overloaded;

import com.cjm721.overloaded.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Comparator;

public class OverloadedItemGroups {

  public static final ItemGroup TECH =
      new ItemGroup("Overloaded_Tech") {
        @Override
        public ItemStack makeIcon() {
          return new ItemStack(ModBlocks.creativeGenerator);
        }

        @Override
        public void fillItemList(@Nonnull NonNullList<ItemStack> items) {
          super.fillItemList(items);

          items.sort(Comparator.comparing(is -> is.getHoverName().toString()));
        }
      };
}
