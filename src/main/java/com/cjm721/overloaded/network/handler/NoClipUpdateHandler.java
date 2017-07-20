package com.cjm721.overloaded.network.handler;

import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.network.packets.MultiToolRightClickMessage;
import com.cjm721.overloaded.network.packets.NoClipStatusMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NoClipUpdateHandler implements IMessageHandler<NoClipStatusMessage, IMessage> {

    @Override
    public IMessage onMessage(NoClipStatusMessage message, MessageContext ctx) {
        if(ctx.side.isClient()) {
            clientSide(message);
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    private void clientSide(NoClipStatusMessage message) {
        ArmorEventHandler.setNoClip(Minecraft.getMinecraft().player,message.isEnabled());
        Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString("No Clip: " + message.isEnabled()), true);
    }

}
