package com.cjm721.overloaded.network.handler;

import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nonnull;

public interface IPlayerMessageMethod<T> {
    void handleMessage(@Nonnull EntityPlayerMP playerMP,@Nonnull T message);
}
