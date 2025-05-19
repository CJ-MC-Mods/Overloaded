package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

import static com.cjm721.overloaded.Overloaded.MODID;

public class KeyBindPressedMessage implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<KeyBindPressedMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "key_pressed"));

  public KeyBind getBind() {
    return bind;
  }

  private KeyBind bind;

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public enum KeyBind {
    NO_CLIP
  }

  public KeyBindPressedMessage(KeyBind bind) {
    this.bind = bind;
  }

  public static final StreamCodec<ByteBuf, KeyBindPressedMessage> STREAM_CODEC = new StreamCodec<ByteBuf, KeyBindPressedMessage>() {
    @Override
    public KeyBindPressedMessage decode(ByteBuf buf) {
      return new KeyBindPressedMessage(KeyBind.valueOf((String) buf.readCharSequence(32, StandardCharsets.UTF_8)));
    }

    @Override
    public void encode(ByteBuf buf, KeyBindPressedMessage message) {
      buf.writeCharSequence(message.bind.toString(), StandardCharsets.UTF_8);
    }
  };
}
