package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static com.cjm721.overloaded.Overloaded.MODID;

public class LeftClickBlockMessage implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<LeftClickBlockMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "left_click"));


  private BlockPos pos;

  public LeftClickBlockMessage(BlockPos pos) {
    this.pos = pos;
  }

  public static final StreamCodec<ByteBuf, LeftClickBlockMessage> STREAM_CODEC = new StreamCodec<ByteBuf, LeftClickBlockMessage>() {
    @Override
    public LeftClickBlockMessage decode(ByteBuf buf) {
      int x = buf.readInt();
      int y = buf.readInt();
      int z = buf.readInt();
      return new LeftClickBlockMessage(new BlockPos(x, y, z));
    }

    @Override
    public void encode(ByteBuf buf, LeftClickBlockMessage message) {
      buf.writeInt(message.pos.getX());
      buf.writeInt(message.pos.getY());
      buf.writeInt(message.pos.getZ());
    }
  };

  public BlockPos getPos() {
    return pos;
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
