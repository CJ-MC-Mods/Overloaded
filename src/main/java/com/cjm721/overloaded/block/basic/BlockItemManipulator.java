package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.tile.functional.TileItemManipulator;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
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
        new ResourceLocation(MODID, "textures/blocks/item_manipulator.png"),
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
    Vec3d lookVec = placer.getLookVec();
    return Direction.getFacingFromVector((float) lookVec.x, (float) lookVec.y, (float) lookVec.z);
  }

  //    @Override
  //    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState
  // state) {
  //        ((TileItemManipulator) worldIn.getTileEntity(pos)).breakBlock();
  //        super.breakBlock(worldIn, pos, state);
  //    }
}
