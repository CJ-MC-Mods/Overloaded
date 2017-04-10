package com.cjm721.overloaded.common.util;

import com.cjm721.overloaded.common.storage.LongEnergyStack;
import com.cjm721.overloaded.common.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.common.storage.energy.IHyperHandlerEnergy;
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
    @CapabilityInject(IHyperHandlerEnergy.class)
    public static Capability<IHyperHandlerEnergy> HYPER_ENERGY_HANDLER = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IHyperHandlerEnergy.class,
            new Capability.IStorage<IHyperHandlerEnergy>() {
                @Override
                public NBTBase writeNBT(Capability<IHyperHandlerEnergy> capability, IHyperHandlerEnergy instance, EnumFacing side)
                {
                    return new NBTTagLong(instance.status().amount);
                }

                @Override
                public void readNBT(Capability<IHyperHandlerEnergy> capability, IHyperHandlerEnergy instance, EnumFacing side, NBTBase nbt)
                {
                    instance.give(new LongEnergyStack(((NBTTagInt)nbt).getLong()), true);
                }
            },
            LongEnergyStorage.class
        );
    }

}
