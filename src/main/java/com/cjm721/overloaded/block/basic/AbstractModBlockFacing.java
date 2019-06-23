package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

abstract class AbstractModBlockFacing extends ModBlock {
  public static final DirectionProperty FACING =
      DirectionProperty.create(
          "facing",
          Direction.NORTH,
          Direction.EAST,
          Direction.SOUTH,
          Direction.WEST,
          Direction.UP,
          Direction.DOWN);

  AbstractModBlockFacing(@Nonnull Properties materialIn) {
    super(materialIn);
  }

  //    @Override
  //    @Nonnull
  //    public BlockStateContainer createBlockState() {
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

  //  @Override
  //  public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player) {
  //    world.setBlockState(
  //        pos,
  //        state.withProperty(
  //            FACING, getFront(placer))); // Direction.getDirectionFromEntityLiving(pos,placer)
  //    super.onBlockClicked(state,world,pos,player);
  //  }

  private Direction getFront(PlayerEntity placer) {
    Vec3d lookVec = placer.getLookVec();
    return Direction.getFacingFromVector((float) lookVec.x, (float) lookVec.y, (float) lookVec.z);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

    //    FacingStateMapper stateMapper = new FacingStateMapper(getRegistryName());
    //        ModelLoader.setCustomStateMapper(this, stateMapper);
  }
}
