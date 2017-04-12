package com.cjm721.overloaded.common.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.tile.hyperTransfer.base.AbstractTileHyperSender;
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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by CJ on 4/10/2017.
 */
public abstract class AbstractBlockHyperSender extends AbstractBlockHyperNode implements ITileEntityProvider {

    public AbstractBlockHyperSender(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(hand == EnumHand.MAIN_HAND) {
            if (heldItem == null) {
                // SubIf so that Else block does not also need to check for heldItem == null
                // Should find a cleaner way of showing all of this
                if(!worldIn.isRemote) {
                    String message = ((AbstractTileHyperSender) worldIn.getTileEntity(pos)).getRightClickMessage();
                    playerIn.addChatComponentMessage(new TextComponentString(message));
                }
            } else if (heldItem.getItem().equals(ModItems.linkingCard)) {
                NBTTagCompound tag = heldItem.getTagCompound();
                if (tag != null) {
                    if(tag.getString("TYPE").equals(this.getType())) {
                        int worldID = tag.getInteger("WORLD");
                        int x = tag.getInteger("X");
                        int y = tag.getInteger("Y");
                        int z = tag.getInteger("Z");

                        bindToPartner(worldIn, pos, worldID, new BlockPos(x, y, z));
                        heldItem.setTagCompound(null);
                        if (worldIn.isRemote) {
                            playerIn.addChatComponentMessage(new TextComponentString("Bound Hyper Nodes"));
                        }
                    } else {
                        if (worldIn.isRemote) {
                            playerIn.addChatComponentMessage(new TextComponentString("Incorrect Hyper Node Type to bind."));
                        }
                    }
                }
            }
            return true;
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    protected void bindToPartner(World world, BlockPos pos, int partnerWorldId, BlockPos partnerPos) {
        ((AbstractTileHyperSender)world.getTileEntity(pos)).setPartnerInfo(partnerWorldId, partnerPos);
    }

}


