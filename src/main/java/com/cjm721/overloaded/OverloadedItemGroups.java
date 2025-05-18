package com.cjm721.overloaded;

import com.cjm721.overloaded.block.ModBlocks;
//import net.minecraft.world.item.ItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.Supplier;

import static com.cjm721.overloaded.Overloaded.CREATIVE_MODE_TABS;
import static com.cjm721.overloaded.Overloaded.MODID;

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
    public static final Supplier<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example", () -> CreativeModeTab.builder()
            //Set the title of the tab. Don't forget to add a translation!
            .title(Component.translatable("itemGroup." + MODID + ".tech"))
            //Set the icon of the tab.
            .icon(() -> new ItemStack(ModBlocks.creativeGenerator.get()))
            //Add your items to the tab.
            .displayItems((params, output) -> {

                output.accept(MyItemsClass.MY_ITEM.get());
                // Accepts an ItemLike. This assumes that MY_BLOCK has a corresponding item.
                output.accept(MyBlocksClass.MY_BLOCK.get());
            })
            .build()
    );
}
