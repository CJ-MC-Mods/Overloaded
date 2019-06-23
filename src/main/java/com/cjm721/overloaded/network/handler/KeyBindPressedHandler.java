package com.cjm721.overloaded.network.handler;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.network.packets.KeyBindPressedMessage;
import com.cjm721.overloaded.network.packets.NoClipStatusMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class KeyBindPressedHandler
    implements BiConsumer<KeyBindPressedMessage, Supplier<NetworkEvent.Context>> {

  @Override
  public void accept(KeyBindPressedMessage message, Supplier<NetworkEvent.Context> ctx) {
    ServerPlayerEntity player = ctx.get().getSender();

    switch (message.getBind()) {
      case NO_CLIP:
        ctx.get()
            .enqueueWork(
                () -> {
                  boolean result = ArmorEventHandler.toggleNoClip(player);
                  Overloaded.proxy.networkWrapper.send(
                      PacketDistributor.PLAYER.with(() -> player), new NoClipStatusMessage(result));
                });

        break;
    }
    ctx.get().setPacketHandled(true);
  }
}
