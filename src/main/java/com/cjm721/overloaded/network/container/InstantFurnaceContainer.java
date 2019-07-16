package com.cjm721.overloaded.network.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.Slot;

public class InstantFurnaceContainer extends Container {

  private final PlayerInventory playerInventory;
  private final IInventory furnanceInventory;

  public InstantFurnaceContainer(int id, PlayerInventory playerInventory) {
    this(id,playerInventory, new Inventory(18));
  }

  public InstantFurnaceContainer(
      int id, PlayerInventory playerInventory, IInventory furnanceInventory) {
    super(ModContainers.INSTANT_FURNACE, id);
    this.playerInventory = playerInventory;
    this.furnanceInventory = furnanceInventory;

    int slotCount = 0;
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        this.addSlot(new Slot(furnanceInventory, slotCount++, 8 + j * 18, 17 + i * 18));
      }
    }

    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        this.addSlot(
            new FurnaceResultSlot(
                playerInventory.player, furnanceInventory, slotCount++, 116 + j * 18, 17 + i * 18));
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
  }

  @Override
  public boolean canInteractWith(PlayerEntity playerIn) {
    // TODO Probbaly should have some security on this.
    return true;
  }
}
