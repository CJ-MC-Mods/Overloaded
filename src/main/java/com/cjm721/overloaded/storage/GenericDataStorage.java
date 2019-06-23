package com.cjm721.overloaded.storage;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class GenericDataStorage
    implements IGenericDataStorage,
        INBTSerializable<CompoundNBT>,
        Capability.IStorage<IGenericDataStorage> {

  @CapabilityInject(IGenericDataStorage.class)
  public static Capability<IGenericDataStorage> GENERIC_DATA_STORAGE = null;

  private final Map<String, Integer> integerMap;
  private final Map<String, Boolean> booleanMap;
  private final Map<String, Double> doubleMap;
  private final Map<String, Float> floatMap;

  public GenericDataStorage() {
    integerMap = Maps.newHashMap();
    booleanMap = Maps.newHashMap();
    doubleMap = Maps.newHashMap();
    floatMap = Maps.newHashMap();
  }

  public static void register() {
    CapabilityManager.INSTANCE.register(
        IGenericDataStorage.class, new GenericDataStorage(), GenericDataStorage::new);
  }

  @Nonnull
  @Override
  public Map<String, Integer> getIntegerMap() {
    return integerMap;
  }

  @Nonnull
  @Override
  public Map<String, Boolean> getBooleanMap() {
    return booleanMap;
  }

  @Nonnull
  @Override
  public Map<String, Double> getDoubleMap() {
    return doubleMap;
  }

  @Nonnull
  @Override
  public Map<String, Float> getFloatMap() {
    return floatMap;
  }

  @Override
  public CompoundNBT serializeNBT() {
    return writeNBT(GENERIC_DATA_STORAGE, this, null);
  }

  @Override
  public void deserializeNBT(CompoundNBT tagCompound) {
    readNBT(GENERIC_DATA_STORAGE, this, null, tagCompound);
  }

  @Nullable
  @Override
  public CompoundNBT writeNBT(
      Capability<IGenericDataStorage> capability, IGenericDataStorage instance, Direction side) {
    CompoundNBT tagCompound = new CompoundNBT();
    Map<String, Integer> integers = instance.getIntegerMap();
    Map<String, Boolean> booleans = instance.getBooleanMap();
    Map<String, Float> floats = instance.getFloatMap();
    Map<String, Double> doubles = instance.getDoubleMap();

    for (String key : integers.keySet()) {
      tagCompound.putInt(key, integers.get(key));
    }

    for (String key : booleans.keySet()) {
      tagCompound.putBoolean(key, booleans.get(key));
    }

    for (String key : floats.keySet()) {
      tagCompound.putFloat(key, floats.get(key));
    }

    for (String key : doubles.keySet()) {
      tagCompound.putDouble(key, doubles.get(key));
    }

    return tagCompound;
  }

  @Override
  public void readNBT(
      Capability<IGenericDataStorage> capability,
      IGenericDataStorage instance,
      Direction side,
      INBT nbt) {
    if (!(nbt instanceof CompoundNBT)) return;

    CompoundNBT tagCompound = ((CompoundNBT) nbt);
    Map<String, Integer> integers = instance.getIntegerMap();
    Map<String, Boolean> booleans = instance.getBooleanMap();
    Map<String, Float> floats = instance.getFloatMap();
    Map<String, Double> doubles = instance.getDoubleMap();

    for (String key : tagCompound.keySet()) {
      switch (tagCompound.getTagId(key)) {
        case Constants.NBT.TAG_INT:
          integers.put(key, tagCompound.getInt(key));
          break;
        case Constants.NBT.TAG_BYTE:
          booleans.put(key, tagCompound.getBoolean(key));
          break;
        case Constants.NBT.TAG_FLOAT:
          floats.put(key, tagCompound.getFloat(key));
          break;
        case Constants.NBT.TAG_DOUBLE:
          doubles.put(key, tagCompound.getDouble(key));
          break;
      }
    }
  }
}
