package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.block.ModBlockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.phys.BlockHitResult;

abstract class AbstractBlockHyperContainer extends ModBlockTile {
    AbstractBlockHyperContainer(Properties materialIn) {
        super(materialIn);
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
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hitResult) {
        if (!world.isClientSide) {
            ItemStack heldItem = player.getItemInHand(handIn);
            if (heldItem.isEmpty() && handIn == InteractionHand.MAIN_HAND) {
                sendPlayerStatus(world, pos, player);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state,world,pos,player, handIn, hitResult);
    }

    protected abstract void sendPlayerStatus(Level world, BlockPos pos, Player player);

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
