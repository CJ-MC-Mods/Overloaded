package com.cjm721.overloaded.network.handler;

import net.minecraft.entity.player.EntityPlayerMP;

public interface IPlayerMessageMethod<T> {
    void handleMessage(EntityPlayerMP playerMP, T message);
}
