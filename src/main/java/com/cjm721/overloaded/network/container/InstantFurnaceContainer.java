package com.cjm721.overloaded.network.container;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.storage.crafting.EnergyInventoryBasedRecipeProcessor;
import com.cjm721.overloaded.tile.functional.TileInstantFurnace;
import com.cjm721.overloaded.util.ContainerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class InstantFurnaceContainer extends Container {

  private final PlayerInventory playerInventory;
  private final TileInstantFurnace instanceFurnace;
  private final IntReferenceHolder power;
  private final IntReferenceHolder maxPower;

  public InstantFurnaceContainer(int id, PlayerInventory playerInventory) {
    this(id, playerInventory, new TileInstantFurnace());
    this.instanceFurnace.setWorld(playerInventory.player.world);
  }

  public InstantFurnaceContainer(
      int id, PlayerInventory playerInventory, TileInstantFurnace instanceFurnace) {
    super(ModContainers.INSTANT_FURNACE, id);
    this.playerInventory = playerInventory;
    this.instanceFurnace = instanceFurnace;
    this.power =
        new IntReferenceHolder() {

          @Override
          public int get() {
            return getPowerFromTE();
          }

          @Override
          public void set(int amount) {
            InstantFurnaceContainer.this
                .instanceFurnace
                .getCapability(ENERGY)
                .ifPresent(e -> ((EnergyInventoryBasedRecipeProcessor) e).setCurrentEnergy(amount));
          }
        };
    maxPower = IntReferenceHolder.single();

    IItemHandler handler = instanceFurnace.getCapability(ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new IllegalStateException("No Item Handler Capability found"));

    int slotCount = 0;
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        this.addSlot(new SlotItemHandler(handler, slotCount++, 8 + j * 18, 8 + i * 18));
      }
    }

    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        this.addSlot(
            new FurnaceResultSlot(
                playerInventory.player, instanceFurnace, slotCount++, 116 + j * 18, 8 + i * 18));
      }
    }

    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 9; ++j) {
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }

    for (int k = 0; k < 9; ++k) {
      this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
    }

    this.trackInt(power);
    this.trackInt(maxPower).set(getMaxPowerFromTE());
  }

  @Override
  @Nonnull
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
    return ContainerUtil.transferStackInSlot(playerIn, index, this);
  }

  @Override
  public boolean canInteractWith(PlayerEntity playerIn) {
    return isWithinUsableDistance(
        IWorldPosCallable.of(instanceFurnace.getWorld(), instanceFurnace.getPos()),
        playerIn,
        ModBlocks.instantFurnace);
  }

  public int getPower() {
    return power.get();
  }

  public int getMaxPower() {
    return maxPower.get();
  }

  private int getPowerFromTE() {
    return instanceFurnace.getCapability(ENERGY).map(e -> e.getEnergyStored()).orElse(0);
  }

  private int getMaxPowerFromTE() {
    return instanceFurnace.getCapability(ENERGY).map(e -> e.getMaxEnergyStored()).orElse(1);
  }

  @Override
  public void detectAndSendChanges() {
    super.detectAndSendChanges();
  }

  @Override
  public void addListener(IContainerListener p_75132_1_) {
    super.addListener(p_75132_1_);
  }
}
