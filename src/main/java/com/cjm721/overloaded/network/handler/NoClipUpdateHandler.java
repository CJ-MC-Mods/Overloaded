package com.cjm721.overloaded.network.handler;

import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.network.packets.NoClipStatusMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.network.NetworkDirection;
import net.neoforged.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class NoClipUpdateHandler
    implements BiConsumer<NoClipStatusMessage, Supplier<NetworkEvent.Context>> {

  @OnlyIn(Dist.CLIENT)
  private void clientSide(NoClipStatusMessage message, Supplier<NetworkEvent.Context> ctx) {
    ctx.get()
        .enqueueWork(
            () -> {
              ArmorEventHandler.setNoClip(Minecraft.getInstance().player, message.isEnabled());
              Minecraft.getInstance()
                  .player
                  .displayClientMessage(
                      Component.literal("No Clip: " + message.isEnabled()), true);
            });
  }

  @Override
  public void accept(NoClipStatusMessage message, Supplier<NetworkEvent.Context> ctx) {
    if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
      clientSide(message, ctx);
    }
    ctx.get().setPacketHandled(true);
  }
}
