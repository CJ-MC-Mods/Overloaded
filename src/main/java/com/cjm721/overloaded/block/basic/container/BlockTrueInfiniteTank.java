package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.storage.stacks.bigint.BigIntFluidStack;
import com.cjm721.overloaded.tile.infinity.TileTrueInfiniteTank;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTrueInfiniteTank extends AbstractBlockHyperContainer {

  public BlockTrueInfiniteTank() {
    super(getDefaultProperties());
    setRegistryName("true_infinite_tank");
  }

  @Override
  protected void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player) {
    BigIntFluidStack storedFluid =
        ((TileTrueInfiniteTank) world.getBlockEntity(pos)).getStorage().bigStatus();
    if (storedFluid == null || storedFluid.fluidStack == null) {
      player.displayClientMessage(new StringTextComponent("Fluid: EMPTY"), false);
    } else {
      player.displayClientMessage(
          new StringTextComponent("Fluid: ")
              .append(storedFluid.fluidStack.getDisplayName())
              .append(
                  String.format(
                      " Amount: %,d Bits: %,d",
                      storedFluid.amount, storedFluid.amount.bitLength())),
          false);
    }
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileTrueInfiniteTank();
  }

  @Override
  public void registerModel() {}
}
