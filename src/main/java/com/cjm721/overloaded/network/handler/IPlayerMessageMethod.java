package com.cjm721.overloaded.network.handler;

import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nonnull;

public interface IPlayerMessageMethod<T> {
  void handleMessage(@Nonnull ServerPlayer playerMP, @Nonnull T message);
}
