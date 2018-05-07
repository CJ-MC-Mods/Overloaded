package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.block.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.item.ModItems;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoAccessor;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nonnull;

@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe")
public abstract class AbstractBlockHyperSender extends AbstractBlockHyperNode implements ITileEntityProvider, IProbeInfoAccessor {

    public AbstractBlockHyperSender(@Nonnull Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (hand == EnumHand.MAIN_HAND) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if (heldItem.isEmpty()) {
                // SubIf so that Else block does not also need to check for heldItem == null
                // Should find a cleaner way of showing all of this
                if (!worldIn.isRemote) {
                    String message = ((AbstractTileHyperSender) worldIn.getTileEntity(pos)).getRightClickMessage();
                    playerIn.sendStatusMessage(new TextComponentString(message), false);
                }
            } else if (heldItem.getItem().equals(ModItems.linkingCard)) {
                NBTTagCompound tag = heldItem.getTagCompound();
                if (tag != null) {
                    if (tag.getString("TYPE").equals(this.getType())) {
                        int worldID = tag.getInteger("WORLD");
                        int x = tag.getInteger("X");
                        int y = tag.getInteger("Y");
                        int z = tag.getInteger("Z");

                        bindToPartner(worldIn, pos, worldID, new BlockPos(x, y, z));
                        if (worldIn.isRemote) {
                            playerIn.sendStatusMessage(new TextComponentString("Bound Hyper Nodes"), true);
                        }
                    } else {
                        if (worldIn.isRemote) {
                            playerIn.sendStatusMessage(new TextComponentString("Incorrect Hyper Node Type to bind."), true);
                        }
                    }
                }
            }
            return true;
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
    }

    private void bindToPartner(@Nonnull World world, @Nonnull BlockPos pos, int partnerWorldId, @Nonnull BlockPos partnerPos) {
        ((AbstractTileHyperSender) world.getTileEntity(pos)).setPartnerInfo(partnerWorldId, partnerPos);
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te != null && te instanceof AbstractTileHyperSender) {
            probeInfo.text(((AbstractTileHyperSender) te).getRightClickMessage());
        }
    }
}


