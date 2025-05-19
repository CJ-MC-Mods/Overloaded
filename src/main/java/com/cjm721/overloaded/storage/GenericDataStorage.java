//package com.cjm721.overloaded.storage;
//
//import com.google.common.collect.Maps;
//import net.minecraft.core.Direction;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.nbt.Tag;
//import net.neoforged.neoforge.common.util.INBTSerializable;
//
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.Map;
//
//import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE_ITEM;
//
//public class GenericDataStorage
//    implements IGenericDataStorage,
//        INBTSerializable<CompoundTag>,
//        Capability.IStorage<IGenericDataStorage> {
//
//  private final Map<String, Integer> integerMap;
//  private final Map<String, Boolean> booleanMap;
//  private final Map<String, Double> doubleMap;
//  private final Map<String, Float> floatMap;
//
//  public GenericDataStorage() {
//    integerMap = Maps.newHashMap();
//    booleanMap = Maps.newHashMap();
//    doubleMap = Maps.newHashMap();
//    floatMap = Maps.newHashMap();
//  }
//
//  @Nonnull
//  @Override
//  public Map<String, Integer> getIntegerMap() {
//    return integerMap;
//  }
//
//  @Nonnull
//  @Override
//  public Map<String, Boolean> getBooleanMap() {
//    return booleanMap;
//  }
//
//  @Nonnull
//  @Override
//  public Map<String, Double> getDoubleMap() {
//    return doubleMap;
//  }
//
//  @Nonnull
//  @Override
//  public Map<String, Float> getFloatMap() {
//    return floatMap;
//  }
//
//  @Override
//  public CompoundTag serializeNBT() {
//    return writeNBT(GENERIC_DATA_STORAGE_ITEM, this, null);
//  }
//
//  @Override
//  public void deserializeNBT(CompoundTag tagCompound) {
//    readNBT(GENERIC_DATA_STORAGE_ITEM, this, null, tagCompound);
//  }
//
//  @Nullable
//  @Override
//  public CompoundTag writeNBT(IGenericDataStorage instance) {
//    CompoundTag tagCompound = new CompoundTag();
//    Map<String, Integer> integers = instance.getIntegerMap();
//    Map<String, Boolean> booleans = instance.getBooleanMap();
//    Map<String, Float> floats = instance.getFloatMap();
//    Map<String, Double> doubles = instance.getDoubleMap();
//
//    for (String key : integers.keySet()) {
//      tagCompound.putInt(key, integers.get(key));
//    }
//
//    for (String key : booleans.keySet()) {
//      tagCompound.putBoolean(key, booleans.get(key));
//    }
//
//    for (String key : floats.keySet()) {
//      tagCompound.putFloat(key, floats.get(key));
//    }
//
//    for (String key : doubles.keySet()) {
//      tagCompound.putDouble(key, doubles.get(key));
//    }
//
//    return tagCompound;
//  }
//
//  @Override
//  public void readNBT(
//      Capability<IGenericDataStorage> capability,
//      IGenericDataStorage instance,
//      Direction side,
//      Tag nbt) {
//    if (!(nbt instanceof CompoundTag)) return;
//
//    CompoundTag tagCompound = ((CompoundTag) nbt);
//    Map<String, Integer> integers = instance.getIntegerMap();
//    Map<String, Boolean> booleans = instance.getBooleanMap();
//    Map<String, Float> floats = instance.getFloatMap();
//    Map<String, Double> doubles = instance.getDoubleMap();
//
//    for (String key : tagCompound.getAllKeys()) {
//      switch (tagCompound.getTagType(key)) {
//        case Constants.NBT.TAG_INT:
//          integers.put(key, tagCompound.getInt(key));
//          break;
//        case Constants.NBT.TAG_BYTE:
//          booleans.put(key, tagCompound.getBoolean(key));
//          break;
//        case Constants.NBT.TAG_FLOAT:
//          floats.put(key, tagCompound.getFloat(key));
//          break;
//        case Constants.NBT.TAG_DOUBLE:
//          doubles.put(key, tagCompound.getDouble(key));
//          break;
//      }
//    }
//  }
//}
