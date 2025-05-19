package com.cjm721.overloaded.storage.crafting;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.storage.item.SubsetItemHandlerWrapper;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.UnknownNullability;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class FurnaceProcessor extends EnergyInventoryBasedRecipeProcessor<
        SmeltingRecipe> {

  @Nonnull
  private final SubsetItemHandlerWrapper inputSubset;
  @Nonnull
  private final SubsetItemHandlerWrapper outputSubset;

  public FurnaceProcessor(
          Supplier<Level> worldSupplier, int maxEnergy, int slots, @Nonnull IDataUpdate dataUpdate) {
    super(worldSupplier, maxEnergy, slots, dataUpdate);

    this.inputSubset = new SubsetItemHandlerWrapper(this, 0, slots);
    this.outputSubset = new SubsetItemHandlerWrapper(this, slots, slots);
  }

  @Override
  int energyCostPerRecipeOperation(SmeltingRecipe recipe) {
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


  @Override
  public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
    return null;
  }

  @Override
  public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {

  }
}
