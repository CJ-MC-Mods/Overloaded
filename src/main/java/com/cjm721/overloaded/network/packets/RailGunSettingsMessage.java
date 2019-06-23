package com.cjm721.overloaded.network.packets;

import net.minecraft.network.PacketBuffer;

public class RailGunSettingsMessage {

  public int powerDelta;

  public RailGunSettingsMessage() {}

  public RailGunSettingsMessage(int powerDelta) {
    this.powerDelta = powerDelta;
  }

  public static RailGunSettingsMessage fromBytes(PacketBuffer buf) {
    return new RailGunSettingsMessage(buf.readInt());
  }

  public static void toBytes(RailGunSettingsMessage message, PacketBuffer buf) {
    buf.writeInt(message.powerDelta);
  }
}
