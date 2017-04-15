package com.cjm721.overloaded.common.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.common.block.tile.hyperTransfer.base.AbstractTileHyperSender;
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

public abstract class AbstractBlockHyperSender extends AbstractBlockHyperNode implements ITileEntityProvider {

    public AbstractBlockHyperSender(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean clOnBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(hand == EnumHand.MAIN_HAND) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if (ItemStackTools.isEmpty(heldItem)) {
                // SubIf so that Else block does not also need to check for heldItem == null
                // Should find a cleaner way of showing all of this
                if(!worldIn.isRemote) {
                    String message = ((AbstractTileHyperSender) worldIn.getTileEntity(pos)).getRightClickMessage();
                    ChatTools.addChatMessage(playerIn, new TextComponentString(message));
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
                            ChatTools.addChatMessage(playerIn, new TextComponentString("Bound Hyper Nodes"));
                        }
                    } else {
                        if (worldIn.isRemote) {
                            ChatTools.addChatMessage(playerIn, new TextComponentString("Incorrect Hyper Node Type to bind."));
                        }
                    }
                }
            }
            return true;
        }

        return super.clOnBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
    }

    private void bindToPartner(@Nonnull World world, @Nonnull BlockPos pos, int partnerWorldId,@Nonnull BlockPos partnerPos) {
        ((AbstractTileHyperSender)world.getTileEntity(pos)).setPartnerInfo(partnerWorldId, partnerPos);
    }

}


