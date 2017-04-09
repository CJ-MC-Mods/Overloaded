package com.cjm721.overloaded.common.util;

import com.cjm721.overloaded.common.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.common.storage.energy.IHyperEnergyHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Created by CJ on 4/8/2017.
 */
public class CapabilityHyperEnergy {
    @CapabilityInject(IHyperEnergyHandler.class)
    public static Capability<IHyperEnergyHandler> HYPER_ENERGY_HANDLER = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IHyperEnergyHandler.class,
            new Capability.IStorage<IHyperEnergyHandler>() {
                @Override
                public NBTBase writeNBT(Capability<IHyperEnergyHandler> capability, IHyperEnergyHandler instance, EnumFacing side)
                {
                    return new NBTTagLong(instance.status());
                }

                @Override
                public void readNBT(Capability<IHyperEnergyHandler> capability, IHyperEnergyHandler instance, EnumFacing side, NBTBase nbt)
                {
                    instance.give(((NBTTagInt)nbt).getLong(), true);
                }
            },
            LongEnergyStorage.class
        );
    }

}
