package com.cjm721.overloaded.capabilities;

import com.cjm721.overloaded.storage.GenericDataStorage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Map;

public class CapabilityGenericDataStorage {

  @CapabilityInject(IGenericDataStorage.class)
  public static Capability<IGenericDataStorage> GENERIC_DATA_STORAGE = null;


  public static void register() {
    CapabilityManager.INSTANCE.register(
        IGenericDataStorage.class,
        new Capability.IStorage<IGenericDataStorage>() {
          @Nullable
          @Override
          public INBT writeNBT(
              Capability<IGenericDataStorage> capability,
              IGenericDataStorage instance,
              Direction side) {
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
        },
        () -> new GenericDataStorage());
  }
}
