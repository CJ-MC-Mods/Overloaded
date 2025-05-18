package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;

public class RayGunMessage implements CustomPacketPayload {

  public Vec3 vector;

  public RayGunMessage() {}

  public RayGunMessage(Vec3 vector) {
    this.vector = vector;
  }

  public static RayGunMessage fromBytes(ByteBuf buf) {
    return new RayGunMessage(MessageUtility.vecFromBytes(buf));
  }

  public static void toBytes(RayGunMessage message, ByteBuf buf) {
    MessageUtility.toBytes(buf, message.vector);
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return null;
  }
}
