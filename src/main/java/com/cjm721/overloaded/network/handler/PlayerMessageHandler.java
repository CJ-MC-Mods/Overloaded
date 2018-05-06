package com.cjm721.overloaded.network.handler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage> {

    private final IPlayerMessageMethod<T> method;

    public PlayerMessageHandler(@Nonnull IPlayerMessageMethod<T> method) {
        this.method = method;
    }

    @Override
    @Nullable
    public IMessage onMessage(T message, MessageContext ctx) {
        @Nonnull EntityPlayerMP player = ctx.getServerHandler().player;

        player.getServerWorld().addScheduledTask(() -> method.handleMessage(player, message));
        return null;
    }
}
