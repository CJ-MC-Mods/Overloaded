package com.cjm721.overloaded.network.handler;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.network.packets.KeyBindPressedMessage;
import com.cjm721.overloaded.network.packets.NoClipStatusMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class KeyBindPressedHandler implements IMessageHandler<KeyBindPressedMessage, IMessage> {

    /**
     * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
     * is needed.
     *
     * @param message The message
     * @param ctx
     * @return an optional return message
     */
    @Override
    public IMessage onMessage(KeyBindPressedMessage message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;

        switch (message.getBind()) {
            case NO_CLIP:
                player.getServerWorld().addScheduledTask(() -> {
                    boolean result = ArmorEventHandler.toggleNoClip(player);
                    Overloaded.proxy.networkWrapper.sendTo(new NoClipStatusMessage(result), player);
                });

                break;
        }

        // Nothing to send to client from here
        return null;
    }
}
