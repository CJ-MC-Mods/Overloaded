package com.cjm721.overloaded.block.tile;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.block.basic.BlockEnergyInjectorChest;
import com.cjm721.overloaded.block.basic.BlockInfiniteWaterSource;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileEnergyInjectorChest extends AbstractTileEntityFaceable
    implements ITickableTileEntity {

  public TileEnergyInjectorChest() {
    super(
        TileEntityType.Builder.create(
            TileEnergyInjectorChest::new, ModBlocks.energyInjectorChest)
            .build(null));
  }

  /** Like the old updateEntity(), except more generic. */
  @Override
  public void tick() {
    if (this.getWorld().isRemote) {
      return;
    }

    BlockPos me = this.getPos();
    TileEntity frontTE = getWorld().getTileEntity(me.add(getFacing().getDirectionVec()));

    frontTE
        .getCapability(ENERGY, getFacing().getOpposite())
        .ifPresent(
            storage -> {
              AtomicInteger energy =
                  new AtomicInteger(storage.extractEnergy(Integer.MAX_VALUE, false));
              for (Direction facing : Direction.values()) {
                if (energy.get() == 0) return;

                if (facing == getFacing()) continue;

                TileEntity te = world.getTileEntity(me.add(facing.getDirectionVec()));

                te.getCapability(ITEM_HANDLER_CAPABILITY, facing.getOpposite())
                    .ifPresent(
                        inventory -> {
                          for (int i = 0; i < inventory.getSlots(); i++) {
                            final ItemStack stack = inventory.getStackInSlot(i);
                            final int slot = i;

                            stack
                                .getCapability(ENERGY, facing.getOpposite())
                                .ifPresent(
                                    receiver -> {
                                      ItemStack tempStack = inventory.extractItem(slot, 1, false);

                                      if (tempStack.isEmpty() || !receiver.canReceive()) {
                                        return;
                                      }

                                      int acceptedAmount =
                                          receiver.receiveEnergy(energy.get(), true);
                                      if (acceptedAmount != 0) {
                                        receiver.receiveEnergy(acceptedAmount, false);
                                        energy.addAndGet(
                                            -storage.receiveEnergy(energy.get(), true));
                                      }

                                      tempStack = inventory.insertItem(slot, tempStack, false);

                                      if (tempStack.isEmpty()) {
                                        return;
                                      }

                                      getWorld()
                                          .addEntity(
                                              new ItemEntity(
                                                  getWorld(),
                                                  getPos().getX(),
                                                  getPos().getY(),
                                                  getPos().getZ(),
                                                  tempStack));
                                    });
                          }
                        });
              }
            });
  }
}
