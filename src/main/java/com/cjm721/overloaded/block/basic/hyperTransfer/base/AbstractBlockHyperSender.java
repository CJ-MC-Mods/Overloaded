package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class AbstractBlockHyperSender extends AbstractBlockHyperNode {

    protected AbstractBlockHyperSender(@Nonnull Properties materialIn) {
        super(materialIn);
    }

    @Override
    @Nonnull
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (hand == Hand.MAIN_HAND) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (heldItem.isEmpty()) {
                // SubIf so that Else block does not also need to check for heldItem == null
                // Should find a cleaner way of showing all of this
                if (!world.isClientSide) {
                    String message = ((AbstractTileHyperSender) world.getBlockEntity(pos)).getRightClickMessage();
                    player.displayClientMessage(new StringTextComponent(message), false);
                }
              return ActionResultType.SUCCESS;
            } else if (heldItem.getItem().equals(ModItems.linkingCard)) {
                CompoundNBT tag = heldItem.getTag();
                if (tag != null) {
                    if (tag.getString("TYPE").equals(this.getType())) {
                        String worldID = tag.getString("WORLD");
                        int x = tag.getInt("X");
                        int y = tag.getInt("Y");
                        int z = tag.getInt("Z");

                        bindToPartner(world, pos, worldID, new BlockPos(x, y, z));
                        if (world.isClientSide) {
                            player.displayClientMessage(new StringTextComponent("Bound Hyper Nodes"), true);
                        }
                    } else {
                        if (world.isClientSide) {
                            player.displayClientMessage(new StringTextComponent("Incorrect Hyper Node Type to bind."), true);
                        }
                    }
                }
              return ActionResultType.SUCCESS;
            }
        }

        return super.use(state, world, pos, player, hand, rayTraceResult);
    }

    private void bindToPartner(@Nonnull World world, @Nonnull BlockPos pos, String registryLocation, @Nonnull BlockPos partnerPos) {
        ((AbstractTileHyperSender) world.getBlockEntity(pos)).setPartnerInfo(registryLocation, partnerPos);
    }
}


