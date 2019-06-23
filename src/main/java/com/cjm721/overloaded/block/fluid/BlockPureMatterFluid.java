package com.cjm721.overloaded.block.fluid;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockPureMatterFluid extends ModBlock implements IModRegistrable, IFluidBlock {

  public BlockPureMatterFluid() {
    super(Block.Properties.create(Material.WATER));
    setRegistryName("pure_matter");
  }

  @Override
  public void baseInit() {}

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    //    ModelLoader.setCustomStateMapper(this, new FluidStateMapper());
  }

  @OnlyIn(Dist.CLIENT)
  @Nonnull
  @Override
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.TRANSLUCENT;
  }

  @Override
  public Fluid getFluid() {
    return null;
  }

  @Override
  public int place(World world, BlockPos pos, @Nonnull FluidStack fluidStack, boolean doPlace) {
    return 0;
  }

  @Nullable
  @Override
  public FluidStack drain(World world, BlockPos pos, boolean doDrain) {
    return null;
  }

  @Override
  public boolean canDrain(World world, BlockPos pos) {
    return false;
  }

  @Override
  public float getFilledPercentage(World world, BlockPos pos) {
    return 0;
  }

  //  private class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {
  //    final ModelResourceLocation location;
  //
  //    FluidStateMapper() {
  //      this.location = new ModelResourceLocation(BlockPureMatterFluid.this.getRegistryName(),
  // "all");
  //    }
  //
  //    @Nonnull
  //    @Override
  //    protected ModelResourceLocation getModelResourceLocation(@Nonnull BlockState state) {
  //      return location;
  //    }
  //
  //    @Nonnull
  //    @Override
  //    public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
  //      return location;
  //    }
  //  }
}
