package com.cjm721.overloaded.network.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ModContainers {

  private static class ContainerResourceLocations {
    static final String INSTANT_FURNACE = MODID + ":instant_furnace";
  }

  @ObjectHolder(ContainerResourceLocations.INSTANT_FURNACE)
  public static ContainerType<InstantFurnaceContainer> INSTANT_FURNACE;


  public static void init(IForgeRegistry<ContainerType<?>> registry) {
    registry.register(new ContainerType<>(InstantFurnaceContainer::new).setRegistryName(MODID,"instant_furnace"));
  }
}
