package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.block.ModBlockTile;
import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.IHyperType;
import com.cjm721.overloaded.tile.infinity.AbstractTileHyperStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    @Nonnull
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            ItemStack heldItem = player.getHeldItem(handIn);
            if (heldItem.isEmpty() && handIn == Hand.MAIN_HAND) {
                sendPlayerStatus(world, pos, player);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state,world,pos,player, handIn, hit);
    }

    protected abstract void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player);

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
