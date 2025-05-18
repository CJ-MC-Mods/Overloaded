package com.cjm721.overloaded.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;

public class RailGunFireMessage implements CustomPacketPayload {

  public int id;
  public Vec3 moveVector;
  public InteractionHand hand;

  public RailGunFireMessage() {}

  public RailGunFireMessage(int id, Vec3 vector, InteractionHand hand) {
    this.id = id;
    this.moveVector = vector;
    this.hand = hand;
  }

  public static RailGunFireMessage fromBytes(FriendlyByteBuf buf) {
    return new RailGunFireMessage(
        buf.readInt(), MessageUtility.vecFromBytes(buf), buf.readEnum(InteractionHand.class));
  }

  public static void toBytes(RailGunFireMessage message, FriendlyByteBuf buf) {
    buf.writeInt(message.id);
    MessageUtility.toBytes(buf, message.moveVector);
    buf.writeEnum(message.hand);
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return null;
  }
}
