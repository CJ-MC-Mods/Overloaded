package com.cjm721.overloaded.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class RightClickBlockMessage {

  private Direction hitSide;
  private BlockPos pos;
  private float hitX;
  private float hitY;
  private float hitZ;

  // Used by FML Reflection to create message
  public RightClickBlockMessage() {}

  public RightClickBlockMessage(
      BlockPos pos, Direction hitSide, float hitX, float hitY, float hitZ) {
    this.pos = pos;
    this.hitSide = hitSide;
    this.hitX = hitX;
    this.hitY = hitY;
    this.hitZ = hitZ;
  }

  public static RightClickBlockMessage fromBytes(PacketBuffer buf) {
    int x = buf.readInt();
    int y = buf.readInt();
    int z = buf.readInt();
    int facing = buf.readInt();

    return new RightClickBlockMessage(
        new BlockPos(x, y, z),
        Direction.byIndex(facing),
        buf.readFloat(),
        buf.readFloat(),
        buf.readFloat());
  }

  public static void toBytes(RightClickBlockMessage message, PacketBuffer buf) {
    buf.writeInt(message.pos.getX());
    buf.writeInt(message.pos.getY());
    buf.writeInt(message.pos.getZ());
    buf.writeInt(message.hitSide.getIndex());

    buf.writeFloat(message.hitX);
    buf.writeFloat(message.hitY);
    buf.writeFloat(message.hitZ);
  }

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
}
