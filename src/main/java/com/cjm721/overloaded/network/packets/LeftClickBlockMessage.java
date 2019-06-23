package com.cjm721.overloaded.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class LeftClickBlockMessage {

  private BlockPos pos;

  public LeftClickBlockMessage() {}

  public LeftClickBlockMessage(BlockPos pos) {
    this.pos = pos;
  }

  public static LeftClickBlockMessage fromBytes(PacketBuffer buf) {
    int x = buf.readInt();
    int y = buf.readInt();
    int z = buf.readInt();
    return new LeftClickBlockMessage(new BlockPos(x, y, z));
  }

  public static void toBytes(LeftClickBlockMessage message, PacketBuffer buf) {
    buf.writeInt(message.pos.getX());
    buf.writeInt(message.pos.getY());
    buf.writeInt(message.pos.getZ());
  }

  public BlockPos getPos() {
    return pos;
  }
}
