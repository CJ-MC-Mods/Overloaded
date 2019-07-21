package com.cjm721.overloaded.storage.crafting;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class FurnaceProcessor extends EnergyInventoryBasedRecipeProcessor<FurnaceRecipe> {

  public FurnaceProcessor(
      Supplier<World> worldSupplier, int maxEnergy, int slots, @Nonnull IDataUpdate dataUpdate) {
    super(IRecipeType.SMELTING, worldSupplier, maxEnergy, slots, dataUpdate);
  }

  @Override
  int energyCostPerRecipeOperation(FurnaceRecipe recipe) {
    long energy = recipe.getCookTime() * (long) OverloadedConfig.INSTANCE.productionConfig.energyPerCookTime;

    return (int) Math.min(energy, Integer.MAX_VALUE);
  }
}
