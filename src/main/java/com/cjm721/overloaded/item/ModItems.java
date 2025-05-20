package com.cjm721.overloaded.item;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.item.crafting.ItemEnergyCore;
import com.cjm721.overloaded.item.crafting.ItemFluidCore;
import com.cjm721.overloaded.item.crafting.ItemItemCore;
import com.cjm721.overloaded.item.functional.*;
import com.cjm721.overloaded.item.functional.armor.ItemMultiBoots;
import com.cjm721.overloaded.item.functional.armor.ItemMultiChestplate;
import com.cjm721.overloaded.item.functional.armor.ItemMultiHelmet;
import com.cjm721.overloaded.item.functional.armor.ItemMultiLeggings;
import com.cjm721.overloaded.storage.itemwrapper.EnergyStored;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ModItems {

  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Overloaded.MODID);
  public static DeferredItem<Item> linkingCard =
      registerItem("linking_card", ItemLinkingCard::new, Item.Properties::new);
  public static DeferredItem<ItemMultiTool> multiTool =
      registerItem("multi_tool", ItemMultiTool::new, Item.Properties::new);
  public static DeferredItem<ItemEnergyShield> energyShield =
      registerItem("energy_shield", ItemEnergyShield::new, Item.Properties::new);
  public static DeferredItem<ItemAmountSelector> amountSelector =
      registerItem("amount_selector", ItemAmountSelector::new, Item.Properties::new);
  public static DeferredItem<ItemRayGun> rayGun =
      registerItem("ray_gun", ItemRayGun::new, Item.Properties::new);
  public static DeferredItem<ItemRailGun> railgun =
      registerItem("railgun", ItemRailGun::new, Item.Properties::new);
  public static DeferredItem<ItemEnergyCore> energyCore =
      registerItem("energy_core", ItemEnergyCore::new, Item.Properties::new);
  public static DeferredItem<ItemItemCore> itemCore =
      registerItem("item_core", ItemItemCore::new, Item.Properties::new);
  public static DeferredItem<ItemFluidCore> fluidCore =
      registerItem("fluid_core", ItemFluidCore::new, Item.Properties::new);
  public static DeferredItem<ItemMultiHelmet> customHelmet =
      registerItem("multi_helmet", ItemMultiHelmet::new, Item.Properties::new);
  public static DeferredItem<ItemMultiChestplate> customChestplate =
      registerItem("multi_chestplate", ItemMultiChestplate::new, Item.Properties::new);
  public static DeferredItem<ItemMultiLeggings> customLeggins =
      registerItem("multi_leggings", ItemMultiLeggings::new, Item.Properties::new);
  public static DeferredItem<ItemMultiBoots> customBoots =
      registerItem("multi_boots", ItemMultiBoots::new, Item.Properties::new);

  private static final List<IModRegistrable> registerList = new LinkedList<>();

  private static ItemSettingEditor settingsEditor;

  public static final DeferredRegister.DataComponents DATA_COMPONENTS =
      DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<EnergyStored>>
      STORED_ENERGY =
          DATA_COMPONENTS.registerComponentType(
              "stored_energy",
              builder ->
                  builder
                      // The codec to read/write the data to disk
                      .persistent(EnergyStored.CODEC)
                      // The codec to read/write the data across the network
                      .networkSynchronized(EnergyStored.STREAM_CODEC));

  public static void addToSecondaryInit(IModRegistrable item) {
    registerList.add(item);
  }

  @OnlyIn(Dist.CLIENT)
  public static void registerModels() {
    for (IModRegistrable item : registerList) item.registerModel();
  }

  private static <T extends Item> DeferredItem<T> registerItem(
      String name, Function<Item.Properties, T> item, Supplier<Item.Properties> properties) {
    ResourceKey<Item> itemResourceKey =
        ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, name));
    return ITEMS.register(name, () -> item.apply(properties.get()));
  }
}
