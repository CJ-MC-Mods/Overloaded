package com.cjm721.overloaded.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public abstract class FluidPureMatter extends FlowingFluid {
  @Override
  public Fluid getFlowingFluid() {
    return null;
  }

  @Override
  public Fluid getStillFluid() {
    return null;
  }

  @Override
  protected boolean canSourcesMultiply() {
    return false;
  }

  @Override
  protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {

  }

  @Override
  protected int getSlopeFindDistance(IWorldReader worldIn) {
    return 0;
  }

  @Override
  protected int getLevelDecreasePerBlock(IWorldReader worldIn) {
    return 0;
  }

  /**
   * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
   * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
   */
  @Override
  public BlockRenderLayer getRenderLayer() {
    return null;
  }

  @Override
  public Item getFilledBucket() {
    return null;
  }

  @Override
  protected boolean func_215665_a(IFluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
    return false;
  }

  @Override
  public int getTickRate(IWorldReader p_205569_1_) {
    return 0;
  }

  @Override
  protected float getExplosionResistance() {
    return 0;
  }

  @Override
  protected BlockState getBlockState(IFluidState state) {
    return null;
  }

  @Override
  public boolean isSource(IFluidState state) {
    return false;
  }

  @Override
  public int getLevel(IFluidState p_207192_1_) {
    return 0;
  }

  public static class Flowing extends FluidPureMatter {
    @Override
    protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
      super.fillStateContainer(builder);
      builder.add(LEVEL_1_8);
    }

    @Override
    public int getLevel(IFluidState state) {
      return state.get(LEVEL_1_8);
    }

    @Override
    public boolean isSource(IFluidState state) {
      return false;
    }
  }

  public static class Source extends FluidPureMatter {
    @Override
    public int getLevel(IFluidState state) {
      return 8;
    }

    @Override
    public boolean isSource(IFluidState state) {
      return true;
    }
  }
}
