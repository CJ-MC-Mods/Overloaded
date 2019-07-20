package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.network.container.InstantFurnaceContainer;
import com.cjm721.overloaded.storage.crafting.EnergyInventoryBasedRecipeProcessor;
import com.cjm721.overloaded.storage.crafting.FurnaceProcessor;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileInstantFurnace extends LockableTileEntity implements IDataUpdate {

  @Nonnull private final EnergyInventoryBasedRecipeProcessor processingStorage;
  @Nonnull private final LazyOptional<?> capability;

  public TileInstantFurnace() {
    super(ModTiles.instantFurnace);

    processingStorage = new FurnaceProcessor(this::getWorld, Integer.MAX_VALUE, 9, this);
    capability = LazyOptional.of(() -> processingStorage);
  }

  @Override
  @Nonnull
  protected ITextComponent getDefaultName() {
    return new StringTextComponent("Instant Furnace");
  }

  @Override
  @Nonnull
  protected Container createMenu(int id, @Nonnull PlayerInventory playerInventory) {
    return new InstantFurnaceContainer(id, playerInventory, this);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || cap == ENERGY) {
      return capability.cast();
    }

    return super.getCapability(cap, side);
  }

  @Override
  public int getSizeInventory() {
    return processingStorage.getSlots();
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  @Nonnull
  public ItemStack getStackInSlot(int index) {
    return processingStorage.getStackInSlot(index);
  }

  @Override
  @Nonnull
  public ItemStack decrStackSize(int index, int count) {
    return processingStorage.extractItem(index, count, false);
  }

  @Override
  @Nonnull
  public ItemStack removeStackFromSlot(int index) {
    return processingStorage.extractItem(index, Integer.MAX_VALUE, false);
  }

  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {
    processingStorage.setItem(index, stack);
  }

  @Override
  public boolean isUsableByPlayer(PlayerEntity player) {
    // TODO Do I want to make sure the player is nearby?
    return true;
  }

  @Override
  public void clear() {
    throw new RuntimeException("clear is called");
  }

  @Override
  public void read(CompoundNBT compound) {
    super.read(compound);
    if (compound.contains("Processor")) {
      processingStorage.deserializeNBT((CompoundNBT) compound.get("Processor"));
    }
  }

  @Override
  @Nonnull
  public CompoundNBT write(CompoundNBT compound) {
    compound.put("Processor", processingStorage.serializeNBT());
    return super.write(compound);
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }
}
