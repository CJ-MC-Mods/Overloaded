package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static com.cjm721.overloaded.Overloaded.MODID;

public class NoClipStatusMessage implements CustomPacketPayload {

  public boolean isEnabled() {
    return enabled;
  }

  private boolean enabled;

  public NoClipStatusMessage(boolean enabled) {
    this.enabled = enabled;
  }

  public static final StreamCodec<ByteBuf, NoClipStatusMessage> STREAM_CODEC = new StreamCodec<ByteBuf, NoClipStatusMessage>() {
    @Override
    public NoClipStatusMessage decode(ByteBuf buf) {
      return new NoClipStatusMessage(buf.readBoolean());
    }

    @Override
    public void encode(ByteBuf buf, NoClipStatusMessage message) {
      buf.writeBoolean(message.enabled);
    }
  };

  public static final CustomPacketPayload.Type<NoClipStatusMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "no_clip_status"));

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
