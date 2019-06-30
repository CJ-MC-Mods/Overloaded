package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.tile.infinity.TileTrueInfiniteBarrel;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTrueInfiniteBarrel extends AbstractBlockHyperContainer {

  public BlockTrueInfiniteBarrel() {
    super(getDefaultProperties());
    setRegistryName("true_infinite_barrel");
  }

  @Override
  protected void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player) {}

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileTrueInfiniteBarrel();
  }

  @Override
  public void registerModel() {}
}
