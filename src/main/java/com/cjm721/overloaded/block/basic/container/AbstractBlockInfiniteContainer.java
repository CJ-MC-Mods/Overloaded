package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public abstract class AbstractBlockInfiniteContainer extends ModBlock {
    public AbstractBlockInfiniteContainer(Material materialIn) {
        super(materialIn);
    }

    @Override
    @Nonnull
    public final List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune) {
        IHyperType stack = getHyperStack(world, pos);

        if (stack != null && stack.getAmount() != 0) {
            ItemStack toDrop = new ItemStack(this, 1);

            NBTTagCompound compound = new NBTTagCompound();
            world.getTileEntity(pos).writeToNBT(compound);

            toDrop.setTagCompound(compound);

            return Collections.singletonList(toDrop);
        }
        return super.getDrops(world, pos, state, fortune);
    }

    @Nullable
    protected abstract IHyperType getHyperStack(IBlockAccess world, BlockPos pos);

    @Override
    public final void onBlockPlacedBy(@Nonnull World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity te = world.getTileEntity(pos);

        if (stack != null && stack.getTagCompound() != null && te != null) {
            te.readFromNBT(stack.getTagCompound());
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }
}
