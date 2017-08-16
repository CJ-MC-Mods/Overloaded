package com.cjm721.overloaded.item;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.block.compressed.BlockCompressed;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.basic.ItemCompressedBlock;
import com.cjm721.overloaded.item.crafting.ItemEnergyCore;
import com.cjm721.overloaded.item.crafting.ItemFluidCore;
import com.cjm721.overloaded.item.crafting.ItemItemCore;
import com.cjm721.overloaded.item.functional.ItemAmountSelector;
import com.cjm721.overloaded.item.functional.ItemEnergyShield;
import com.cjm721.overloaded.item.functional.ItemLinkingCard;
import com.cjm721.overloaded.item.functional.ItemMultiTool;
import com.cjm721.overloaded.item.functional.armor.ItemMultiBoots;
import com.cjm721.overloaded.item.functional.armor.ItemMultiChestplate;
import com.cjm721.overloaded.item.functional.armor.ItemMultiHelmet;
import com.cjm721.overloaded.item.functional.armor.ItemMultiLeggings;
import com.cjm721.overloaded.util.CraftingRegistry;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class ModItems {
    public static ModItem linkingCard;
    public static ItemMultiTool itemMultiTool;

    public static ItemEnergyShield energyShield;
    public static ItemAmountSelector amountSelector;

    public static ModItem energyCore;
    public static ModItem fluidCore;
    public static ModItem itemCore;

    public static ItemMultiHelmet customHelmet;
    public static ItemMultiChestplate customChestplate;
    public static ItemMultiLeggings customLeggins;
    public static ItemMultiBoots customBoots;
    public static LinkedList<ItemCompressedBlock> compressedItemBlocks;

    private static List<IModRegistrable> registerList = new LinkedList<>();

    public static void addToSecondaryInit(IModRegistrable item) {
        registerList.add(item);
    }

    public static void init() {
        linkingCard = registerItem(new ItemLinkingCard());
        itemMultiTool = registerItem(new ItemMultiTool());

        energyCore = registerItem(new ItemEnergyCore());
        fluidCore = registerItem(new ItemFluidCore());
        itemCore = registerItem(new ItemItemCore());

        customHelmet = registerItem(new ItemMultiHelmet());
        customChestplate = registerItem(new ItemMultiChestplate());
        customLeggins = registerItem(new ItemMultiLeggings());
        customBoots = registerItem(new ItemMultiBoots());

        if (OverloadedConfig.developmentConfig.wipStuff) {
            energyShield = registerItem(new ItemEnergyShield());
//            amountSelector = registerItem(new ItemAmountSelector());
        }

        compressedItemBlocks = new LinkedList<>();
        for (BlockCompressed block : ModBlocks.compressedBlocks) {
            ItemCompressedBlock itemCompressed = registerItem(new ItemCompressedBlock(block));
            compressedItemBlocks.add(itemCompressed);


            if (block.isRecipeEnabled()) {
                CraftingRegistry.addShapedRecipe(new ItemStack(itemCompressed, 1, 0), "XXX", "XXX", "XXX", 'X', new ItemStack(block.getBaseBlock(), 1));
                CraftingRegistry.addShapelessRecipe(new ItemStack(block.getBaseBlock(), 9), new ItemStack(itemCompressed, 1, 0));

                for (int meta = 0; meta < block.getMaxCompression() - 1; meta++) {
                    CraftingRegistry.addShapedRecipe(new ItemStack(itemCompressed, 1, meta + 1), "XXX", "XXX", "XXX", 'X', new ItemStack(itemCompressed, 1, meta));
                    CraftingRegistry.addShapelessRecipe(new ItemStack(itemCompressed, 9, meta), new ItemStack(itemCompressed, 1, meta + 1));
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        for (IModRegistrable item : registerList)
            item.registerModel();
    }

    private static <T extends Item> T registerItem(T item) {
        Overloaded.proxy.itemToRegister.add(item);
        if (item instanceof IModRegistrable)
            ModItems.addToSecondaryInit((IModRegistrable) item);

        return item;
    }
}
