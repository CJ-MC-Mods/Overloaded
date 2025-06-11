package com.cjm721.overloaded.storage;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class GenericDataStorage implements IGenericDataStorage {

  public static final Codec<GenericData> CODEC =
      RecordCodecBuilder.create(
          instance ->
              instance
                  .group(
                      Codec.unboundedMap(Codec.STRING, Codec.INT)
                          .fieldOf("integerMap")
                          .forGetter(GenericData::integerMap),
                      Codec.unboundedMap(Codec.STRING, Codec.BOOL)
                          .fieldOf("booleanMap")
                          .forGetter(GenericData::booleanMap),
                      Codec.unboundedMap(Codec.STRING, Codec.DOUBLE)
                          .fieldOf("doubleMap")
                          .forGetter(GenericData::doubleMap),
                      Codec.unboundedMap(Codec.STRING, Codec.FLOAT)
                          .fieldOf("floatMap")
                          .forGetter(GenericData::floatMap))
                  .apply(instance, GenericData::new));

  public static final StreamCodec<FriendlyByteBuf, GenericData> STREAM_CODEC =
      StreamCodec.composite(
          ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.INT, 256),
          GenericData::integerMap,
          ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.BOOL, 256),
          GenericData::booleanMap,
          ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.DOUBLE, 256),
          GenericData::doubleMap,
          ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.FLOAT, 256),
          GenericData::floatMap,
          GenericData::new);

  private final GenericData data;

  public record GenericData(
      Map<String, Integer> integerMap,
      Map<String, Boolean> booleanMap,
      Map<String, Double> doubleMap,
      Map<String, Float> floatMap) {

    public GenericData(
        Map<String, Integer> integerMap,
        Map<String, Boolean> booleanMap,
        Map<String, Double> doubleMap,
        Map<String, Float> floatMap) {
      this.integerMap = new HashMap<>(integerMap);
      this.booleanMap = new HashMap<>(booleanMap);
      this.doubleMap = new HashMap<>(doubleMap);
      this.floatMap = new HashMap<>(floatMap);
    }

    public GenericData() {
      this(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }
  }

  public GenericDataStorage(GenericData data) {
    this.data = data;
  }

  @Nonnull
  @Override
  public Map<String, Integer> getIntegerMap() {
    return data.integerMap;
  }

  @Nonnull
  @Override
  public Map<String, Boolean> getBooleanMap() {
    return data.booleanMap;
  }

  @Nonnull
  @Override
  public Map<String, Double> getDoubleMap() {
    return data.doubleMap;
  }

  @Nonnull
  @Override
  public Map<String, Float> getFloatMap() {
    return data.floatMap;
  }
}
