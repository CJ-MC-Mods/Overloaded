package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import static com.cjm721.overloaded.Overloaded.MODID;

public class RightClickBlockMessage implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<RightClickBlockMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "right_click"));


  private Direction hitSide;
  private BlockPos pos;
  private float hitX;
  private float hitY;
  private float hitZ;

  public RightClickBlockMessage(
      BlockPos pos, Direction hitSide, float hitX, float hitY, float hitZ) {
    this.pos = pos;
    this.hitSide = hitSide;
    this.hitX = hitX;
    this.hitY = hitY;
    this.hitZ = hitZ;
  }

  public static final StreamCodec<ByteBuf, RightClickBlockMessage> STREAM_CODEC = new StreamCodec<ByteBuf, RightClickBlockMessage>() {
    @Override
    public RightClickBlockMessage decode(ByteBuf buf) {
      int x = buf.readInt();
      int y = buf.readInt();
      int z = buf.readInt();
      int facing = buf.readInt();

      return new RightClickBlockMessage(
              new BlockPos(x, y, z),
              Direction.from3DDataValue(facing),
              buf.readFloat(),
              buf.readFloat(),
              buf.readFloat());
    }

    @Override
    public void encode(ByteBuf buf, RightClickBlockMessage message) {
      buf.writeInt(message.pos.getX());
      buf.writeInt(message.pos.getY());
      buf.writeInt(message.pos.getZ());
      buf.writeInt(message.hitSide.get3DDataValue());

      buf.writeFloat(message.hitX);
      buf.writeFloat(message.hitY);
      buf.writeFloat(message.hitZ);
    }
  };

  public BlockPos getPos() {
    return pos;
  }

  public Direction getHitSide() {
    return hitSide;
  }

  public float getHitX() {
    return hitX;
  }

  public float getHitY() {
    return hitY;
  }

  public float getHitZ() {
    return hitZ;
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
