package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;

public abstract class AbstractBlockHyperSender extends AbstractBlockHyperNode {

    protected AbstractBlockHyperSender(@Nonnull Properties materialIn) {
        super(materialIn);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hand == InteractionHand.MAIN_HAND) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (heldItem.isEmpty()) {
                // SubIf so that Else block does not also need to check for heldItem == null
                // Should find a cleaner way of showing all of this
                if (!world.isClientSide) {
                    String message = ((AbstractTileHyperSender) world.getBlockEntity(pos)).getRightClickMessage();
                    player.displayClientMessage(Component.literal(message), false);
                }
              return InteractionResult.SUCCESS;
            } else if (heldItem.getItem().equals(ModItems.linkingCard)) {
//                CompoundTag tag = heldItem.getTag();
//                if (tag != null) {
//                    if (tag.getString("TYPE").equals(this.getType())) {
//                        String worldID = tag.getString("WORLD");
//                        int x = tag.getInt("X");
//                        int y = tag.getInt("Y");
//                        int z = tag.getInt("Z");
//
//                        bindToPartner(world, pos, worldID, new BlockPos(x, y, z));
//                        if (world.isClientSide) {
//                            player.displayClientMessage(Component.literal("Bound Hyper Nodes"), true);
//                        }
//                    } else {
//                        if (world.isClientSide) {
//                            player.displayClientMessage(Component.literal("Incorrect Hyper Node Type to bind."), true);
//                        }
//                    }
//                }
              return InteractionResult.SUCCESS;
            }
        }

        return super.useItemOn(stack, state, world, pos, player, hand,hitResult);
    }

    private void bindToPartner(@Nonnull Level world, @Nonnull BlockPos pos, String registryLocation, @Nonnull BlockPos partnerPos) {
        ((AbstractTileHyperSender) world.getBlockEntity(pos)).setPartnerInfo(registryLocation, partnerPos);
    }
}


