package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.TileItemManipulator;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockItemManipulator extends ModBlock implements ITileEntityProvider {

    private static final PropertyDirection FACING = BlockDirectional.FACING;

    public BlockItemManipulator() {
        super(Material.ROCK);
    }

    @Override
    public void baseInit() {
        setRegistryName("item_manipulator");
        setUnlocalizedName("item_manipulator");

        GameRegistry.registerTileEntity(TileItemManipulator.class, MODID + ":item_manipulator");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/item_manipulator.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/item_manipulator.png"),
                OverloadedConfig.textureResolutions.blockResolution));
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).add(FACING).build();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getProperties().get(FACING)).getIndex();
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileItemManipulator().setFacing(EnumFacing.getFront(meta));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
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
    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        ((TileItemManipulator) worldIn.getTileEntity(pos)).breakBlock();
        super.breakBlock(worldIn, pos, state);
    }
}
