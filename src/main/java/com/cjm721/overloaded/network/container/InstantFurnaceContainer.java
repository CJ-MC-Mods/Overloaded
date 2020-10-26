package com.cjm721.overloaded.network.container;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.network.packets.ContainerDataMessage;
import com.cjm721.overloaded.storage.crafting.EnergyInventoryBasedRecipeProcessor;
import com.cjm721.overloaded.tile.functional.TileInstantFurnace;
import com.cjm721.overloaded.util.ContainerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class InstantFurnaceContainer extends ModContainer {

  private final PlayerInventory playerInventory;
  private final TileInstantFurnace instanceFurnace;
  private final IntReferenceHolder power;
  private final IntReferenceHolder maxPower;

  public InstantFurnaceContainer(int id, PlayerInventory playerInventory) {
    this(id, playerInventory, new TileInstantFurnace());
    this.instanceFurnace.setWorldAndPos(playerInventory.player.world, playerInventory.player.getPosition());
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

    IItemHandler handler =
        instanceFurnace
            .getCapability(ITEM_HANDLER_CAPABILITY)
            .orElseThrow(() -> new IllegalStateException("No Item Handler Capability found"));

    int slotCount = 0;
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        this.addSlot(new SlotItemHandler(handler, slotCount++, 8 + j * 18, 20 + i * 18));
      }
    }

    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        this.addSlot(
            new FurnaceResultSlot(
                playerInventory.player, instanceFurnace, slotCount++, 116 + j * 18, 20 + i * 18));
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

    this.trackInt(power).set(getPowerFromTE());
    this.trackInt(maxPower).set(getMaxPowerFromTE());
  }

  @Override
  @Nonnull
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
    return ContainerUtil.transferStackInSlot(playerIn, index, this);
  }

  @Override
  public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
    return isWithinUsableDistance(
        IWorldPosCallable.of(instanceFurnace.getWorld(), instanceFurnace.getPos()),
        playerIn,
        ModBlocks.instantFurnace);
  }

  public int getPowerFromTE() {
    return instanceFurnace.getCapability(ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
  }

  public int getMaxPowerFromTE() {
    return instanceFurnace.getCapability(ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(1);
  }

  @Override
  public void detectAndSendChanges() {
    int j;
    for (j = 0; j < this.inventorySlots.size(); ++j) {
      ItemStack itemstack = this.inventorySlots.get(j).getStack();
      itemstack = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
      this.inventoryItemStacks.set(j, itemstack);

      for (IContainerListener icontainerlistener : this.listeners) {
        icontainerlistener.sendSlotContents(this, j, itemstack);
      }
    }

    ContainerDataMessage message = new ContainerDataMessage(this.windowId);
    for (j = 0; j < this.trackedIntReferences.size(); ++j) {
      IntReferenceHolder intreferenceholder = this.trackedIntReferences.get(j);
      if (intreferenceholder.isDirty()) {
        message.addData(j, intreferenceholder.get());
      }
    }

    if (!message.getData().isEmpty()) {
      for (IContainerListener listener : this.listeners) {
        if (listener instanceof ServerPlayerEntity) {
          Overloaded.proxy.networkWrapper.send(
              PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) listener), message);
        }
      }
    }
  }
}
