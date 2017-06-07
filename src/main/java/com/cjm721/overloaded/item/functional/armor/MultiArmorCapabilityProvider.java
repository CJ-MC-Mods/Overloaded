package com.cjm721.overloaded.item.functional.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class MultiArmorCapabilityProvider extends PlayerDataStorage implements ICapabilityProvider {

    @CapabilityInject(IOverloadedPlayerDataStorage.class)
    public static Capability<IOverloadedPlayerDataStorage> PLAYER_DATA_STORAGE = null;
    private final EntityPlayer player;


    public MultiArmorCapabilityProvider(EntityPlayer player) {
        this.player = player;
    }

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IOverloadedPlayerDataStorage.class,
                new Capability.IStorage<IOverloadedPlayerDataStorage>() {
                    @Override
                    public NBTBase writeNBT(Capability<IOverloadedPlayerDataStorage> capability, @Nonnull IOverloadedPlayerDataStorage instance, EnumFacing side)
                    {
                        NBTTagCompound tagCompound = new NBTTagCompound();
                        Map<String, Integer> integers = instance.getIntegerMap();
                        Map<String, Boolean> booleans = instance.getBooleanMap();

                        for(String key: integers.keySet()) {
                            tagCompound.setInteger(key, integers.get(key));
                        }

                        for(String key: booleans.keySet()) {
                            tagCompound.setBoolean(key, booleans.get(key));
                        }

                        return tagCompound;
                    }

                    @Override
                    public void readNBT(Capability<IOverloadedPlayerDataStorage> capability,@Nonnull IOverloadedPlayerDataStorage instance, EnumFacing side, NBTBase nbt)
                    {
                        if( !(nbt instanceof NBTTagCompound))
                            return;

                        NBTTagCompound tagCompound = ((NBTTagCompound) nbt);
                        Map<String, Integer> integers = instance.getIntegerMap();
                        Map<String, Boolean> booleans = instance.getBooleanMap();

                        for(String key: tagCompound.getKeySet()) {
                            integers.put(key, tagCompound.getInteger(key));
                        }

                        for(String key: booleans.keySet()) {
                            booleans.put(key, tagCompound.getBoolean(key));
                        }
                    }
                },
                PlayerDataStorage.class
        );
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing enumFacing) {
        return capability == PLAYER_DATA_STORAGE;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing enumFacing) {
        if(capability == PLAYER_DATA_STORAGE) {
            return PLAYER_DATA_STORAGE.cast(this);
        }
        return null;
    }
}
