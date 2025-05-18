package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ContainerDataMessage implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<ContainerDataMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "container_data"));

  public final int container;
  private final List<ContainerData> data;

  public ContainerDataMessage(int container) {
    this.container = container;
    data = new ArrayList<>();
  }

  public ContainerDataMessage addData(int index, int value) {
    this.data.add(new ContainerData(index, value));

    return this;
  }

  public List<ContainerData> getData() {
    return data;
  }

  public static final StreamCodec<ByteBuf, ContainerDataMessage> STREAM_CODEC = new StreamCodec<ByteBuf, ContainerDataMessage>() {
    @Override
    public ContainerDataMessage decode(ByteBuf buffer) {
      ContainerDataMessage data = new ContainerDataMessage(buffer.readInt());

      int size = buffer.readInt();

      for (int i = 0; i < size; i++) {
        data.addData(buffer.readInt(), buffer.readInt());
      }

      return data;
    }

    @Override
    public void encode(ByteBuf buffer, ContainerDataMessage message) {
      buffer.writeInt(message.container);
      buffer.writeInt(message.data.size());
      for (ContainerData data : message.data) {
        buffer.writeInt(data.index);
        buffer.writeInt(data.value);
      }
    }
  };


  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  @Override
  public ClientboundCustomPayloadPacket toVanillaClientbound() {
    return CustomPacketPayload.super.toVanillaClientbound();
  }

  @Override
  public ServerboundCustomPayloadPacket toVanillaServerbound() {
    return CustomPacketPayload.super.toVanillaServerbound();
  }

  public static class ContainerData {
    public final int index, value;

    ContainerData(int index, int value) {
      this.index = index;
      this.value = value;
    }
  }
}
