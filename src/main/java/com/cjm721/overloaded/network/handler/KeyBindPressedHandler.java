package com.cjm721.overloaded.network.handler;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.network.packets.ContainerDataMessage;
import com.cjm721.overloaded.network.packets.KeyBindPressedMessage;
import com.cjm721.overloaded.network.packets.NoClipStatusMessage;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static com.cjm721.overloaded.Overloaded.MODID;

public class KeyBindPressedHandler {
  public static void accept(KeyBindPressedMessage message, final IPayloadContext ctx) {
    ServerPlayer player = (ServerPlayer) ctx.player();

    switch (message.getBind()) {
      case NO_CLIP:
        ctx
            .enqueueWork(
                () -> {
                  boolean result = ArmorEventHandler.toggleNoClip(player);
                    PacketDistributor.sendToPlayer(player, new NoClipStatusMessage(result));
                });

        break;
    }
  }
}
