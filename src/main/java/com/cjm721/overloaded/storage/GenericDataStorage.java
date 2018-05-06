package com.cjm721.overloaded.storage;

import com.google.common.collect.Maps;
import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class GenericDataStorage implements IGenericDataStorage, INBTSerializable<NBTTagCompound>, Capability.IStorage<IGenericDataStorage> {

    @CapabilityInject(IGenericDataStorage.class)
    public static Capability<IGenericDataStorage> GENERIC_DATA_STORAGE = null;
    private final Map<String, Integer> integerMap;
    private final Map<String, Boolean> booleanMap;
    private final Map<String, Double> doubleMap;

    public GenericDataStorage() {
        integerMap = Maps.newHashMap();
        booleanMap = Maps.newHashMap();
        doubleMap = Maps.newHashMap();
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IGenericDataStorage.class,
                new GenericDataStorage(),
                GenericDataStorage::new
        );
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

    @Override
    public NBTTagCompound serializeNBT() {
        return writeNBT(GENERIC_DATA_STORAGE, this, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound tagCompound) {
        readNBT(GENERIC_DATA_STORAGE, this, null, tagCompound);
    }

    @Nullable
    @Override
    public NBTTagCompound writeNBT(Capability<IGenericDataStorage> capability, IGenericDataStorage instance, EnumFacing side) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        Map<String, Integer> integers = instance.getIntegerMap();
        Map<String, Boolean> booleans = instance.getBooleanMap();
        Map<String, Double> doubles = instance.getDoubleMap();

        for (String key : integers.keySet()) {
            tagCompound.setInteger(key, integers.get(key));
        }

        for (String key : booleans.keySet()) {
            tagCompound.setBoolean(key, booleans.get(key));
        }

        for (String key : doubles.keySet()) {
            tagCompound.setDouble(key, doubles.get(key));
        }

        return tagCompound;
    }

    @Override
    public void readNBT(Capability<IGenericDataStorage> capability, IGenericDataStorage instance, EnumFacing side, NBTBase nbt) {
        if (!(nbt instanceof NBTTagCompound))
            return;

        NBTTagCompound tagCompound = ((NBTTagCompound) nbt);
        Map<String, Integer> integers = instance.getIntegerMap();
        Map<String, Boolean> booleans = instance.getBooleanMap();
        Map<String, Double> doubles = instance.getDoubleMap();

        for (String key : tagCompound.getKeySet()) {
            switch(tagCompound.getTagId(key)) {
                case Constants.NBT.TAG_INT:
                    integers.put(key, tagCompound.getInteger(key));
                    break;
                case Constants.NBT.TAG_BYTE:
                    booleans.put(key, tagCompound.getBoolean(key));
                    break;
                case Constants.NBT.TAG_DOUBLE:
                    doubles.put(key, tagCompound.getDouble(key));
                    break;
            }
        }
    }
}
