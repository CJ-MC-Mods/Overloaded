package com.cjm721.overloaded.util;

import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nonnull;

public class CapabilityHyperEnergy {
  @CapabilityInject(IHyperHandlerEnergy.class)
  public static Capability<IHyperHandlerEnergy> HYPER_ENERGY_HANDLER = null;

  public static void register() {
    CapabilityManager.INSTANCE.register(
        IHyperHandlerEnergy.class,
        new Capability.IStorage<IHyperHandlerEnergy>() {
          @Override
          public INBT writeNBT(
              Capability<IHyperHandlerEnergy> capability,
              @Nonnull IHyperHandlerEnergy instance,
              Direction side) {
            return new LongNBT(instance.status().amount);
          }

          @Override
          public void readNBT(
              Capability<IHyperHandlerEnergy> capability,
              IHyperHandlerEnergy instance,
              Direction direction,
              INBT nbt) {
            instance.give(new LongEnergyStack(((LongNBT) nbt).getLong()), true);
          }
        },
        () -> new LongEnergyStorage(() -> {}));
  }
}
