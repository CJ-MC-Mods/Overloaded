package com.cjm721.overloaded.fluid;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.IForgeRegistry;

public class ModFluids {
  public static FlowingFluid pureMatterSource;
  public static FlowingFluid pureMatterFlowing;

  public static void init(IForgeRegistry<Fluid> registry) {
    pureMatterSource = new FluidPureMatter.Source();
    pureMatterFlowing = new FluidPureMatter.Flowing();

//    registry.registerAll(pureMatterSource, pureMatterFlowing);
  }
}
