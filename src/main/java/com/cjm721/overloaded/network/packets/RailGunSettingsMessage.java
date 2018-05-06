package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class RailGunSettingsMessage implements IMessage {

    public int powerDelta;

    public RailGunSettingsMessage() {}

    public RailGunSettingsMessage(int powerDelta) {
        this.powerDelta = powerDelta;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        powerDelta = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(powerDelta);
    }
}
