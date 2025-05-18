package com.cjm721.overloaded.network.container;

import com.cjm721.overloaded.network.packets.ContainerDataMessage;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import javax.annotation.Nullable;
import java.util.function.Consumer;

// TODO This should be called Menu now
public abstract class ModContainer extends AbstractContainerMenu implements Consumer<ContainerDataMessage> {
  protected ModContainer(@Nullable MenuType<?> menuType, int containerId) {
    super(menuType, containerId);
  }

  @Override
  public void accept(ContainerDataMessage message) {
    for (ContainerDataMessage.ContainerData data : message.getData()) {
      this.slots.get(data.index).set(data.value);
    }
  }
}
