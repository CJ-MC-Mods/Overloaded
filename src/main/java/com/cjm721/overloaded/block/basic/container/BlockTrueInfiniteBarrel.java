package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.storage.stacks.bigint.BigIntItemStack;
import com.cjm721.overloaded.tile.infinity.TileTrueInfiniteBarrel;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTrueInfiniteBarrel extends AbstractBlockHyperContainer {

  public BlockTrueInfiniteBarrel() {
    super(getDefaultProperties());
    setRegistryName("true_infinite_barrel");
  }

  @Override
  protected void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player) {
    BigIntItemStack stack =
        ((TileTrueInfiniteBarrel) world.getTileEntity(pos)).getStorage().bigStatus();

    if (stack.itemStack.isEmpty()) {
      player.sendStatusMessage(new StringTextComponent("Item: EMPTY"), false);
    } else {
      player.sendStatusMessage(
          new StringTextComponent("Item: ")
              .append(stack.itemStack.getTextComponent())
              .appendString(
                  String.format(
                      " Amount %,d Bits: %,d", stack.getAmount(), stack.getAmount().bitLength())),
          false);
    }
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileTrueInfiniteBarrel();
  }

  @Override
  public void registerModel() {}
}
