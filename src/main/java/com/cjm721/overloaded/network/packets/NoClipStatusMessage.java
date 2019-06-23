package com.cjm721.overloaded.network.packets;

import net.minecraft.network.PacketBuffer;

public class NoClipStatusMessage {

  public boolean isEnabled() {
    return enabled;
  }

  private boolean enabled;

  public NoClipStatusMessage() {}

  public NoClipStatusMessage(boolean enabled) {
    this.enabled = enabled;
  }

  public static NoClipStatusMessage fromBytes(PacketBuffer buf) {
    return new NoClipStatusMessage(buf.readBoolean());
  }

  public static void toBytes(NoClipStatusMessage message, PacketBuffer buf) {
    buf.writeBoolean(message.enabled);
  }
}
