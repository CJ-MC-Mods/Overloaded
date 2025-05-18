package com.cjm721.overloaded.tile.hyperTransfer.base;

import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.util.LazyOptional;

import javax.annotation.Nonnull;

public abstract class AbstractTileHyperReceiver<Type extends IHyperType, H extends IHyperHandler<Type>> extends BlockEntity {

    private final Capability<H> capability;

    protected AbstractTileHyperReceiver(BlockEntityType<?> type, Capability<H> capability, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.capability = capability;
    }

    @Nonnull
    public Type receive(@Nonnull Type stack) {
        for (Direction side : Direction.values()) {
            TileEntity te = this.getLevel().getBlockEntity(this.getBlockPos().offset(side.getNormal()));

            if (te == null) {
                continue;
            }

            LazyOptional<H> cap = te.getCapability(capability, side.getOpposite());

            if (!cap.isPresent()) {
                continue;
            }
            stack = cap.orElse(null).give(stack, true);

            if (stack.getAmount().longValue() == 0L)
                return stack;
        }
        return stack;
    }
}
