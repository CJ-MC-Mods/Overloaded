package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.tile.TileGrill;
import com.cjm721.overloaded.common.util.FacingStateMapper;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockGrill extends ModBlock  {

    private static final PropertyDirection FACING = BlockDirectional.FACING;

    public BlockGrill() {
        super(Material.ROCK);

        this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        setRegistryName("grill");
        setUnlocalizedName("grill");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();

        GameRegistry.registerTileEntity(TileGrill.class, MODID + ":grill");
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).add(FACING).build();
    }

    @Override
    @Deprecated
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getProperties().get(FACING)).getIndex();
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ResourceLocation resourceLocation = new ResourceLocation(MODID, "grill");

        ModelResourceLocation location = new ModelResourceLocation(resourceLocation, null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        FacingStateMapper stateMapper = new FacingStateMapper(resourceLocation);
        ModelLoader.setCustomStateMapper(this, stateMapper);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Deprecated
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }



}
