package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;

import static com.cjm721.overloaded.Overloaded.MODID;

public class RailGunFireMessage implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<RailGunFireMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "railgun_fire"));

  public int id;
  public Vec3 moveVector;
  public InteractionHand hand;

  public RailGunFireMessage(int id, Vec3 vector, InteractionHand hand) {
    this.id = id;
    this.moveVector = vector;
    this.hand = hand;
  }

  public static final StreamCodec<FriendlyByteBuf, RailGunFireMessage> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, RailGunFireMessage>() {
    @Override
    public RailGunFireMessage decode(FriendlyByteBuf buf) {
      return new RailGunFireMessage(
              buf.readInt(), MessageUtility.vecFromBytes(buf), buf.readEnum(InteractionHand.class));
    }

    @Override
    public void encode(FriendlyByteBuf buf, RailGunFireMessage message) {
      buf.writeInt(message.id);
      MessageUtility.toBytes(buf, message.moveVector);
      buf.writeEnum(message.hand);
    }
  };

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
