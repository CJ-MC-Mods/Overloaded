package com.cjm721.overloaded.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;

public class RayGunMessage {

  public Vector3d vector;

  public RayGunMessage() {}

  public RayGunMessage(Vector3d vector) {
    this.vector = vector;
  }

  public static RayGunMessage fromBytes(PacketBuffer buf) {
    return new RayGunMessage(MessageUtility.vecFromBytes(buf));
  }

  public static void toBytes(RayGunMessage message, PacketBuffer buf) {
    MessageUtility.toBytes(buf, message.vector);
  }
}
