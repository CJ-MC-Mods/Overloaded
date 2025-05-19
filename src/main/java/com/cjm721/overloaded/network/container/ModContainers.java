package com.cjm721.overloaded.network.container;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ModContainers {

  private static class ContainerResourceLocations {
    static final String INSTANT_FURNACE = MODID + ":instant_furnace";
  }

  public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MODID);
  public static final Supplier<MenuType<InstantFurnaceContainer>> INSTANT_FURNACE = MENUS.register("instant_furnace", () -> new MenuType<>(InstantFurnaceContainer::new, FeatureFlags.DEFAULT_FLAGS));
}
