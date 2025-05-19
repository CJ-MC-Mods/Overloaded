package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

import static net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.BLOCK;
import static net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.ITEM;

public class TileEnergyInjectorChest extends AbstractTileEntityFaceable {

    public TileEnergyInjectorChest(BlockPos pos, BlockState blockState) {
        super(ModTiles.energyInjectorChest.get(), pos, blockState);
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void tick() {
        if (this.getLevel().isClientSide) {
            return;
        }

        BlockPos me = this.getBlockPos();
        BlockEntity frontTE = getLevel().getBlockEntity(me.offset(getFacing().getNormal()));

        @Nullable IEnergyStorage storage = getLevel()
                .getCapability(BLOCK, frontTE.getBlockPos(), getFacing().getOpposite());
        AtomicInteger energy =
                new AtomicInteger(storage.extractEnergy(Integer.MAX_VALUE, false));
        for (Direction facing : Direction.values()) {
            if (energy.get() == 0) return;

            if (facing == getFacing()) continue;

            BlockEntity te = level.getBlockEntity(me.offset(facing.getNormal()));

            @Nullable IItemHandler inventory = level.getCapability(Capabilities.ItemHandler.BLOCK, te.getBlockPos(), facing.getOpposite());
            for (int i = 0; i < inventory.getSlots(); i++) {
                final ItemStack stack = inventory.getStackInSlot(i);
                final int slot = i;

                @Nullable IEnergyStorage receiver = stack
                        .getCapability(ITEM);
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

                getLevel()
                        .addFreshEntity(
                                new ItemEntity(
                                        getLevel(),
                                        getBlockPos().getX(),
                                        getBlockPos().getY(),
                                        getBlockPos().getZ(),
                                        tempStack));
            }
        }
    }
}
