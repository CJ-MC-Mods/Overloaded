package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.infinity.TileInfiniteBarrel;
import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

abstract class AbstractBlockInfiniteContainer extends ModBlock {
    AbstractBlockInfiniteContainer(Properties materialIn) {
        super(materialIn);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    //    @Override
//    @Nonnull
//    public final List<ItemStack> getDrops(BlockState state, ServerWorld world, BlockPos pos, @Nullable TileEntity te, Entity breaker, ItemStack breakingItem) {
//        IHyperType stack = getHyperStack(world, pos);
//
//        if (stack != null && stack.getAmount() != 0) {
//            ItemStack toDrop = new ItemStack(this, 1);
//
//            CompoundNBT compound = new CompoundNBT();
//            world.getTileEntity(pos).write(compound);
//
//            toDrop.put(compound);
//
//            return Collections.singletonList(toDrop);
//        }
//        return super.getDrops(state, world, pos, te, breaker,breakingItem);
//    }

    @Override
    public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        // Note if changed also change BlockInfiniteTank
        if (!world.isRemote) {
            ItemStack heldItem = player.getActiveItemStack();
            if (heldItem.isEmpty() && player.getActiveHand() == Hand.MAIN_HAND) {
                sendPlayerStatus(world, pos, player);
                return;
            }
        }
        super.onBlockClicked(state,world,pos,player);
    }

    protected abstract void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player);

    @Nullable
    protected final IHyperType getHyperStack(@Nonnull ServerWorld world, @Nonnull BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof TileInfiniteBarrel) {
            return (IHyperType) te.getCapability(getHyperCapabilityType()).<IHyperHandler>cast().map(IHyperHandler::status).orElse(null);
        }
        return null;
    }

    @Nonnull
    abstract <T extends IHyperHandler> Capability<T> getHyperCapabilityType();

//    @Override
//    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
//        TileEntity te = world.getTileEntity(pos);
//
//        if (stack != null && stack.get() != null && te != null) {
//            te.read(stack.get());
//        }
//        super.onBlockPlacedBy(world, pos, state, placer, stack);
//    }
}
