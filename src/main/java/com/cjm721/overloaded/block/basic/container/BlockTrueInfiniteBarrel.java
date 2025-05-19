package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.storage.stacks.bigint.BigIntItemStack;
import com.cjm721.overloaded.tile.infinity.TileTrueInfiniteBarrel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;

public class BlockTrueInfiniteBarrel extends AbstractBlockHyperContainer {

  public BlockTrueInfiniteBarrel(Properties properties) {
    super(properties);
  }

  @Override
  protected void sendPlayerStatus(Level world, BlockPos pos, Player player) {
    BigIntItemStack stack =
        ((TileTrueInfiniteBarrel) world.getBlockEntity(pos)).getStorage().bigStatus();

    if (stack.itemStack.isEmpty()) {
      player.displayClientMessage(Component.literal("Item: EMPTY"), false);
    } else {
      player.displayClientMessage(
          Component.literal("Item: ")
              .append(stack.itemStack.getDisplayName())
              .append(
                  String.format(
                      " Amount %,d Bits: %,d", stack.getAmount(), stack.getAmount().bitLength())),
          false);
    }
  }

  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileTrueInfiniteBarrel(pos,state);
  }

  @Override
  public void registerModel() {}
}
