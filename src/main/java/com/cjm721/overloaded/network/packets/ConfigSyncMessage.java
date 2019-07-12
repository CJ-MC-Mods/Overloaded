package com.cjm721.overloaded.network.packets;

import net.minecraft.network.PacketBuffer;

public class ConfigSyncMessage {

  public String config;

  public ConfigSyncMessage() {}

  public ConfigSyncMessage(String config) {
    this.config = config;
  }

  public static ConfigSyncMessage fromBytes(PacketBuffer buf) {
    return new ConfigSyncMessage(buf.readString(20000));
  }

  public static void toBytes(ConfigSyncMessage message, PacketBuffer buf) {
    buf.writeString(message.config);
  }
}
