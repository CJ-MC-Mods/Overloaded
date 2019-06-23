package com.cjm721.overloaded.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class RailGunFireMessage {

  public int id;
  public Vec3d moveVector;
  public Hand hand;

  public RailGunFireMessage() {}

  public RailGunFireMessage(int id, Vec3d vector, Hand hand) {
    this.id = id;
    this.moveVector = vector;
    this.hand = hand;
  }

  public static RailGunFireMessage fromBytes(PacketBuffer buf) {
    return new RailGunFireMessage(
        buf.readInt(), MessageUtility.vecFromBytes(buf), Hand.valueOf(buf.readString()));
  }

  public static void toBytes(RailGunFireMessage message, PacketBuffer buf) {
    buf.writeInt(message.id);
    MessageUtility.toBytes(buf, message.moveVector);
    buf.writeString(message.hand.name());
  }
}
