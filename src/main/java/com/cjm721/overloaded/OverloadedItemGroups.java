package com.cjm721.overloaded;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

import static com.cjm721.overloaded.Overloaded.CREATIVE_MODE_TABS;
import static com.cjm721.overloaded.Overloaded.MODID;

public class OverloadedItemGroups {

    public static final Supplier<CreativeModeTab> TECH = CREATIVE_MODE_TABS.register("Overloaded_Tech", () -> CreativeModeTab.builder()
            //Set the title of the tab. Don't forget to add a translation!
            .title(Component.translatable("itemGroup." + MODID + ".tech"))
            //Set the icon of the tab.
            .icon(() -> new ItemStack(ModBlocks.creativeGenerator.get()))
            //Add your items to the tab.
            .displayItems((params, output) -> {

                output.accept(ModItems.amountSelector.get());
                // Accepts an ItemLike. This assumes that MY_BLOCK has a corresponding item.
                output.accept(ModBlocks.almostInfiniteBarrel.get());
            })
            .build()
    );
}
