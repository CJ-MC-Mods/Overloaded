package com.cjm721.overloaded.common.util;

import com.cjm721.overloaded.common.storage.LongFluidStack;
import com.cjm721.overloaded.common.storage.fluid.LongFluidStorage;
import com.cjm721.overloaded.common.storage.fluid.IHyperFluidHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by CJ on 4/9/2017.
 */
public class CapabilityHyperFluid {

    @CapabilityInject(IHyperFluidHandler.class)
    public static Capability<IHyperFluidHandler> HYPER_FLUID_HANDLER = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IHyperFluidHandler.class, new Capability.IStorage<IHyperFluidHandler>() {
            @Override
            public NBTBase writeNBT(Capability<IHyperFluidHandler> capability, IHyperFluidHandler instance, EnumFacing side)
            {
                NBTTagCompound tag = new NBTTagCompound();
                LongFluidStack stack = instance.status();
                if(stack != null) {
                    tag.setLong("Count", stack.amount);
                    NBTTagCompound subTag = new NBTTagCompound();
                    stack.fluidStack.writeToNBT(subTag);
                    tag.setTag("Fluid", tag);
                }
                return tag;
            }

            @Override
            public void readNBT(Capability<IHyperFluidHandler> capability, IHyperFluidHandler instance, EnumFacing side, NBTBase nbt)
            {
                NBTTagCompound tag = (NBTTagCompound)nbt;

                if(tag.hasKey("Item")) {
                    LongFluidStack stack = new LongFluidStack(FluidStack.loadFluidStackFromNBT((NBTTagCompound) tag.getTag("Fluid")), tag.getLong("Count"));
                    instance.give(stack, false);
                }
            }
        }, LongFluidStorage.class);
    }
}
