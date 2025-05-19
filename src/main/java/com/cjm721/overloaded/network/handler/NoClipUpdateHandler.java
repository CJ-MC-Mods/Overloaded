package com.cjm721.overloaded.network.handler;

import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.network.packets.NoClipStatusMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class NoClipUpdateHandler {

  @OnlyIn(Dist.CLIENT)
  private static void clientSide(NoClipStatusMessage message, IPayloadContext ctx) {
    ctx
        .enqueueWork(
            () -> {
              ArmorEventHandler.setNoClip(Minecraft.getInstance().player, message.isEnabled());
              Minecraft.getInstance()
                  .player
                  .displayClientMessage(
                      Component.literal("No Clip: " + message.isEnabled()), true);
            });
  }

  public static void accept(NoClipStatusMessage message, IPayloadContext ctx) {
    if (ctx.flow() == PacketFlow.CLIENTBOUND) {
      clientSide(message, ctx);
    }
  }
}
