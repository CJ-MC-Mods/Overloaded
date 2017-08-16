package com.cjm721.overloaded.item.functional.armor;

import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.Map;

public class PlayerDataStorage implements IOverloadedPlayerDataStorage, INBTSerializable<NBTTagCompound> {

    private Map<String, Integer> integerMap;
    private Map<String, Boolean> booleanMap;

    public PlayerDataStorage() {
        integerMap = Maps.newHashMap();
        booleanMap = Maps.newHashMap();
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

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        Map<String, Integer> integers = this.getIntegerMap();
        Map<String, Boolean> booleans = this.getBooleanMap();

        for (String key : integers.keySet()) {
            tagCompound.setInteger(key, integers.get(key));
        }

        for (String key : booleans.keySet()) {
            tagCompound.setBoolean(key, booleans.get(key));
        }

        return tagCompound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tagCompound) {
        Map<String, Integer> integers = this.getIntegerMap();
        Map<String, Boolean> booleans = this.getBooleanMap();

        for (String key : tagCompound.getKeySet()) {
            integers.put(key, tagCompound.getInteger(key));
        }

        for (String key : booleans.keySet()) {
            booleans.put(key, tagCompound.getBoolean(key));
        }
    }
}
