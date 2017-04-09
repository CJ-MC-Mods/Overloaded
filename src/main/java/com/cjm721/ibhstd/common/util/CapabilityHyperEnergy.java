package com.cjm721.ibhstd.common.util;

import com.cjm721.ibhstd.magic.energy.HyperEnergyHandler;
import com.cjm721.ibhstd.magic.energy.IHyperEnergyHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.concurrent.Callable;

/**
 * Created by CJ on 4/8/2017.
 */
public class CapabilityHyperEnergy {
    @CapabilityInject(IHyperEnergyHandler.class)
    public static Capability<IHyperEnergyHandler> HYPER_ENERGY_HANDLER = null;
    

}
