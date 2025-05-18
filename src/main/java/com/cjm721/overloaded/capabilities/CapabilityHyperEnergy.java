package com.cjm721.overloaded.capabilities;

import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Direction;
import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.capabilities.CapabilityInject;
import net.neoforged.common.capabilities.CapabilityManager;
import net.neoforged.neoforge.capabilities.BlockCapability;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class CapabilityHyperEnergy {

  public static BlockCapability<IHyperHandlerEnergy, Direction> HYPER_ENERGY_HANDLER = BlockCapability.createSided(
          ResourceLocation.fromNamespaceAndPath(MODID,"hyper_energy"),
          IHyperHandlerEnergy.class
  );

  public static void register() {
    CapabilityManager.INSTANCE.register(
        IHyperHandlerEnergy.class,
        new Capability.IStorage<IHyperHandlerEnergy>() {
          @Override
          public INBT writeNBT(
              Capability<IHyperHandlerEnergy> capability,
              @Nonnull IHyperHandlerEnergy instance,
              Direction side) {
            return LongNBT.valueOf(instance.status().amount);
          }

          @Override
          public void readNBT(
              Capability<IHyperHandlerEnergy> capability,
              IHyperHandlerEnergy instance,
              Direction direction,
              INBT nbt) {
            instance.give(new LongEnergyStack(((LongNBT) nbt).getAsLong()), true);
          }
        },
        () -> new LongEnergyStorage(() -> {}));
  }
}
