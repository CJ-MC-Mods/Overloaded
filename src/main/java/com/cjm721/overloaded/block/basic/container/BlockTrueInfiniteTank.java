package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.storage.stacks.bigint.BigIntFluidStack;
import com.cjm721.overloaded.tile.infinity.TileTrueInfiniteTank;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;

public class BlockTrueInfiniteTank extends AbstractBlockHyperContainer {

  public BlockTrueInfiniteTank(Properties properties) {
    super(properties);
  }

  @Override
  protected void sendPlayerStatus(Level world, BlockPos pos, Player player) {
    BigIntFluidStack storedFluid =
        ((TileTrueInfiniteTank) world.getBlockEntity(pos)).getStorage().bigStatus();
    if (storedFluid == null || storedFluid.fluidStack == null) {
      player.displayClientMessage(Component.literal("Fluid: EMPTY"), false);
    } else {
      player.displayClientMessage(
          Component.literal("Fluid: ")
              .append(storedFluid.fluidStack.getHoverName())
              .append(
                  String.format(
                      " Amount: %,d Bits: %,d",
                      storedFluid.amount, storedFluid.amount.bitLength())),
          false);
    }
  }

  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileTrueInfiniteTank(pos, state);
  }

  @Override
  public void registerModel() {}
}
