package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.storage.stacks.bigint.BigIntEnergyStack;
import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import com.cjm721.overloaded.tile.infinity.TileAlmostInfiniteCapacitor;
import com.cjm721.overloaded.tile.infinity.TileTrueInfiniteCapacitor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTrueInfiniteCapacitor extends AbstractBlockHyperContainer {

  public BlockTrueInfiniteCapacitor() {
    super(getDefaultProperties());
    setRegistryName("true_infinite_capacitor");
  }

  @Override
  protected void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player) {
    BigIntEnergyStack stack =
        ((TileTrueInfiniteCapacitor) world.getTileEntity(pos)).getStorage().bigStatus();

    player.sendStatusMessage(
        new StringTextComponent(
            String.format("Energy Amount: %,d Bits: %,d", stack.getAmount(), stack.getAmount().bitLength())),
        false);
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileTrueInfiniteCapacitor();
  }

  @Override
  public void registerModel() {}
}
