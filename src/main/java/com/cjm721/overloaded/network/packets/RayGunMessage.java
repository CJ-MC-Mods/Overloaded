package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import static com.cjm721.overloaded.Overloaded.MODID;

public class RayGunMessage implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<RayGunMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "ray_gun"));

  public Vec3 vector;

  public RayGunMessage(Vec3 vector) {
    this.vector = vector;
  }

  public static final StreamCodec<ByteBuf, RayGunMessage> STREAM_CODEC = new StreamCodec<ByteBuf, RayGunMessage>() {
    @Override
    public RayGunMessage decode(ByteBuf buf) {
      return new RayGunMessage(MessageUtility.vecFromBytes(buf));
    }

    @Override
    public void encode(ByteBuf buf, RayGunMessage message) {
      MessageUtility.toBytes(buf, message.vector);
    }
  };

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
