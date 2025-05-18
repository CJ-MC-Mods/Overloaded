package com.cjm721.overloaded.network.container;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.tile.functional.TileInstantFurnace;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class InstantFurnaceContainer extends ModContainer {

  private final Inventory playerInventory;
  private final TileInstantFurnace instanceFurnace;
//  private final IntReferenceHolder power;
//  private final IntReferenceHolder maxPower;

  public InstantFurnaceContainer(
      int id, Inventory playerInventory, TileInstantFurnace instanceFurnace) {
    super(ModContainers.INSTANT_FURNACE.get(), id);
    this.playerInventory = playerInventory;
    this.instanceFurnace = instanceFurnace;
//    this.power =
//        new IntReferenceHolder() {
//
//          @Override
//          public int get() {
//            return getPowerFromTE();
//          }
//
//          @Override
//          public void set(int amount) {
//            InstantFurnaceContainer.this
//                .instanceFurnace
//                .getCapability(ENERGY)
//                .ifPresent(e -> ((EnergyInventoryBasedRecipeProcessor) e).setCurrentEnergy(amount));
//          }
//        };
//    maxPower = IntReferenceHolder.standalone();

//    IItemHandler handler =
//        instanceFurnace
//            .getCapability(ITEM_HANDLER_CAPABILITY)
//            .orElseThrow(() -> new IllegalStateException("No Item Handler Capability found"));
//
//    int slotCount = 0;
//    for (int i = 0; i < 3; ++i) {
//      for (int j = 0; j < 3; ++j) {
//        this.addSlot(new SlotItemHandler(handler, slotCount++, 8 + j * 18, 20 + i * 18));
//      }
//    }
//
//    for (int i = 0; i < 3; ++i) {
//      for (int j = 0; j < 3; ++j) {
//        this.addSlot(
//            new FurnaceResultSlot(
//                playerInventory.player, instanceFurnace, slotCount++, 116 + j * 18, 20 + i * 18));
//      }
//    }
//
//    for (int i = 0; i < 3; ++i) {
//      for (int j = 0; j < 9; ++j) {
//        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 108 + i * 18));
//      }
//    }
//
//    for (int k = 0; k < 9; ++k) {
//      this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 166));
//    }
//
//    this.addDataSlot(power).set(getPowerFromTE());
//    this.addDataSlot(maxPower).set(getMaxPowerFromTE());
  }

//  @Override
//  @Nonnull
//  public ItemStack quickMoveStack(Player playerIn, int index) {
//    return ContainerUtil.transferStackInSlot(playerIn, index, this);
//  }

  @Override
  public ItemStack quickMoveStack(Player player, int index) {
    return null;
  }

  @Override
  public boolean stillValid(@Nonnull Player playerIn) {
    return stillValid(
            null,
        playerIn,
        ModBlocks.instantFurnace.get());
  }
//
//  public int getPowerFromTE() {
//    return instanceFurnace.getCapability(ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
//  }
//
//  public int getMaxPowerFromTE() {
//    return instanceFurnace.getCapability(ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(1);
//  }

//  @Override
//  public void broadcastChanges() {
//    int j;
//    for (j = 0; j < this.slots.size(); ++j) {
//      ItemStack itemstack = this.slots.get(j).getItem();
//      itemstack = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
//      this.lastSlots.set(j, itemstack);
//
//      for (IContainerListener icontainerlistener : this.containerListeners) {
//        icontainerlistener.slotChanged(this, j, itemstack);
//      }
//    }
//
//    ContainerDataMessage message = new ContainerDataMessage(this.containerId);
//    for (j = 0; j < this.dataSlots.size(); ++j) {
//      IntReferenceHolder intreferenceholder = this.dataSlots.get(j);
//      if (intreferenceholder.checkAndClearUpdateFlag()) {
//        message.addData(j, intreferenceholder.get());
//      }
//    }
//
//    if (!message.getData().isEmpty()) {
//      for (IContainerListener listener : this.containerListeners) {
//        if (listener instanceof ServerPlayerEntity) {
//          PacketDistributor.send(
//              PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) listener), message);
//        }
//      }
//    }
//  }
}
