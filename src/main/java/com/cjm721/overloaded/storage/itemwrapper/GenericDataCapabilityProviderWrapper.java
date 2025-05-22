package com.cjm721.overloaded.storage.itemwrapper;

import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.storage.GenericDataStorage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Map;

public class GenericDataCapabilityProviderWrapper implements IGenericDataStorage {
  @Nonnull private final ItemStack stack;
  @Nonnull private final GenericDataStorage.GenericData data;

  public GenericDataCapabilityProviderWrapper(@Nonnull ItemStack stack) {
    this.stack = stack;
    this.data =
        this.stack.getOrDefault(ModItems.GENERIC_DATA, new GenericDataStorage.GenericData());
  }

  @Override
  public @NotNull Map<String, Integer> getIntegerMap() {
    return data.integerMap();
  }

  @Override
  public @NotNull Map<String, Boolean> getBooleanMap() {
    return data.booleanMap();
  }

  @Override
  public @NotNull Map<String, Double> getDoubleMap() {
    return data.doubleMap();
  }

  @Override
  public @NotNull Map<String, Float> getFloatMap() {
    return data.floatMap();
  }

  @Override
  public void suggestUpdate() {
    this.stack.getOrDefault(ModItems.GENERIC_DATA, new GenericDataStorage.GenericData());
  }

  @Override
  public void suggestSave() {
    this.stack.set(ModItems.GENERIC_DATA, data);
  }
}
