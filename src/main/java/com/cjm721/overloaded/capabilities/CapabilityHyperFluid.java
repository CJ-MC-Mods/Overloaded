package com.cjm721.overloaded.capabilities;

import com.cjm721.overloaded.storage.fluid.IHyperHandlerFluid;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;

import static com.cjm721.overloaded.Overloaded.MODID;

public class CapabilityHyperFluid {

  public static BlockCapability<IHyperHandlerFluid, Direction> HYPER_FLUID_HANDLER = BlockCapability.createSided(
          ResourceLocation.fromNamespaceAndPath(MODID,"hyper_fluid"),
          IHyperHandlerFluid.class
  );

//  public static void register() {
//    CapabilityManager.INSTANCE.register(
//        IHyperHandlerFluid.class,
//        new Capability.IStorage<IHyperHandlerFluid>() {
//          @Override
//          public INBT writeNBT(
//              Capability<IHyperHandlerFluid> capability,
//              @Nonnull IHyperHandlerFluid instance,
//              Direction side) {
//            CompoundNBT tag = new CompoundNBT();
//            LongFluidStack stack = instance.status();
//            if (stack.fluidStack != null) {
//              tag.putLong("Count", stack.amount);
//              CompoundNBT subTag = new CompoundNBT();
//              stack.fluidStack.writeToNBT(subTag);
//              tag.put("Fluid", tag);
//            }
//            return tag;
//          }
//
//          @Override
//          public void readNBT(
//              Capability<IHyperHandlerFluid> capability,
//              @Nonnull IHyperHandlerFluid instance,
//              Direction side,
//              @Nonnull INBT nbt) {
//            CompoundNBT tag = (CompoundNBT) nbt;
//
//            if (tag.contains("Item")) {
//              LongFluidStack stack =
//                  new LongFluidStack(
//                      FluidStack.loadFluidStackFromNBT((CompoundNBT) tag.get("Fluid")),
//                      tag.getLong("Count"));
//              instance.give(stack, false);
//            }
//          }
//        },
//        () -> new LongFluidStorage(() -> {}));
//  }
}
