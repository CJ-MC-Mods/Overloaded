package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ConfigSyncMessage implements IMessage {

    public String config;

    public ConfigSyncMessage() {}

    public ConfigSyncMessage(String config) {
        this.config = config;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        config = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, config);
    }
}
