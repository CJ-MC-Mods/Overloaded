package com.cjm721.overloaded.network.handler;

import com.cjm721.overloaded.network.container.ModContainer;
import com.cjm721.overloaded.network.packets.ContainerDataMessage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ContainerDataHandler
    implements BiConsumer<ContainerDataMessage, Supplier<NetworkEvent.Context>> {

  @OnlyIn(Dist.CLIENT)
  private void clientSide(ContainerDataMessage message, Supplier<NetworkEvent.Context> ctx) {
    if (Minecraft.getInstance().player.openContainer == null
        || Minecraft.getInstance().player.openContainer.windowId != message.container || !(Minecraft.getInstance().player.openContainer instanceof ModContainer)) {
      return;
    }
    
    ((ModContainer)Minecraft.getInstance().player.openContainer).accept(message);
  }

  @Override
  public void accept(ContainerDataMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
    if (contextSupplier.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
      clientSide(message, contextSupplier);
    }
  }
}
