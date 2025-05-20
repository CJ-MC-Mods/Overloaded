package com.cjm721.overloaded.storage.crafting;

import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

// TODO Re-Generic this again
public abstract class EnergyInventoryBasedRecipeProcessor
    implements IItemHandlerModifiable, IEnergyStorage {

  //  private final T recipeType;
  private final Supplier<Level> worldSupplier;
  private final int maxEnergy;
  private final int slots;
  @Nonnull private final IDataUpdate dataUpdate;
  //  private final T recipeType;

  @Nonnull private List<ItemStack> input;
  @Nonnull private List<ItemStack> output;
  private int currentEnergy;

  EnergyInventoryBasedRecipeProcessor(
      //      T recipeType,
      Supplier<Level> worldSupplier, int maxEnergy, int slots, @Nonnull IDataUpdate dataUpdate) {
    //    this.recipeType = recipeType;
    this.worldSupplier = worldSupplier;
    this.maxEnergy = maxEnergy;
    this.slots = slots;
    this.dataUpdate = dataUpdate;

    input = new ArrayList<>();
    output = new ArrayList<>();
    currentEnergy = 0;
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    int energyReceived = Math.min(maxEnergy - currentEnergy, maxReceive);
    if (!simulate) {
      currentEnergy += energyReceived;
      tryProcessRecipes(!isOnServer());
    }
    return energyReceived;
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    int energyExtracted = Math.min(currentEnergy, maxExtract);
    if (!simulate) currentEnergy -= energyExtracted;
    return energyExtracted;
  }

  @Override
  public int getEnergyStored() {
    return currentEnergy;
  }

  public NonNullList<ItemStack> getItemsForMenu() {
    NonNullList<ItemStack> items = NonNullList.withSize(slots + slots, ItemStack.EMPTY);
    for (int i = 0; i < input.size(); i++) {
      items.set(i, input.get(i));
    }
    for (int i = 0; i < output.size(); i++) {
      items.set(i + input.size(), output.get(i));
    }
    return items;
  }

  @Override
  public int getMaxEnergyStored() {
    return maxEnergy;
  }

  @Override
  public boolean canExtract() {
    return false;
  }

  @Override
  public boolean canReceive() {
    return true;
  }

  @Override
  public int getSlots() {
    return slots + slots;
  }

  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {
    if (slot < slots && slot < input.size()) {
      return input.get(slot);
    } else if (slot >= slots && slot - slots < output.size()) {
      return output.get(slot - slots);
    }
    return ItemStack.EMPTY;
  }

  @Override
  public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
    this.setItem(slot, stack);
  }

  public ItemStack setItem(int slot, ItemStack stack) {
    ItemStack toReturn;

    if (slot < this.slots) {
      if (slot >= input.size()) {
        if (!stack.isEmpty()) {
          input.add(stack);
        }
        toReturn = ItemStack.EMPTY;
      } else {
        if (stack.isEmpty()) {
          toReturn = input.get(slot);
          input.remove(slot);
        } else {
          toReturn = input.set(slot, stack);
        }
      }
    } else {
      slot -= slots;
      if (slot >= output.size()) {
        if (!stack.isEmpty()) {
          output.add(stack);
        }
        toReturn = ItemStack.EMPTY;
      } else {
        if (stack.isEmpty()) {
          toReturn = output.get(slot);
          output.remove(slot);
        } else {
          toReturn = output.set(slot, stack);
        }
      }
    }

    tryProcessRecipes(!isOnServer());
    return toReturn;
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (slot >= slots) {
      return stack;
    }

    ItemStack returnStack = insertItem(input, slot, stack, simulate);

    if (!simulate && slot < slots && stack.getCount() != returnStack.getCount()) {
      tryProcessRecipes(!isOnServer());
    }
    return returnStack;
  }

  @Nonnull
  private ItemStack insertItem(
      List<ItemStack> inventory, int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (slot >= slots) {
      return stack;
    }

    if (slot >= inventory.size()) {
      if (!simulate) {
        inventory.add(stack);
      }
      return ItemStack.EMPTY;
    } else {
      ItemStack currentItem = inventory.get(slot);
      ItemStack returnStack = stack.copy();

      if (ItemStack.isSameItemSameComponents(currentItem, returnStack)) {
        int originalCount = currentItem.getCount();
        int newCount =
            Math.min(currentItem.getMaxStackSize(), originalCount + returnStack.getCount());
        if (!simulate) {
          currentItem.setCount(newCount);
        }
        returnStack.setCount(returnStack.getCount() + originalCount - newCount);
      }
      return returnStack;
    }
  }

  private ItemStack insertItem(List<ItemStack> container, ItemStack stack, boolean simulate) {
    ItemStack inFlightStack = stack.copy();

    for (int i = 0; i < container.size(); i++) {
      if (inFlightStack.isEmpty()) {
        break;
      }

      ItemStack storedStack = container.get(i).copy();

      if (storedStack.isEmpty()) {
        if (!simulate) {
          container.set(i, inFlightStack.copy());
        }
        inFlightStack = ItemStack.EMPTY;
      } else if (ItemStack.isSameItemSameComponents(storedStack, inFlightStack)) {
        int originalCount = storedStack.getCount();
        int newCount =
            Math.min(storedStack.getMaxStackSize(), originalCount + inFlightStack.getCount());
        if (!simulate) {
          storedStack.setCount(newCount);
          container.set(i, storedStack.copy());
        }
        inFlightStack.setCount(inFlightStack.getCount() + originalCount - newCount);
      }
    }

    if (!inFlightStack.isEmpty() && container.size() < slots) {
      if (!simulate) {
        container.add(inFlightStack.copy());
      }

      return ItemStack.EMPTY;
    }
    return inFlightStack;
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    if (!simulate) {
      //      return ContainerHelper.removeItem(
      //          slot < slots ? input : output, slot < slots ? slot : slot - slots, amount);
    }
    ItemStack toReturn;
    if (slot < slots) {
      if (input.size() > slot) {
        toReturn = input.get(slot);
      } else {
        toReturn = ItemStack.EMPTY;
      }
    } else if (slot - slots < output.size()) {
      toReturn = output.get(slot - slots);
    } else {
      toReturn = ItemStack.EMPTY;
    }
    toReturn = toReturn.copy();

    toReturn.setCount(Math.min(toReturn.getCount(), amount));

    if (!simulate && toReturn.getCount() != 0) {
      if (slot >= slots) {
        // Output was modified, may be able to process more items now
        tryProcessRecipes(!isOnServer());
      }
      dataUpdate.dataUpdated();
    }
    return toReturn;
  }

  @Override
  public int getSlotLimit(int slot) {
    if (slot < slots && slot < input.size()) {
      return input.get(slot).getMaxStackSize();
    } else if (slot >= slots && slot - slots < output.size()) {
      return output.get(slot - slots).getMaxStackSize();
    }
    return 64; // Magic Number from Vanilla Minecraft. Zero would make more sense
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return slot < slots;
  }

  //  @Override
  //  public CompoundTag serializeNBT() {
  //    CompoundTag storage = new CompoundTag();
  //    storage.put("Input", NBTHelper.serializeItems(input));
  //    storage.put("Output", NBTHelper.serializeItems(output));
  //    storage.putInt("Energy", currentEnergy);
  //    return storage;
  //  }
  //
  //  @Override
  //  public void deserializeNBT(CompoundTag nbt) {
  //    if (nbt.contains("Input")) {
  //      input = NBTHelper.deserializeItems(nbt.getList("Input", 10));
  //    }
  //
  //    if (nbt.contains("Output")) {
  //      output = NBTHelper.deserializeItems(nbt.getList("Output", 10));
  //    }
  //
  //    currentEnergy = nbt.getInt("Energy");
  //  }
  //
  public void setCurrentEnergy(int energy) {
    this.currentEnergy = energy;
  }

  private boolean isOnServer() {
    return worldSupplier != null
        && worldSupplier.get() != null
        && !worldSupplier.get().isClientSide;
  }

  private void tryProcessRecipes(boolean simulate) {
    if (!isOnServer()) {
      return;
    }
    for (int i = Math.min(slots, input.size()) - 1; i >= 0; i--) {
      ItemStack leftOvers = processRecipeAndStoreOutput(input.get(i), simulate);

      if (!simulate) {
        if (leftOvers.isEmpty()) {
          input.remove(i);
        } else {
          input.set(i, leftOvers);
        }
      }
    }

    this.dataUpdate.dataUpdated();
  }

  private List<RecipeHolder<SmeltingRecipe>> getRecipe(SingleRecipeInput inventory) {
    return this.worldSupplier
        .get()
        .getRecipeManager()
        .getRecipesFor(RecipeType.SMELTING, inventory, this.worldSupplier.get());
  }

  private ItemStack processRecipeAndStoreOutput(ItemStack stack, boolean simulate) {
    SingleRecipeInput inventory = new SingleRecipeInput(stack.copy());

    List<RecipeHolder<SmeltingRecipe>> recipesForInput = getRecipe(inventory);

    if (recipesForInput.isEmpty()) {
      return inventory.getItem(0);
    }

    RecipeHolder<SmeltingRecipe> recipe = recipesForInput.get(0);

    ItemStack result;
    while (!inventory.getItem(0).isEmpty()
        && !(result = recipe.value().assemble(inventory, worldSupplier.get().registryAccess()))
            .isEmpty()) {
      int energyCost = energyCostPerRecipeOperation(recipe);
      if (energyCost != this.extractEnergy(energyCost, true)) {
        break;
      }

      ItemStack outputLeftOvers = insertItem(output, result, true);

      // TODO incorporate: recipe.getRemainingItems(inventory)

      if (!outputLeftOvers.isEmpty()) {
        break;
      }
      insertItem(output, result, simulate);
      this.extractEnergy(energyCost, simulate);
      int deduct = recipe.value().getIngredients().get(0).getItems()[0].getCount();
      inventory.getItem(0).shrink(deduct);
    }

    return inventory.getItem(0);
    //    NonNullList<ItemStack> leftOvers = recipe.getRemainingItems(inventory);
    //
    //    if (leftOvers.isEmpty() || leftOvers.stream().allMatch(ItemStack::isEmpty)) {
    //      return ItemStack.EMPTY;
    //    }
    //    if (leftOvers.size() > 1) {
    //      Overloaded.logger.warn(
    //          "Deleting Item due to to many recipe leftovers. Items: "
    //              + leftOvers.subList(1, leftOvers.size() - 1).stream()
    //              .map(ItemStack::toString)
    //              .collect(Collectors.joining(",")));
    //    }
    //    return leftOvers.get(0);
  }

  abstract int energyCostPerRecipeOperation(RecipeHolder<SmeltingRecipe> recipe);
}
