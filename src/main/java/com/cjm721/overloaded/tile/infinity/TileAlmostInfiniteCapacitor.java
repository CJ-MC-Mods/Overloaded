package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

public class TileAlmostInfiniteCapacitor extends AbstractTileHyperStorage<LongEnergyStorage> implements IDataUpdate {

    @Nonnull
    private final LongEnergyStorage energyStorage;

    public TileAlmostInfiniteCapacitor(BlockPos pos, BlockState state) {
        super(ModTiles.almostInfiniteCapacitor.get(), pos, state);
        energyStorage = new LongEnergyStorage(this);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        CompoundTag energy = energyStorage.serializeNBT(registries);
        compound.put("LongEnergyStorage", energy);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        if (compound.contains("LongEnergyStorage")) {
            energyStorage.deserializeNBT(registries, (CompoundTag) compound.get("LongEnergyStorage"));
        }
    }

    @Override
    @Nonnull
    public LongEnergyStorage getStorage() {
        return energyStorage;
    }

    @Override
    public void dataUpdated() {
        setChanged();
    }
}
