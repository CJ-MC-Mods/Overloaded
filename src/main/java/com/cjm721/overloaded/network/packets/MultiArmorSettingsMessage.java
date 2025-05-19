package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static com.cjm721.overloaded.Overloaded.MODID;

public class MultiArmorSettingsMessage implements CustomPacketPayload {

  public float flightSpeed;
  public float groundSpeed;
  public boolean noclipFlightLock;
  public boolean flight;
  public boolean feed;
  public boolean heal;
  public boolean removeHarmful;
  public boolean air;
  public boolean extinguish;

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

  public static final StreamCodec<ByteBuf, MultiArmorSettingsMessage> STREAM_CODEC = new StreamCodec<ByteBuf, MultiArmorSettingsMessage>() {
    @Override
    public MultiArmorSettingsMessage decode(ByteBuf buf) {
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

    @Override
    public void encode(ByteBuf buf, MultiArmorSettingsMessage message) {
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
  };

  public static final CustomPacketPayload.Type<MultiArmorSettingsMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "multi_armor_settings"));


  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
