package com.cjm721.overloaded.common.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

/**
 * Created by CJ on 4/13/2017.
 */
public class TileEnergyExtractor extends TileEntity implements ITickable {

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        BlockPos me = this.getPos();
        for(EnumFacing facing: EnumFacing.values()) {
            TileEntity te = world.getTileEntity(me.add(facing.getDirectionVec()));
            if(te == null)
                continue;

            if(!te.hasCapability(ENERGY, facing.getOpposite()))
                continue;

            IEnergyStorage storage = te.getCapability(ENERGY, facing.getOpposite());

            if(!storage.canReceive())
                continue;


        }
    }

}
