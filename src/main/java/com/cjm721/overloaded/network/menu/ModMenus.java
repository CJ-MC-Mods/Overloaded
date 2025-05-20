package com.cjm721.overloaded.network.menu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ModMenus {

  private static class ContainerResourceLocations {
    static final String INSTANT_FURNACE = MODID + ":instant_furnace";
  }

  public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MODID);
  public static final Supplier<MenuType<InstantFurnaceMenu>> INSTANT_FURNACE = MENUS.register("instant_furnace", () -> new MenuType<>(InstantFurnaceMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
