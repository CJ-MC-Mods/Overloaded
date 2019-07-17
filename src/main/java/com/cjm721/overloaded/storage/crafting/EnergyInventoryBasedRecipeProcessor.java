package com.cjm721.overloaded.storage.crafting;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.tile.functional.TileInstantFurnace;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class EnergyInventoryBasedRecipeProcessor<
        T extends net.minecraft.item.crafting.IRecipe<IInventory>>
    implements IItemHandler, IEnergyStorage, INBTSerializable<CompoundNBT> {

  private final IRecipeType<T> recipeType;
  private final TileEntity tileEntity;
  private final int maxEnergy;
  private final int inputSlots, outputSlots;
  @Nonnull private final IDataUpdate dataUpdate;

  @Nonnull NonNullList<ItemStack> input;
  @Nonnull NonNullList<ItemStack> output;
  private int currentEnergy;

  public EnergyInventoryBasedRecipeProcessor(
      IRecipeType recipeType,
      TileInstantFurnace tileEntity,
      int maxEnergy,
      int inputSlots,
      int outputSlots,
      @Nonnull IDataUpdate dataUpdate) {
    this.recipeType = recipeType;
    this.tileEntity = tileEntity;
    this.maxEnergy = maxEnergy;
    this.inputSlots = inputSlots;
    this.outputSlots = outputSlots;
    this.dataUpdate = dataUpdate;

    input = NonNullList.withSize(inputSlots, ItemStack.EMPTY);
    output = NonNullList.withSize(outputSlots, ItemStack.EMPTY);
    currentEnergy = 0;
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    int energyReceived = Math.min(maxEnergy - currentEnergy, maxReceive);
    if (!simulate) {
      currentEnergy += energyReceived;
      tryProcessRecipes();
    }
    return energyReceived;
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    return 0;
  }

  @Override
  public int getEnergyStored() {
    return currentEnergy;
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
    return inputSlots + outputSlots;
  }

  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {
    return slot < inputSlots ? input.get(slot) : output.get(slot - inputSlots);
  }

  public ItemStack setItem(int slot, ItemStack stack) {
    if (tileEntity != null && tileEntity.getWorld() != null && !tileEntity.getWorld().isRemote) {
      Inventory tempInv = new Inventory(stack);
      stack = processRecipeAndStoreOutput(getRecipe(tempInv), tempInv, false);
    }

    ItemStack toReturn =
        slot < this.inputSlots ? input.set(slot, stack) : output.set(slot - inputSlots, stack);

    return toReturn;
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (slot >= inputSlots) {
      return stack;
    }

    Inventory tempInventory = new Inventory(stack);

    List<T> recipesForInput = getRecipe(tempInventory);
    if (recipesForInput.isEmpty()) {
      return stack;
    }

    processRecipeAndStoreOutput(recipesForInput, tempInventory, simulate);

    ItemStack returnStack = insertItem(input, slot, stack, simulate);

    if (!simulate && slot < inputSlots && stack.getCount() != returnStack.getCount()) {
      tryProcessRecipes();
    }
    return returnStack;
  }

  public ItemStack insertItem(NonNullList<ItemStack> container, ItemStack stack, boolean simulate) {
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
      } else if (ItemHandlerHelper.canItemStacksStack(storedStack, inFlightStack)) {
        int origionalCount = storedStack.getCount();
        int newCount = Math.min(storedStack.getMaxStackSize(), origionalCount + inFlightStack.getCount());
        if (!simulate) {
          storedStack.setCount(newCount);
          container.set(i, storedStack.copy());
        }
        inFlightStack.setCount(inFlightStack.getCount() + origionalCount - newCount);
      }
    }
    return inFlightStack;
  }

  private ItemStack processRecipeAndStoreOutput(
      List<T> recipesForInput, Inventory inventory, boolean simulated) {
    if(recipesForInput.isEmpty()) {
      return inventory.getStackInSlot(0);
    }

    T recipe = recipesForInput.get(0);

    ItemStack result;
    while(!inventory.getStackInSlot(0).isEmpty() && !(result = recipe.getCraftingResult(inventory)).isEmpty()) {
      ItemStack outputLeftOvers = insertItem(output, result, true);
      // TODO incorporate: recipe.getRemainingItems(inventory)

      if(!outputLeftOvers.isEmpty()) {
        break;
      }
      insertItem(output, result, false);
      int deduct = recipe.getIngredients().get(0).getMatchingStacks()[0].getCount();
      inventory.getStackInSlot(0).shrink(deduct);
    }


    NonNullList<ItemStack> leftOvers = recipe.getRemainingItems(inventory);

    if (leftOvers.isEmpty() || leftOvers.stream().allMatch(ItemStack::isEmpty)) {
      return ItemStack.EMPTY;
    }
    if (leftOvers.size() > 1) {
      Overloaded.logger.warn(
          "Deleting Item due to to many recipe leftovers. Items: "
              + leftOvers.subList(1, leftOvers.size() - 1).stream()
                  .map(ItemStack::toString)
                  .collect(Collectors.joining(",")));
    }
    return leftOvers.get(0);
  }

  private List<T> getRecipe(Inventory inventory) {
    return this.tileEntity
        .getWorld()
        .getRecipeManager()
        .getRecipes(recipeType, inventory, this.tileEntity.getWorld());
  }

  @Nonnull
  private ItemStack insertItem(
      NonNullList<ItemStack> inventory, int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (simulate) {
      return inventory.get(slot);
    } else {
      return inventory.set(slot, stack);
    }
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    if (!simulate) {
      return ItemStackHelper.getAndSplit(
          slot < inputSlots ? input : output, slot < inputSlots ? slot : slot - inputSlots, amount);
    }
    ItemStack toReturn =
        (slot < inputSlots ? input.get(slot) : output.get(slot - inputSlots)).copy();

    toReturn.setCount(Math.min(toReturn.getCount(), amount));

    if (!simulate && toReturn.getCount() != 0) {
      if (slot >= inputSlots) {
        // Output was modified, may be able to process more items now
        tryProcessRecipes();
      }
      dataUpdate.dataUpdated();
    }
    return toReturn;
  }

  @Override
  public int getSlotLimit(int slot) {
    return slot < inputSlots
        ? input.get(slot).getMaxStackSize()
        : output.get(slot - inputSlots).getMaxStackSize();
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return slot < inputSlots;
  }

  @Override
  public CompoundNBT serializeNBT() {
    CompoundNBT storage = new CompoundNBT();
    CompoundNBT inputNBT = new CompoundNBT();
    CompoundNBT outputNBT = new CompoundNBT();
    ItemStackHelper.saveAllItems(inputNBT, input);
    ItemStackHelper.saveAllItems(outputNBT, output);
    storage.put("Input", inputNBT);
    storage.put("Output", outputNBT);
    storage.putInt("Energy", currentEnergy);
    return storage;
  }

  @Override
  public void deserializeNBT(CompoundNBT nbt) {
    if (nbt.contains("Input")) {
      ItemStackHelper.loadAllItems(nbt.getCompound("Input"), input);
    }

    if (nbt.contains("Output")) {
      ItemStackHelper.loadAllItems(nbt.getCompound("Output"), output);
    }

    currentEnergy = nbt.getInt("Energy");
  }

  public void setCurrentEnergy(int energy) {
    this.currentEnergy = energy;
  }

  private void tryProcessRecipes() {
    this.dataUpdate.dataUpdated();
  }
}
