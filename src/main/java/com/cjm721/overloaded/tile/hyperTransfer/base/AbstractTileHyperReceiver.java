package com.cjm721.overloaded.tile.hyperTransfer.base;

import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;

import javax.annotation.Nonnull;

public abstract class AbstractTileHyperReceiver<Type extends IHyperType, H extends IHyperHandler<Type>> extends BlockEntity {

    private final BlockCapability<H, Direction> capability;

    protected AbstractTileHyperReceiver(BlockEntityType<?> type, BlockCapability<H, Direction> capability, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.capability = capability;
    }

    @Nonnull
    public Type receive(@Nonnull Type stack) {
        for (Direction side : Direction.values()) {
            BlockEntity te = this.getLevel().getBlockEntity(this.getBlockPos().offset(side.getNormal()));

            if (te == null) {
                continue;
            }

            H cap = level.getCapability(capability, te.getBlockPos(), side.getOpposite());

            if (cap == null) {
                continue;
            }
            stack = cap.give(stack, true);

            if (stack.getAmount().longValue() == 0L)
                return stack;
        }
        return stack;
    }

    @Override
    public boolean isValidBlockState(BlockState p_353131_) {
        // TODO What is this supose to check
        return true;
    }
}
