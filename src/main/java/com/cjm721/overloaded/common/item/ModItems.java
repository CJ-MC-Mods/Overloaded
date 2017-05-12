package com.cjm721.overloaded.common.item;

import com.cjm721.overloaded.common.config.DevelopmentConfig;
import com.cjm721.overloaded.common.item.crafting.ItemEnergyCore;
import com.cjm721.overloaded.common.item.crafting.ItemFluidCore;
import com.cjm721.overloaded.common.item.crafting.ItemItemCore;
import com.cjm721.overloaded.common.item.functional.ItemAmountSelector;
import com.cjm721.overloaded.common.item.functional.ItemEnergyShield;
import com.cjm721.overloaded.common.item.functional.ItemLinkingCard;
import com.cjm721.overloaded.common.item.functional.ItemMultiTool;
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

    private static List<ModItem> registerList = new LinkedList<>();

    public static void addToSecondaryInit(ModItem item) {
        registerList.add(item);
    }

    public static void init() {
        linkingCard = new ItemLinkingCard();
        distanceBreaker = new ItemMultiTool();

        energyCore = new ItemEnergyCore();
        fluidCore = new ItemFluidCore();
        itemCore = new ItemItemCore();

        if(DevelopmentConfig.I.wipStuff) {
            energyShield = new ItemEnergyShield();
            amountSelector = new ItemAmountSelector();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        for(ModItem item: registerList)
            item.registerModel();
    }

    public static void registerRecipes() {
        for(ModItem item: registerList)
            item.registerRecipe();
    }
}
