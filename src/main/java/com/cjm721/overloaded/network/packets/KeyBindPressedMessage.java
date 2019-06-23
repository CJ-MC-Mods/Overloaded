package com.cjm721.overloaded.network.packets;

import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public class KeyBindPressedMessage {
  public KeyBind getBind() {
    return bind;
  }

  private KeyBind bind;

  public enum KeyBind {
    NO_CLIP
  }

  public KeyBindPressedMessage() {}

  public KeyBindPressedMessage(KeyBind bind) {
    this.bind = bind;
  }

  public static KeyBindPressedMessage fromBytes(PacketBuffer buf) {
    return new KeyBindPressedMessage(KeyBind.valueOf(buf.readString()));
  }

  public static void toBytes(KeyBindPressedMessage message, @Nonnull PacketBuffer buf) {
    buf.writeString(message.bind.toString());
  }
}
