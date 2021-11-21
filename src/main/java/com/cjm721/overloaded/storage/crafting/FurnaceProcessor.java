package com.cjm721.overloaded.storage.crafting;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.storage.item.SubsetItemHandlerWrapper;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class FurnaceProcessor extends EnergyInventoryBasedRecipeProcessor<FurnaceRecipe> {

  @Nonnull
  private final SubsetItemHandlerWrapper inputSubset;
  @Nonnull
  private final SubsetItemHandlerWrapper outputSubset;

  public FurnaceProcessor(
      Supplier<World> worldSupplier, int maxEnergy, int slots, @Nonnull IDataUpdate dataUpdate) {
    super(IRecipeType.SMELTING, worldSupplier, maxEnergy, slots, dataUpdate);

    this.inputSubset = new SubsetItemHandlerWrapper(this, 0, slots);
    this.outputSubset = new SubsetItemHandlerWrapper(this, slots, slots);
  }

  @Override
  int energyCostPerRecipeOperation(FurnaceRecipe recipe) {
    long energy = recipe.getCookingTime() * (long) OverloadedConfig.INSTANCE.productionConfig.energyPerCookTime;

    return (int) Math.min(energy, Integer.MAX_VALUE);
  }

  @Nonnull
  public IItemHandler inputIItemHandler() {
    return inputSubset;
  }

  @Nonnull
  public IItemHandler outputIItemHandler() {
    return outputSubset;
  }


}
