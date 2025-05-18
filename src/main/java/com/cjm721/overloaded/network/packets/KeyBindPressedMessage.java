package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

public class KeyBindPressedMessage implements CustomPacketPayload {
  public KeyBind getBind() {
    return bind;
  }

  private KeyBind bind;

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return null;
  }

  public enum KeyBind {
    NO_CLIP
  }

  public KeyBindPressedMessage() {}

  public KeyBindPressedMessage(KeyBind bind) {
    this.bind = bind;
  }

  public static KeyBindPressedMessage fromBytes(ByteBuf buf) {
    return new KeyBindPressedMessage(KeyBind.valueOf((String) buf.readCharSequence(32, StandardCharsets.UTF_8)));
  }

  public static void toBytes(KeyBindPressedMessage message, @Nonnull ByteBuf buf) {
    buf.writeCharSequence(message.bind.toString(), StandardCharsets.UTF_8);
  }
}
