package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class RailGunSettingsMessage implements CustomPacketPayload {

  public int powerDelta;

  public RailGunSettingsMessage() {}

  public RailGunSettingsMessage(int powerDelta) {
    this.powerDelta = powerDelta;
  }

  public static RailGunSettingsMessage fromBytes(ByteBuf buf) {
    return new RailGunSettingsMessage(buf.readInt());
  }

  public static void toBytes(RailGunSettingsMessage message, ByteBuf buf) {
    buf.writeInt(message.powerDelta);
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return null;
  }
}
