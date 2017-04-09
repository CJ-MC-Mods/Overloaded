package com.cjm721.ibhstd.common.block.basic;

import com.cjm721.ibhstd.common.block.ModBlock;
import com.cjm721.ibhstd.common.block.tile.TileHyperItemReceiver;
import com.cjm721.ibhstd.common.block.tile.bases.AbstractTileHyperItemNode;
import com.cjm721.ibhstd.common.item.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by CJ on 4/9/2017.
 */
public abstract class AbstractBlockHyperItemNode extends ModBlock {

    public AbstractBlockHyperItemNode(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(heldItem != null && heldItem.getItem().equals(ModItems.linkingCard)) {
            NBTTagCompound tag = heldItem.getTagCompound();
            if(tag == null) {
                tag = new NBTTagCompound();
                writeNodeData(tag, worldIn, pos);
                heldItem.setTagCompound(tag);
                return true;
            }
            String type = tag.getString("TYPE");
            if(getType().equals(type)) {
                writeNodeData(tag, worldIn, pos);
                heldItem.setTagCompound(tag);
            } else {
                int worldID = tag.getInteger("WORLD");
                int x = tag.getInteger("X");
                int y = tag.getInteger("Y");
                int z = tag.getInteger("Z");

                bindToPartner(worldIn, pos, worldID, new BlockPos(x,y,z));
                heldItem.setTagCompound(null);
                if(worldIn.isRemote) {
                    playerIn.addChatComponentMessage(new TextComponentString("Bound Hyper Nodes"));
                }
            }
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

    protected abstract String getType();

    protected void bindToPartner(World world, BlockPos pos, int partnerWorldId, BlockPos partnerPos) {
        ((AbstractTileHyperItemNode)world.getTileEntity(pos)).setPartnetInfo(partnerWorldId, partnerPos);
    }
}
