package com.cjm721.overloaded.util;

import com.cjm721.overloaded.storage.LongFluidStack;
import com.cjm721.overloaded.storage.fluid.IHyperHandlerFluid;
import com.cjm721.overloaded.storage.fluid.LongFluidStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class CapabilityHyperFluid {

    @CapabilityInject(IHyperHandlerFluid.class)
    public static Capability<IHyperHandlerFluid> HYPER_FLUID_HANDLER = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IHyperHandlerFluid.class, new Capability.IStorage<IHyperHandlerFluid>() {
            @Override
            public NBTBase writeNBT(Capability<IHyperHandlerFluid> capability, @Nonnull IHyperHandlerFluid instance, EnumFacing side) {
                NBTTagCompound tag = new NBTTagCompound();
                LongFluidStack stack = instance.status();
                if (stack.fluidStack != null) {
                    tag.setLong("Count", stack.amount);
                    NBTTagCompound subTag = new NBTTagCompound();
                    stack.fluidStack.writeToNBT(subTag);
                    tag.setTag("Fluid", tag);
                }
                return tag;
            }

            @Override
            public void readNBT(Capability<IHyperHandlerFluid> capability, @Nonnull IHyperHandlerFluid instance, EnumFacing side, @Nonnull NBTBase nbt) {
                NBTTagCompound tag = (NBTTagCompound) nbt;

                if (tag.hasKey("Item")) {
                    LongFluidStack stack = new LongFluidStack(FluidStack.loadFluidStackFromNBT((NBTTagCompound) tag.getTag("Fluid")), tag.getLong("Count"));
                    instance.give(stack, false);
                }
            }
        }, LongFluidStorage.class);
    }
}
