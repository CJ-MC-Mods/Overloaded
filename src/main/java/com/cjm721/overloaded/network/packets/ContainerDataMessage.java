package com.cjm721.overloaded.network.packets;

import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ContainerDataMessage {

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

  public static ContainerDataMessage fromBytes(@Nonnull PacketBuffer buf) {
    ContainerDataMessage data = new ContainerDataMessage(buf.readInt());

    int size = buf.readInt();

    for (int i = 0; i < size; i++) {
      data.addData(buf.readInt(), buf.readInt());
    }

    return data;
  }

  public static void toBytes(ContainerDataMessage message, @Nonnull PacketBuffer buf) {
    buf.writeInt(message.container);
    buf.writeInt(message.data.size());
    for (ContainerData data : message.data) {
      buf.writeInt(data.index);
      buf.writeInt(data.value);
    }
  }

  public static class ContainerData {
    public final int index, value;

    ContainerData(int index, int value) {
      this.index = index;
      this.value = value;
    }
  }
}
