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
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedList;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.ITEMS;

public class ModItems {

  public static final DeferredItem<Item> linkingCard = ITEMS.registerItem("linking_card", ItemLinkingCard::new);
  public static DeferredItem<ItemMultiTool> multiTool = ITEMS.registerItem("multi_tool", ItemMultiTool::new);
  public static DeferredItem<ItemEnergyShield> energyShield = ITEMS.registerItem("energy_shield", ItemEnergyShield::new);
  public static DeferredItem<ItemAmountSelector> amountSelector = ITEMS.registerItem("amount_selector", ItemAmountSelector::new);
  public static DeferredItem<ItemRayGun> rayGun = ITEMS.registerItem("ray_gun", ItemRayGun::new);
  public static DeferredItem<ItemRailGun> railgun = ITEMS.registerItem("railgun", ItemRailGun::new);
  public static DeferredItem<ItemEnergyCore> energyCore = ITEMS.registerItem("energy_core", ItemEnergyCore::new);
  public static DeferredItem<ItemItemCore> itemCore = ITEMS.registerItem("item_core", ItemItemCore::new);
  public static DeferredItem<ItemFluidCore> fluidCore = ITEMS.registerItem("fluid_core", ItemFluidCore::new);
  public static DeferredItem<ItemMultiHelmet> customHelmet = ITEMS.registerItem("multi_helmet", ItemMultiHelmet::new);
  public static DeferredItem<ItemMultiChestplate> customChestplate = ITEMS.registerItem("multi_chestplate", ItemMultiChestplate::new);
  public static DeferredItem<ItemMultiLeggings> customLeggins = ITEMS.registerItem("multi_leggings", ItemMultiLeggings::new);
  public static DeferredItem<ItemMultiBoots> customBoots = ITEMS.registerItem("multi_boots", ItemMultiBoots::new);

  private static final List<IModRegistrable> registerList = new LinkedList<>();

  private static ItemSettingEditor settingsEditor;

  public static void addToSecondaryInit(IModRegistrable item) {
    registerList.add(item);
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
