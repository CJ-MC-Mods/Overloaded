package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.block.tile.TileEnergyExtractor;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.util.FacingStateMapper;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipesCrafting;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
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

public class BlockEnergyExtractor extends ModBlock implements ITileEntityProvider {

    private static final PropertyDirection FACING = BlockDirectional.FACING;

    public BlockEnergyExtractor() {
        super(Material.ROCK);

        setRegistryName("energy_extractor");
        setUnlocalizedName("energy_extractor");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);

        register();
        GameRegistry.registerTileEntity(TileEnergyExtractor.class, MODID + ":energy_extractor");
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).add(FACING).build();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getProperties().get(FACING)).getIndex();
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        TileEntity te = new TileEnergyExtractor().setFacing(EnumFacing.getFront(meta));

        return te;
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

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.energyExtractor)
            GameRegistry.addRecipe(new ItemStack(this), "IRI", "RBR", "IRI", 'R', Items.REDSTONE, 'I', Items.IRON_INGOT, 'B', Blocks.REDSTONE_BLOCK);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        FacingStateMapper stateMapper = new FacingStateMapper(getRegistryName());
        ModelLoader.setCustomStateMapper(this, stateMapper);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, getFront(placer))); // EnumFacing.getDirectionFromEntityLiving(pos,placer)
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    private EnumFacing getFront(EntityLivingBase placer) {
        Vec3d lookVec = placer.getLookVec();
        return EnumFacing.getFacingFromVector((float)lookVec.xCoord, (float)lookVec.yCoord, (float)lookVec.zCoord);
    }
}
