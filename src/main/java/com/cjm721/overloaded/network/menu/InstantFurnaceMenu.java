package com.cjm721.overloaded.network.menu;

import com.cjm721.overloaded.storage.crafting.FurnaceProcessor;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class InstantFurnaceMenu extends ModMenu {

  private final Inventory playerInventory;

  private final FurnaceProcessor processor;
  private final DataSlot power;
  private final DataSlot maxPower;

  public InstantFurnaceMenu(int id, Inventory playerInventory) {
    this(id, playerInventory, new FurnaceProcessor(() -> null, Integer.MAX_VALUE, 9, () -> {}));
  }

  public InstantFurnaceMenu(int id, Inventory playerInventory, FurnaceProcessor processor) {
    super(ModMenus.INSTANT_FURNACE.get(), id);
    this.playerInventory = playerInventory;
    this.processor = processor;
    this.power =
        new DataSlot() {

          @Override
          public int get() {
            return getPowerFromTE();
          }

          @Override
          public void set(int amount) {
            processor.setCurrentEnergy(amount);
          }
        };
    maxPower = DataSlot.standalone();

    int slotCount = 0;
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        this.addSlot(new SlotItemHandler(processor, slotCount++, 8 + j * 18, 20 + i * 18));
      }
    }

    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        this.addSlot(
            new FurnaceResultSlot(
                playerInventory.player,
                new SimpleContainer(18),
                slotCount++,
                116 + j * 18,
                20 + i * 18));
      }
    }

    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 9; ++j) {
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 108 + i * 18));
      }
    }

    for (int k = 0; k < 9; ++k) {
      this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 166));
    }

    this.addDataSlot(power).set(getPowerFromTE());
    this.addDataSlot(maxPower).set(getMaxPowerFromTE());
  }

  //  @Override
  //  @Nonnull
  //  public ItemStack quickMoveStack(Player playerIn, int index) {
  //    return ContainerUtil.transferStackInSlot(playerIn, index, this);
  //  }

  @Override
  public ItemStack quickMoveStack(Player player, int index) {
    ItemStack stack = ItemStack.EMPTY;
    Slot slot = this.slots.get(index);

    if (slot != null && slot.hasItem()) {
      ItemStack stackInSlot = slot.getItem();
      stack = stackInSlot.copy();

      int containerSize = this.slots.size() - this.playerInventory.getContainerSize();

      // If the slot is in the container (not the player's inventory)
      if (index < containerSize) {
        // Try to move the item into the player's inventory
        if (!this.moveItemStackTo(stackInSlot, containerSize, this.slots.size(), true)) {
          return ItemStack.EMPTY;
        }
      }
      // If the slot is in the player's inventory
      else if (!this.moveItemStackTo(stackInSlot, 0, containerSize, false)) {
        return ItemStack.EMPTY;
      }

      if (stackInSlot.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }

      if (stackInSlot.getCount() == stack.getCount()) {
        return ItemStack.EMPTY;
      }

      slot.onTake(player, stackInSlot);
    }

    return stack;
  }

  @Override
  public boolean stillValid(@Nonnull Player playerIn) {
    return true;
  }

  public int getPowerFromTE() {
    return processor.getEnergyStored();
  }

  public int getMaxPowerFromTE() {
    return processor.getMaxEnergyStored();
  }

  //  @Override
  //  public void broadcastChanges() {
  //    super.broadcastChanges();
  //    //    int j;
  //    //    for (j = 0; j < this.slots.size(); ++j) {
  //    //      ItemStack itemstack = this.slots.get(j).getItem();
  //    //      itemstack = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
  //    //      this.setItem(j, 0, itemstack);
  //
  //    //        for (IContainerListener icontainerlistener : this.containerListeners) {
  //    //          icontainerlistener.slotChanged(this, j, itemstack);
  //    //        }
  //    //    }
  //    this.sendAllDataToRemote();
  //    //    ContainerDataMessage message = new ContainerDataMessage(this.containerId);
  //    //    for (j = 0; j < this.slots.size(); ++j) {
  //    //            DataSlot intreferenceholder = this.data.get(j);
  //    //            if (intreferenceholder.checkAndClearUpdateFlag()) {
  //    //              message.addData(j, intreferenceholder.get());
  //    //            }
  //    //    }
  //    //
  //    //    if (!message.getData().isEmpty()) {
  //    //      //      for (IContainerListener listener : this.containerListeners) {
  //    //      //        if (listener instanceof ServerPlayerEntity) {
  //    //      //          PacketDistributor.send(
  //    //      //              PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) listener),
  //    // message);
  //    //      //        }
  //    //      //      }
  //    //    }
  //  }
}
