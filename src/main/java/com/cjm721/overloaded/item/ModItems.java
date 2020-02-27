package com.cjm721.overloaded.item;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.basic.InDevItem;
import com.cjm721.overloaded.item.crafting.ItemEnergyCore;
import com.cjm721.overloaded.item.crafting.ItemFluidCore;
import com.cjm721.overloaded.item.crafting.ItemItemCore;
import com.cjm721.overloaded.item.functional.*;
import com.cjm721.overloaded.item.functional.armor.ItemMultiBoots;
import com.cjm721.overloaded.item.functional.armor.ItemMultiChestplate;
import com.cjm721.overloaded.item.functional.armor.ItemMultiHelmet;
import com.cjm721.overloaded.item.functional.armor.ItemMultiLeggings;
import com.cjm721.overloaded.proxy.CommonProxy;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.LinkedList;
import java.util.List;

public class ModItems {
  public static ModItem linkingCard;
  public static ItemMultiTool multiTool;

  public static ItemEnergyShield energyShield;
  public static ItemAmountSelector amountSelector;
  public static ItemRayGun rayGun;
  public static ItemRailGun railgun;

  private static ModItem energyCore;
  private static ModItem itemCore;
  private static ModItem fluidCore;

  public static ItemMultiHelmet customHelmet;
  public static ItemMultiChestplate customChestplate;
  public static ItemMultiLeggings customLeggins;
  public static ItemMultiBoots customBoots;

  private static final List<IModRegistrable> registerList = new LinkedList<>();

  private static ItemSettingEditor settingsEditor;

  public static void addToSecondaryInit(IModRegistrable item) {
    registerList.add(item);
  }

  public static void init() {
    linkingCard = registerItem(new ItemLinkingCard());
    multiTool = registerItem(new ItemMultiTool());

    energyCore = registerItem(new ItemEnergyCore());
    itemCore = registerItem(new ItemItemCore());
    fluidCore = registerItem(new ItemFluidCore());

    customHelmet = registerItem(new ItemMultiHelmet());
    customChestplate = registerItem(new ItemMultiChestplate());
    customLeggins = registerItem(new ItemMultiLeggings());
    customBoots = registerItem(new ItemMultiBoots());
    settingsEditor = registerItem(new ItemSettingEditor());
    rayGun = registerItem(new ItemRayGun());
    railgun = registerItem(new ItemRailGun());

    if (OverloadedConfig.INSTANCE.developmentConfig.wipStuff) {
      //            energyShield = registerItem(new ItemEnergyShield());
      //            amountSelector = registerItem(new ItemAmountSelector());
      for (int i = 0; i < 10; i++) {
        registerItem(new InDevItem("in_dev_item_" + i));
      }
    }
  }

  @OnlyIn(Dist.CLIENT)
  public static void registerModels() {
    for (IModRegistrable item : registerList) item.registerModel();
  }

  private static <T extends Item> T registerItem(T item) {
    CommonProxy.itemToRegister.add(item);
    if (item instanceof IModRegistrable) ModItems.addToSecondaryInit((IModRegistrable) item);

    return item;
  }
}
