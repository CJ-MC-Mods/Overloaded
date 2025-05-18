package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class LeftClickBlockMessage implements CustomPacketPayload {

  private BlockPos pos;

  public LeftClickBlockMessage() {}

  public LeftClickBlockMessage(BlockPos pos) {
    this.pos = pos;
  }

  public static LeftClickBlockMessage fromBytes(ByteBuf buf) {
    int x = buf.readInt();
    int y = buf.readInt();
    int z = buf.readInt();
    return new LeftClickBlockMessage(new BlockPos(x, y, z));
  }

  public static void toBytes(LeftClickBlockMessage message, ByteBuf buf) {
    buf.writeInt(message.pos.getX());
    buf.writeInt(message.pos.getY());
    buf.writeInt(message.pos.getZ());
  }

  public BlockPos getPos() {
    return pos;
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return null;
  }
}
