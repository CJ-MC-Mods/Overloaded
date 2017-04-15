package com.cjm721.overloaded.common.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileCreativeGeneratorFE extends TileEntity implements ITickable, IEnergyStorage {

    private boolean normalTicks;

    @Nonnull
    private Map<EnumFacing,IEnergyStorage> cache;

    public TileCreativeGeneratorFE() {
        cache = new HashMap<>();
        normalTicks = false;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if(!normalTicks) {
            onPlace();
        }

        cache.values().stream().forEach(te -> {
            te.receiveEnergy(Integer.MAX_VALUE, false);
        });
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == ENERGY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ENERGY || super.hasCapability(capability, facing);
    }

    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive Maximum amount of energy to be inserted.
     * @param simulate   If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract Maximum amount of energy to be extracted.
     * @param simulate   If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted from the storage.
     */
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return maxExtract;
    }

    /**
     * Returns the amount of energy currently stored.
     */
    @Override
    public int getEnergyStored() {
        return Integer.MAX_VALUE;
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    @Override
    public int getMaxEnergyStored() {
        return Integer.MAX_VALUE;
    }

    /**
     * Returns if this storage can have energy extracted.
     * If this is false, then any calls to extractEnergy will return 0.
     */
    @Override
    public boolean canExtract() {
        return true;
    }

    /**
     * Used to determine if this storage can receive energy.
     * If this is false, then any calls to receiveEnergy will return 0.
     */
    @Override
    public boolean canReceive() {
        return false;
    }

    private void onNeighborChange(@Nonnull BlockPos neighbor) {
        TileEntity te = this.getWorld().getTileEntity(neighbor);

        BlockPos sidePos = this.getPos().subtract(neighbor);
        EnumFacing side = EnumFacing.getFacingFromVector(sidePos.getX(), sidePos.getY(), sidePos.getZ());

        cache.remove(side);
        if(te != null) {
            if(te.hasCapability(ENERGY, side)) {
                cache.put(side, te.getCapability(ENERGY,side));
            }
        }
    }

    public void onPlace() {
        Arrays.stream(EnumFacing.values()).forEach((side) -> onNeighborChange(this.getPos().add(side.getDirectionVec())));
    }
}
