package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MultiArmorSettingsMessage implements IMessage {

    public float flightSpeed;
    public float groundSpeed;
    public boolean noclipFlightLock;
    public boolean flight;
    public boolean feed;
    public boolean heal;
    public boolean removeHarmful;
    public boolean air;
    public boolean extinguish;

    public MultiArmorSettingsMessage() {
    }

    public MultiArmorSettingsMessage(float flightSpeed, float groundSpeed, boolean noclipFlightLock, boolean flight, boolean feed, boolean heal, boolean removeHarmful, boolean air, boolean extinguish) {
        this.flightSpeed = flightSpeed;
        this.groundSpeed = groundSpeed;
        this.noclipFlightLock = noclipFlightLock;
        this.flight = flight;
        this.feed = feed;
        this.heal = heal;
        this.removeHarmful = removeHarmful;
        this.air = air;
        this.extinguish = extinguish;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        flightSpeed = buf.readFloat();
        groundSpeed = buf.readFloat();
        noclipFlightLock = buf.readBoolean();
        flight = buf.readBoolean();
        feed = buf.readBoolean();
        heal = buf.readBoolean();
        removeHarmful = buf.readBoolean();
        air = buf.readBoolean();
        extinguish = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(flightSpeed);
        buf.writeFloat(groundSpeed);
        buf.writeBoolean(noclipFlightLock);
        buf.writeBoolean(flight);
        buf.writeBoolean(feed);
        buf.writeBoolean(heal);
        buf.writeBoolean(removeHarmful);
        buf.writeBoolean(air);
        buf.writeBoolean(extinguish);
    }
}
