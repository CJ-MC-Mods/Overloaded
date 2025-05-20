package com.cjm721.overloaded.storage.itemwrapper;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record EnergyStored(int energy) {
  public static final Codec<EnergyStored> CODEC =
      RecordCodecBuilder.create(
          instance ->
              instance
                  .group(Codec.INT.fieldOf("energy").forGetter(EnergyStored::energy))
                  .apply(instance, EnergyStored::new));

  public static final StreamCodec<ByteBuf, EnergyStored> STREAM_CODEC =
      StreamCodec.composite(ByteBufCodecs.INT, EnergyStored::energy, EnergyStored::new);
}
