package com.cjm721.overloaded.block.tile.hyperTransfer.base;

import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

public abstract class AbstractTileHyperReceiver<Type extends IHyperType, H extends IHyperHandler<Type>> extends TileEntity {

    private final Capability<H> capability;

    protected AbstractTileHyperReceiver(Capability<H> capability) {
        this.capability = capability;
    }

    @Nonnull
    public Type receive(@Nonnull Type stack) {
        for (EnumFacing side : EnumFacing.values()) {
            TileEntity te = this.getWorld().getTileEntity(this.getPos().add(side.getDirectionVec()));

            if (te == null || !te.hasCapability(capability, side.getOpposite()))
                continue;

            IHyperHandler<Type> temp = te.getCapability(capability, side.getOpposite());
            stack = temp.give(stack, true);

            if (stack.getAmount() == 0L)
                return stack;
        }
        return stack;
    }
}
