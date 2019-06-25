package com.cjm721.overloaded.tile.hyperTransfer.base;

import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public abstract class AbstractTileHyperReceiver<Type extends IHyperType, H extends IHyperHandler<Type>> extends TileEntity {

    private final Capability<H> capability;

    protected AbstractTileHyperReceiver(TileEntityType<?> type, Capability<H> capability) {
        super(type);
        this.capability = capability;
    }

    @Nonnull
    public Type receive(@Nonnull Type stack) {
        for (Direction side : Direction.values()) {
            TileEntity te = this.getWorld().getTileEntity(this.getPos().add(side.getDirectionVec()));

            LazyOptional<H> cap = te.getCapability(capability, side.getOpposite());

            if (!cap.isPresent()) {
                continue;
            }
            stack = cap.orElse(null).give(stack, true);

            if (stack.getAmount() == 0L)
                return stack;
        }
        return stack;
    }
}
