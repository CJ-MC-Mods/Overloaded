package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.tile.functional.TileItemManipulator;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockItemManipulator extends AbstractModBlockFacing {
  public BlockItemManipulator() {
    super(getDefaultProperties());
    setRegistryName("item_manipulator");
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //   ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/block/item_manipulator.png"),
        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
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

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileItemManipulator(); // .setFacing(Direction.byIndex(meta));
  }

  //    @Override
  //    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable
  // LivingEntity entity, ItemStack stack) {
  //        world.setBlockState(pos, state.withProperty(FACING, getFront(placer))); //
  // Direction.getDirectionFromEntityLiving(pos,placer)
  //        super.onBlockPlacedBy(world, pos, state, entity, stack);
  //    }

  private Direction getFront(LivingEntity placer) {
    Vector3d lookVec = placer.getLookAngle();
    return Direction.getNearest((float) lookVec.x, (float) lookVec.y, (float) lookVec.z);
  }

  @Override
  public void onRemove(
      BlockState oldState, World world, BlockPos pos, BlockState newState, boolean isMoving) {
    if (oldState.getBlock() != newState.getBlock()) {
      TileEntity te = world.getBlockEntity(pos);

      if (te instanceof TileItemManipulator) {
        ((TileItemManipulator) te).breakBlock();
      }
    }

    super.onRemove(oldState, world, pos, newState, isMoving);
  }
}
