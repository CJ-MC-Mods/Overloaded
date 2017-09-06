package com.cjm721.overloaded.block.compressed;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.client.render.dynamic.compressed.block.CompressedBlockAssets;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.config.compressed.CompressedEntry;
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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockCompressed extends ModBlock {

    private static final PropertyInteger compressionProperty = PropertyInteger.create("compression", 0, 15);

    private Block baseBlock;
    private final int maxCompressionAmount;
    private final float hardnessMultiplier;
    private final boolean recipeEnabled;
    private final CompressedEntry entry;

    BlockCompressed(@Nonnull String registryName, @Nonnull String unlocalizedName, CompressedEntry entry) {
        super(Material.AIR);
        this.entry = entry;
        this.maxCompressionAmount = entry.depth;
        this.hardnessMultiplier = entry.hardnessMultiplier;
        this.recipeEnabled = entry.recipeEnabled;

        setRegistryName(registryName);
        setUnlocalizedName(unlocalizedName);

        setCreativeTab(OverloadedCreativeTabs.COMPRESSED_BLOCKS);
    }

    public boolean baseBlockInit() {
        IForgeRegistry<Block> registry = GameRegistry.findRegistry(Block.class);
        this.baseBlock = registry.getValue(new ResourceLocation(entry.baseRegistryName));

        if (baseBlock == null) {
            Overloaded.logger.error("Invalid Compressed config entry: Base Block does not exist. %s", entry.baseRegistryName);
            return false;
        }
        Field blockMaterialField = ReflectionHelper.findField(Block.class, "blockMaterial", "field_149764_J");
        Field blockMapColorIn = ReflectionHelper.findField(Block.class, "blockMapColor", "field_181083_K");

        blockMaterialField.setAccessible(true);
        blockMapColorIn.setAccessible(true);

        try {
            blockMaterialField.set(this, blockMaterialField.get(baseBlock));
            blockMapColorIn.set(this, blockMapColorIn.get(baseBlock));
        } catch (IllegalAccessException e) {
            Overloaded.logger.error("Unable to get material and color of base block.", e);
            return false;
        }

        setSoundType(baseBlock.getSoundType());

        String harvestTool = baseBlock.getHarvestTool(baseBlock.getDefaultState());
        if (harvestTool != null) {
            setHarvestLevel(harvestTool, baseBlock.getHarvestLevel(baseBlock.getDefaultState()));
        }
        return true;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int meta = 0; meta < maxCompressionAmount; meta++) {
            items.add(new ItemStack(Item.getItemFromBlock(this), 1, meta));
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
        if (baseBlock == null)
            return 0;

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
            CompressedBlockAssets.addToTextureQueue(new CompressedBlockAssets.CompressedResourceLocation(entry.texturePath, rl, meta + 1));
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
        if (baseBlock == null)
            return Blocks.AIR;
        return baseBlock;
    }

    public boolean isRecipeEnabled() {
        return recipeEnabled;
    }

    public int getBaseMeta() {
        return entry.meta;
    }
}
