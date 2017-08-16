package com.cjm721.overloaded.block.compressed;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.client.render.dynamic.compressed.block.CompressedBlockAssets;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockCompressed extends ModBlock {

    private static final PropertyInteger compressionProperty = PropertyInteger.create("compression", 0, 15);

    @Nonnull
    private final Block baseBlock;
    private final int maxCompressionAmount;
    private final float hardnessMultiplier;
    private final boolean recipeEnabled;

    BlockCompressed(@Nonnull Block baseBlock, int maxCompressionAmount, @Nonnull Material materialIn, @Nonnull String registryName, @Nonnull String unlocalizedName, String harvestTool, int harvestLevel, float hardnessMultiplier, boolean recipeEnabled) {
        super(materialIn);
        this.baseBlock = baseBlock;
        this.maxCompressionAmount = maxCompressionAmount;
        this.hardnessMultiplier = hardnessMultiplier;
        this.recipeEnabled = recipeEnabled;

        setRegistryName(registryName);
        setUnlocalizedName(unlocalizedName);
        setSoundType(baseBlock.getSoundType());

        if (harvestTool != null) {
            setHarvestLevel(harvestTool, harvestLevel);
        }
        
        setCreativeTab(OverloadedCreativeTabs.COMPRESSED_BLOCKS);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int meta = 0; meta < maxCompressionAmount; meta++) {
            items.add(new ItemStack(Item.getItemFromBlock(this),1,meta));
        }
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).add(compressionProperty).build();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(compressionProperty);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(compressionProperty, meta);
    }

    @Nonnull
    @Override
    public IBlockState getStateForPlacement(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer, EnumHand hand) {
        return getStateFromMeta(meta);
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        int compression = blockState.getValue(compressionProperty);

        float hardness = baseBlock.getDefaultState().getBlockHardness(worldIn, pos);

        for (int i = 0; i <= compression; i++) {
            hardness *= hardnessMultiplier;
        }

        return hardness;
    }

    @Override
    @Nonnull
    public String getUnlocalizedName() {
        return super.getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, ITooltipFlag advanced) {
        if (OverloadedConfig.compressedConfig.showHardness)
            tooltip.add(String.format("Hardness: %.0f", ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()).getBlockHardness(null, null)));

        super.addInformation(stack, world, tooltip, advanced);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        Map<IBlockState, ModelResourceLocation> states = new HashMap<>();

        for (int meta = 0; meta < maxCompressionAmount; meta++) {
            ResourceLocation rl = new ResourceLocation(getRegistryName().getResourceDomain(), getRegistryName().getResourcePath() + meta);
            CompressedBlockAssets.addToTextureQueue(new CompressedBlockAssets.CompressedResourceLocation(getBaseModelLocation(), rl, meta + 1));
            ModelResourceLocation ml = new ModelResourceLocation(rl, "normal");

            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), meta, ml);

            states.put(this.getStateFromMeta(meta), ml);

        }
        ModelLoader.setCustomStateMapper(this, new IStateMapper() {
            @Override
            @Nonnull
            public Map<IBlockState, ModelResourceLocation> putStateModelLocations(@Nonnull Block blockIn) {
                return states;
            }
        });

    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    private ModelResourceLocation getBaseModelLocation() {
        return new ModelResourceLocation(this.baseBlock.getRegistryName(), null);
    }

    @Override
    @Nonnull
    public String getLocalizedName() {
        return baseBlock.getLocalizedName();
    }

    public int getMaxCompression() {
        return maxCompressionAmount;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Nonnull
    public Block getBaseBlock() {
        return baseBlock;
    }

    public boolean isRecipeEnabled() {
        return recipeEnabled;
    }
}
