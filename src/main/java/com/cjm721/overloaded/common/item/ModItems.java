package com.cjm721.overloaded.common.item;

import com.cjm721.overloaded.common.config.DevelopmentConfig;
import com.cjm721.overloaded.common.item.crafting.ItemEnergyCore;
import com.cjm721.overloaded.common.item.crafting.ItemFluidCore;
import com.cjm721.overloaded.common.item.crafting.ItemItemCore;
import com.cjm721.overloaded.common.item.functional.ItemAmountSelector;
import com.cjm721.overloaded.common.item.functional.ItemEnergyShield;
import com.cjm721.overloaded.common.item.functional.ItemLinkingCard;
import com.cjm721.overloaded.common.item.functional.ItemMultiTool;
import com.cjm721.overloaded.common.item.functional.armor.ItemMultiBoots;
import com.cjm721.overloaded.common.item.functional.armor.ItemMultiChestplate;
import com.cjm721.overloaded.common.item.functional.armor.ItemMultiHelmet;
import com.cjm721.overloaded.common.item.functional.armor.ItemMultiLeggings;
import com.cjm721.overloaded.common.util.IModRegistrable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class ModItems {
    public static ModItem linkingCard;
    public static ItemMultiTool distanceBreaker;

    public static ItemEnergyShield energyShield;
    public static ItemAmountSelector amountSelector;

    public static ModItem energyCore;
    public static ModItem fluidCore;
    public static ModItem itemCore;

    public static ItemMultiHelmet customHelmet;
    public static ItemMultiChestplate customChestplate;
    public static ItemMultiLeggings customLeggins;
    public static ItemMultiBoots customBoots;

    private static List<IModRegistrable> registerList = new LinkedList<>();

    public static void addToSecondaryInit(IModRegistrable item) {
        registerList.add(item);
    }

    public static void init() {
        linkingCard = new ItemLinkingCard();
        distanceBreaker = new ItemMultiTool();

        energyCore = new ItemEnergyCore();
        fluidCore = new ItemFluidCore();
        itemCore = new ItemItemCore();

        if(DevelopmentConfig.I.wipStuff) {
//            energyShield = new ItemEnergyShield();
//            amountSelector = new ItemAmountSelector();
            customHelmet = new ItemMultiHelmet();
            customChestplate = new ItemMultiChestplate();
            customLeggins = new ItemMultiLeggings();
            customBoots = new ItemMultiBoots();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        for(IModRegistrable item: registerList)
            item.registerModel();
    }

    public static void registerRecipes() {
        for(IModRegistrable item: registerList)
            item.registerRecipe();
    }
}
