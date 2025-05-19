package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static com.cjm721.overloaded.Overloaded.MODID;

public class RailGunSettingsMessage implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<RailGunSettingsMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "railgun_settings"));

  public int powerDelta;

  public RailGunSettingsMessage(int powerDelta) {
    this.powerDelta = powerDelta;
  }

  public static final StreamCodec<ByteBuf, RailGunSettingsMessage> STREAM_CODEC = new StreamCodec<ByteBuf, RailGunSettingsMessage>() {
    @Override
    public RailGunSettingsMessage decode(ByteBuf buf) {
      return new RailGunSettingsMessage(buf.readInt());
    }

    @Override
    public void encode(ByteBuf buf, RailGunSettingsMessage message) {
      buf.writeInt(message.powerDelta);
    }
  };

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
