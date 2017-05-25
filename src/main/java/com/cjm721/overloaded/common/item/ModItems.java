package com.cjm721.overloaded.common.item;

import com.cjm721.overloaded.common.config.DevelopmentConfig;
import com.cjm721.overloaded.common.item.crafting.ItemEnergyCore;
import com.cjm721.overloaded.common.item.crafting.ItemFluidCore;
import com.cjm721.overloaded.common.item.crafting.ItemItemCore;
import com.cjm721.overloaded.common.item.functional.ItemAmountSelector;
import com.cjm721.overloaded.common.item.functional.ItemEnergyShield;
import com.cjm721.overloaded.common.item.functional.ItemLinkingCard;
import com.cjm721.overloaded.common.item.functional.ItemMultiTool;
import com.cjm721.overloaded.common.item.functional.armor.ItemCustomBoots;
import com.cjm721.overloaded.common.item.functional.armor.ItemCustomChestplate;
import com.cjm721.overloaded.common.item.functional.armor.ItemCustomHelmet;
import com.cjm721.overloaded.common.item.functional.armor.ItemCustomLeggins;
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

    public static ItemCustomHelmet customHelmet;
    public static ItemCustomChestplate customChestplate;
    public static ItemCustomLeggins customLeggins;
    public static ItemCustomBoots customBoots;

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
            customHelmet = new ItemCustomHelmet();
            customChestplate = new ItemCustomChestplate();
            customLeggins = new ItemCustomLeggins();
            customBoots = new ItemCustomBoots();
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
