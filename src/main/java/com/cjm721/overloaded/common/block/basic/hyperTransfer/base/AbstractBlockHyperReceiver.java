package com.cjm721.overloaded.common.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.common.item.ModItems;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by CJ on 4/10/2017.
 */
public abstract class AbstractBlockHyperReceiver extends AbstractBlockHyperNode implements ITileEntityProvider {

    public AbstractBlockHyperReceiver(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(heldItem != null && heldItem.getItem().equals(ModItems.linkingCard)) {
            NBTTagCompound tag = heldItem.getTagCompound();
            if(tag == null) {
                tag = new NBTTagCompound();
            }
            writeNodeData(tag, worldIn, pos);
            heldItem.setTagCompound(tag);
            return true;
        } else {
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
        }
    }

    private void writeNodeData(NBTTagCompound tag, World worldIn, BlockPos pos) {
        tag.setInteger("X", pos.getX());
        tag.setInteger("Y", pos.getY());
        tag.setInteger("Z", pos.getZ());
        tag.setInteger("WORLD", worldIn.provider.getDimension());
        tag.setString("TYPE", getType());
    }

}
