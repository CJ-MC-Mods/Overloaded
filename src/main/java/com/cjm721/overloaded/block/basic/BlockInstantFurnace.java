package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.ModBlockContainer;
import com.cjm721.overloaded.tile.functional.TileInstantFurnace;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockInstantFurnace extends ModBlockContainer {

  public BlockInstantFurnace() {
    super(ModBlock.getDefaultProperties());
    setRegistryName("instant_furnace");
  }

  @Override
  public boolean onBlockActivated(
      BlockState state,
      World worldIn,
      BlockPos pos,
      PlayerEntity player,
      Hand handIn,
      BlockRayTraceResult hit) {
    //    player.openContainer(new InstantFurnaceContainer(,player, worldIn.getTileEntity(pos)));
    player.openContainer(state.getContainer(worldIn, pos));
    return true;
  }

  @Nonnull
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(IBlockReader worldIn) {
    return new TileInstantFurnace();
  }
}
