package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.tile.infinity.TileTrueInfiniteTank;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTrueInfiniteTank extends AbstractBlockHyperContainer {

  public BlockTrueInfiniteTank() {
    super(getDefaultProperties());
    setRegistryName("true_infinite_tank");
  }

  @Override
  protected void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player) {}

  @Override
  public MaterialColor getMaterialColor(
      BlockState p_180659_1_, IBlockReader p_180659_2_, BlockPos p_180659_3_) {
    return MaterialColor.WHITE_TERRACOTTA;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileTrueInfiniteTank();
  }

  @Override
  public void registerModel() {}
}
