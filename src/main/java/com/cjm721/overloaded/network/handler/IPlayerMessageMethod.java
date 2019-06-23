package com.cjm721.overloaded.network.handler;

import net.minecraft.entity.player.ServerPlayerEntity;

import javax.annotation.Nonnull;

public interface IPlayerMessageMethod<T> {
  void handleMessage(@Nonnull ServerPlayerEntity playerMP, @Nonnull T message);
}
