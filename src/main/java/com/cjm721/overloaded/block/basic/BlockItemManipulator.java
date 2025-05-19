package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.tile.functional.TileItemManipulator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockItemManipulator extends AbstractModBlockFacing implements EntityBlock {
  public BlockItemManipulator(Properties properties) {
    super(properties);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
//    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
//    //   ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/block/item_manipulator.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }

  //    @Override
  //    @Nonnull
  //    protected BlockStateContainer createBlockState() {
  //        return new BlockStateContainer.Builder(this).add(FACING).build();
  //    }
  //
  //    @Override
  //    public int getMetaFromState(BlockState state) {
  //        return ((Direction) state.getProperties().get(FACING)).getIndex();
  //    }
  //
  //    @Override
  //    @Nonnull
  //    public BlockState getStateFromMeta(int meta) {
  //        return getDefaultState().withProperty(FACING, Direction.byIndex(meta));
  //    }


  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileItemManipulator(pos, state); // .setFacing(Direction.byIndex(meta));
  }

  //    @Override
  //    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable
  // LivingEntity entity, ItemStack stack) {
  //        world.setBlockState(pos, state.withProperty(FACING, getFront(placer))); //
  // Direction.getDirectionFromEntityLiving(pos,placer)
  //        super.onBlockPlacedBy(world, pos, state, entity, stack);
  //    }
//
//  private Direction getFront(LivingEntity placer) {
//    Vector3d lookVec = placer.getLookAngle();
//    return Direction.getNearest((float) lookVec.x, (float) lookVec.y, (float) lookVec.z);
//  }

  @Override
  public void onRemove(
      BlockState oldState, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
    if (oldState.getBlock() != newState.getBlock()) {
      BlockEntity te = world.getBlockEntity(pos);

      if (te instanceof TileItemManipulator) {
        ((TileItemManipulator) te).breakBlock();
      }
    }

    super.onRemove(oldState, world, pos, newState, isMoving);
  }
}
