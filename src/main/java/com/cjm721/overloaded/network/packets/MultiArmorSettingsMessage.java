package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;

public class MultiArmorSettingsMessage {

  public float flightSpeed;
  public float groundSpeed;
  public boolean noclipFlightLock;
  public boolean flight;
  public boolean feed;
  public boolean heal;
  public boolean removeHarmful;
  public boolean air;
  public boolean extinguish;

  public MultiArmorSettingsMessage() {}

  public MultiArmorSettingsMessage(
      float flightSpeed,
      float groundSpeed,
      boolean noclipFlightLock,
      boolean flight,
      boolean feed,
      boolean heal,
      boolean removeHarmful,
      boolean air,
      boolean extinguish) {
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

  public static MultiArmorSettingsMessage fromBytes(PacketBuffer buf) {
    return new MultiArmorSettingsMessage(
        buf.readFloat(),
        buf.readFloat(),
        buf.readBoolean(),
        buf.readBoolean(),
        buf.readBoolean(),
        buf.readBoolean(),
        buf.readBoolean(),
        buf.readBoolean(),
        buf.readBoolean());
  }

  public static void toBytes(MultiArmorSettingsMessage message, ByteBuf buf) {
    buf.writeFloat(message.flightSpeed);
    buf.writeFloat(message.groundSpeed);
    buf.writeBoolean(message.noclipFlightLock);
    buf.writeBoolean(message.flight);
    buf.writeBoolean(message.feed);
    buf.writeBoolean(message.heal);
    buf.writeBoolean(message.removeHarmful);
    buf.writeBoolean(message.air);
    buf.writeBoolean(message.extinguish);
  }
}
