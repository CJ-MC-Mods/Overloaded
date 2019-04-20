package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.util.FacingStateMapper;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;


abstract class AbstractModBlockFacing extends ModBlock {
    private static final PropertyDirection FACING = BlockDirectional.FACING;

    AbstractModBlockFacing(@Nonnull Material materialIn) {
        super(materialIn);
    }

    @Override
    @Nonnull
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).add(FACING).build();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getProperties().get(FACING)).getIndex();
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, getFront(placer))); // EnumFacing.getDirectionFromEntityLiving(pos,placer)
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    private EnumFacing getFront(EntityLivingBase placer) {
        Vec3d lookVec = placer.getLookVec();
        return EnumFacing.getFacingFromVector((float) lookVec.x, (float) lookVec.y, (float) lookVec.z);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        FacingStateMapper stateMapper = new FacingStateMapper(getRegistryName());
        ModelLoader.setCustomStateMapper(this, stateMapper);
    }
}
