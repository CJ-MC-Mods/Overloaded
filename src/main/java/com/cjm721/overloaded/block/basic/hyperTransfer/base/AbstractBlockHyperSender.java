package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class AbstractBlockHyperSender extends AbstractBlockHyperNode {

    protected AbstractBlockHyperSender(@Nonnull Properties materialIn) {
        super(materialIn);
    }

    @Override
    public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (player.getActiveHand() == Hand.MAIN_HAND) {
            ItemStack heldItem = player.getActiveItemStack();
            if (heldItem.isEmpty()) {
                // SubIf so that Else block does not also need to check for heldItem == null
                // Should find a cleaner way of showing all of this
                if (!world.isRemote) {
                    String message = ((AbstractTileHyperSender) world.getTileEntity(pos)).getRightClickMessage();
                    player.sendStatusMessage(new StringTextComponent(message), false);
                }
            } else if (heldItem.getItem().equals(ModItems.linkingCard)) {
                CompoundNBT tag = heldItem.getTag();
                if (tag != null) {
                    if (tag.getString("TYPE").equals(this.getType())) {
                        int worldID = tag.getInt("WORLD");
                        int x = tag.getInt("X");
                        int y = tag.getInt("Y");
                        int z = tag.getInt("Z");

                        bindToPartner(world, pos, worldID, new BlockPos(x, y, z));
                        if (world.isRemote) {
                            player.sendStatusMessage(new StringTextComponent("Bound Hyper Nodes"), true);
                        }
                    } else {
                        if (world.isRemote) {
                            player.sendStatusMessage(new StringTextComponent("Incorrect Hyper Node Type to bind."), true);
                        }
                    }
                }
            }
            return;
        }

        super.onBlockClicked(state, world,pos,player);
    }

    private void bindToPartner(@Nonnull World world, @Nonnull BlockPos pos, int partnerWorldId, @Nonnull BlockPos partnerPos) {
        ((AbstractTileHyperSender) world.getTileEntity(pos)).setPartnerInfo(partnerWorldId, partnerPos);
    }
}


