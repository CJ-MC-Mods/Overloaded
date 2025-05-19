package com.cjm721.overloaded.network.handler;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

public class PlayerMessageHandler<T> implements BiConsumer<T, IPayloadContext> {

  private final IPlayerMessageMethod<T> method;

  public PlayerMessageHandler(@Nonnull IPlayerMessageMethod<T> method) {
    this.method = method;
  }

  @Override
  public void accept(T message, IPayloadContext ctx) {
    ServerPlayer player = (ServerPlayer) ctx.player();

    if (player == null) {
      return;
    }

    ctx.enqueueWork(() -> method.handleMessage(player, message));
  }
}
