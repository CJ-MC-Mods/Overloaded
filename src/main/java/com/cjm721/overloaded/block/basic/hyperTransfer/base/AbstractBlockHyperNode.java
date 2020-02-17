package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.block.ModBlock;
import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.*;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public abstract class AbstractBlockHyperNode extends ModBlock {

  private static final VoxelShape modelShape;

  static {
    VoxelShape part = makeCuboidShape(5, 5, 0, 11, 11, 5);
    modelShape = VoxelShapeUtils.batchCombine(
        part, IBooleanFunction.OR, true,
        VoxelShapeUtils.rotate(part, Rotation.CLOCKWISE_90),
        VoxelShapeUtils.rotate(part, Rotation.CLOCKWISE_180),
        VoxelShapeUtils.rotate(part, Rotation.COUNTERCLOCKWISE_90),
        makeCuboidShape(5,0,5,11,5,11),
        makeCuboidShape(5,11,5,11,16,11)
    );
  }

  AbstractBlockHyperNode(@Nonnull Properties materialIn) {
    super(materialIn.variableOpacity());
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nonnull
  protected abstract String getType();

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

    //        StateMapperBase ignoreState = new StateMapperBase() {
    //            @Override
    //            @Nonnull
    //            protected ModelResourceLocation getModelResourceLocation(@Nonnull BlockState
    // iBlockState) {
    //                return location;
    //            }
    //        };
    //        ModelLoader.setCustomStateMapper(this, ignoreState);
  }

  @Override
  public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
    return false;
  }

  @Deprecated
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return modelShape;
  }
}
