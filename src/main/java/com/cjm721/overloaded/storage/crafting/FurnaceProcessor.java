package com.cjm721.overloaded.storage.crafting;

import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
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
    return recipe.getCookTime();
  }
}
