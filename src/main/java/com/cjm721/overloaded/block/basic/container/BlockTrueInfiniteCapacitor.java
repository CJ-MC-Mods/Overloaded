package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.storage.stacks.bigint.BigIntEnergyStack;
import com.cjm721.overloaded.tile.infinity.TileTrueInfiniteCapacitor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;

public class BlockTrueInfiniteCapacitor extends AbstractBlockHyperContainer {

  public BlockTrueInfiniteCapacitor() {
    super(getDefaultProperties());
  }

  @Override
  protected void sendPlayerStatus(Level world, BlockPos pos, Player player) {
    BigIntEnergyStack stack =
        ((TileTrueInfiniteCapacitor) world.getBlockEntity(pos)).getStorage().bigStatus();

    player.displayClientMessage(
        Component.literal(
            String.format("Energy Amount: %,d Bits: %,d", stack.getAmount(), stack.getAmount().bitLength())),
        false);
  }

  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileTrueInfiniteCapacitor(pos,state);
  }

  @Override
  public void registerModel() {}
}
