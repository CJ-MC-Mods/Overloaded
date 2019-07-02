package com.cjm721.overloaded.capabilities;

import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;
import com.cjm721.overloaded.storage.fluid.IHyperHandlerFluid;
import com.cjm721.overloaded.storage.fluid.LongFluidStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class CapabilityHyperFluid {

  @CapabilityInject(IHyperHandlerFluid.class)
  public static Capability<IHyperHandlerFluid> HYPER_FLUID_HANDLER = null;

  public static void register() {
    CapabilityManager.INSTANCE.register(
        IHyperHandlerFluid.class,
        new Capability.IStorage<IHyperHandlerFluid>() {
          @Override
          public INBT writeNBT(
              Capability<IHyperHandlerFluid> capability,
              @Nonnull IHyperHandlerFluid instance,
              Direction side) {
            CompoundNBT tag = new CompoundNBT();
            LongFluidStack stack = instance.status();
            if (stack.fluidStack != null) {
              tag.putLong("Count", stack.amount);
              CompoundNBT subTag = new CompoundNBT();
              stack.fluidStack.writeToNBT(subTag);
              tag.put("Fluid", tag);
            }
            return tag;
          }

          @Override
          public void readNBT(
              Capability<IHyperHandlerFluid> capability,
              @Nonnull IHyperHandlerFluid instance,
              Direction side,
              @Nonnull INBT nbt) {
            CompoundNBT tag = (CompoundNBT) nbt;

            if (tag.contains("Item")) {
              LongFluidStack stack =
                  new LongFluidStack(
                      FluidStack.loadFluidStackFromNBT((CompoundNBT) tag.get("Fluid")),
                      tag.getLong("Count"));
              instance.give(stack, false);
            }
          }
        },
        () -> new LongFluidStorage(() -> {}));
  }
}
