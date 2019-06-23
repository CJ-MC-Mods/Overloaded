package com.cjm721.overloaded.network.handler;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class PlayerMessageHandler<T> implements BiConsumer<T, Supplier<NetworkEvent.Context>> {

  private final IPlayerMessageMethod<T> method;

  public PlayerMessageHandler(@Nonnull IPlayerMessageMethod<T> method) {
    this.method = method;
  }

  @Override
  public void accept(T message, Supplier<NetworkEvent.Context> ctx) {
    ServerPlayerEntity player = ctx.get().getSender();

    if (player == null) {
      return;
    }

    ctx.get().enqueueWork(() -> method.handleMessage(player, message));
    ctx.get().setPacketHandled(true);
  }
}
