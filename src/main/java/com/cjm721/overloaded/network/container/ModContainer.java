package com.cjm721.overloaded.network.container;

import com.cjm721.overloaded.network.packets.ContainerDataMessage;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class ModContainer extends Container implements Consumer<ContainerDataMessage> {
  protected ModContainer(@Nullable ContainerType<?> type, int id) {
    super(type, id);
  }

  @Override
  public void accept(ContainerDataMessage message) {
    for (ContainerDataMessage.ContainerData data : message.getData()) {
      this.dataSlots.get(data.index).set(data.value);
    }
  }
}
