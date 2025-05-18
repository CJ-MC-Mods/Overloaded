package com.cjm721.overloaded.network.handler;

import com.cjm721.overloaded.network.container.ModContainer;
import com.cjm721.overloaded.network.packets.ContainerDataMessage;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.network.NetworkDirection;
import net.neoforged.fml.network.NetworkEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ContainerDataHandler {

  @OnlyIn(Dist.CLIENT)
  public static void clientSide(final ContainerDataMessage message, final IPayloadContext ctx) {
    if (Minecraft.getInstance().player.containerMenu == null
        || Minecraft.getInstance().player.containerMenu.containerId != message.container || !(Minecraft.getInstance().player.containerMenu instanceof ModContainer)) {
      return;
    }

    ((ModContainer)Minecraft.getInstance().player.containerMenu).accept(message);
  }

//  public static void accept(final ContainerDataMessage message, final IPayloadContext ctx) {
//    if (contextSupplier.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
//      clientSide(message, contextSupplier);
//      contextSupplier.get().setPacketHandled(true);
//    }
//  }
}
