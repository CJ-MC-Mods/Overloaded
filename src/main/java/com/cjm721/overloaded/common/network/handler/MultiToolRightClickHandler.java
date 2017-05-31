package com.cjm721.overloaded.common.network.handler;

import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.network.packets.MultiToolRightClickMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

public class MultiToolRightClickHandler implements IMessageHandler<MultiToolRightClickMessage, IMessage> {

    /**
     * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
     * is needed.
     *
     * @param message The message
     * @param ctx the context of the message
     * @return an optional return message
     */
    @Override
    @Nullable
    public IMessage onMessage(MultiToolRightClickMessage message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        WorldServer world = player.getServerWorld();

        world.addScheduledTask(() ->ModItems.itemMultiTool.rightClickWithItem(world,player, message.getPos(), message.getHitSide(), message.getHitX(), message.getHitY(), message.getHitZ()));
        // Nothing to send to client from here
        return null;
    }
}
