package com.cjm721.overloaded.common.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.common.item.ModItems;
import mcjty.lib.tools.ChatTools;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.ITileEntityProvider;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractBlockHyperReceiver extends AbstractBlockHyperNode implements ITileEntityProvider {

    public AbstractBlockHyperReceiver(@Nonnull Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean clOnBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if(!ItemStackTools.isEmpty(heldItem) && heldItem.getItem().equals(ModItems.linkingCard)) {
            NBTTagCompound tag = heldItem.getTagCompound();
            if(tag == null) {
                tag = new NBTTagCompound();
            }

            int worldId = worldIn.provider.getDimension();
            writeNodeData(tag, worldId, pos);
            heldItem.setTagCompound(tag);

            if(worldIn.isRemote) {
                ChatTools.addChatMessage(playerIn, new TextComponentString(String.format("Recorded: World: %d Position: %s", worldId, pos.toString())));
            }

            return true;
        } else {
            return super.clOnBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
        }
    }

    private void writeNodeData(@Nonnull NBTTagCompound tag, int worldId, @Nonnull BlockPos pos) {
        tag.setInteger("X", pos.getX());
        tag.setInteger("Y", pos.getY());
        tag.setInteger("Z", pos.getZ());
        tag.setInteger("WORLD", worldId);
        tag.setString("TYPE", getType());
    }

}
