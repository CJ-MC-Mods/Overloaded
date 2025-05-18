package com.cjm721.overloaded.capabilities;

import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;

import static com.cjm721.overloaded.Overloaded.MODID;

public class CapabilityHyperEnergy {

  public static BlockCapability<IHyperHandlerEnergy, Direction> HYPER_ENERGY_HANDLER = BlockCapability.createSided(
          ResourceLocation.fromNamespaceAndPath(MODID,"hyper_energy"),
          IHyperHandlerEnergy.class
  );

//  public static void register() {
//    CapabilityManager.INSTANCE.register(
//        IHyperHandlerEnergy.class,
//        new Capability.IStorage<IHyperHandlerEnergy>() {
//          @Override
//          public INBT writeNBT(
//              Capability<IHyperHandlerEnergy> capability,
//              @Nonnull IHyperHandlerEnergy instance,
//              Direction side) {
//            return LongNBT.valueOf(instance.status().amount);
//          }
//
//          @Override
//          public void readNBT(
//              Capability<IHyperHandlerEnergy> capability,
//              IHyperHandlerEnergy instance,
//              Direction direction,
//              INBT nbt) {
//            instance.give(new LongEnergyStack(((LongNBT) nbt).getAsLong()), true);
//          }
//        },
//        () -> new LongEnergyStorage(() -> {}));
//  }
}
