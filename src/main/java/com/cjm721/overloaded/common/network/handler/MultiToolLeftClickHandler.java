package com.cjm721.overloaded.common.network.handler;

import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.network.packets.MultiToolLeftClickMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

public class MultiToolLeftClickHandler implements IMessageHandler<MultiToolLeftClickMessage,IMessage>{

    /**
     * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
     * is needed.
     *
     * @param message The message
     * @param ctx
     * @return an optional return message
     */
    @Override
    @Nullable
    public IMessage onMessage(MultiToolLeftClickMessage message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;

        player.getServerWorld().addScheduledTask(() -> ModItems.distanceBreaker.leftClickOnBlockServer(player.getEntityWorld(), player, message.getPos()));
        // Nothing to send to client from here
        return null;
    }
}
