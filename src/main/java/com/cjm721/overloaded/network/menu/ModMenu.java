package com.cjm721.overloaded.network.menu;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import javax.annotation.Nullable;

public abstract class ModMenu extends AbstractContainerMenu {
  protected ModMenu(@Nullable MenuType<?> menuType, int containerId) {
    super(menuType, containerId);
  }
}
