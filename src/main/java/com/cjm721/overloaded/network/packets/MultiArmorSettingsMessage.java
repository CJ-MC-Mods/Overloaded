package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MultiArmorSettingsMessage implements IMessage {

    public float flightSpeed;
    public float groundSpeed;
    public boolean noclipFlightLock;

    public MultiArmorSettingsMessage() {}

    public MultiArmorSettingsMessage(float flightSpeed, float groundSpeed, boolean noclipFlightLock) {
        this.flightSpeed= flightSpeed;
        this.groundSpeed = groundSpeed;
        this.noclipFlightLock = noclipFlightLock;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        flightSpeed = buf.readFloat();
        groundSpeed = buf.readFloat();
        noclipFlightLock = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(flightSpeed);
        buf.writeFloat(groundSpeed);
        buf.writeBoolean(noclipFlightLock);
    }
}
