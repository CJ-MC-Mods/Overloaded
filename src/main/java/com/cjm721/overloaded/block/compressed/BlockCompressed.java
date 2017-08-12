package com.cjm721.overloaded.block.compressed;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockCompressed extends ModBlock {

    public static final PropertyInteger COMPRESSSION = PropertyInteger.create("compression", 0, 15);

    @Nonnull
    private final Block baseBlock;
    @Nonnull
    private final int maxCompressionAmount;

    BlockCompressed(@Nonnull Block baseBlock, int maxCompressionAmount, @Nonnull Material materialIn, @Nonnull String registryName, @Nonnull String unlocalizedName, float hardness, String harvestTool, int harvestLevel, boolean recipeEnabled) {
        super(materialIn);
        this.baseBlock = baseBlock;
        this.maxCompressionAmount = maxCompressionAmount;

        setRegistryName(registryName);
        setUnlocalizedName(unlocalizedName);
        setSoundType(baseBlock.getSoundType());
        setHardness(hardness);


        if (harvestTool != null)
            setHarvestLevel(harvestTool, harvestLevel);
        // TODO: When implemented uncomment this
        // setCreativeTab(OverloadedCreativeTabs.COMPRESSED_BLOCKS);
        register();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for(int i = 0; i < maxCompressionAmount; i++) {
            items.add(new ItemStack(this,1,i));
        }

        super.getSubBlocks(itemIn, items);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).add(COMPRESSSION).build();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COMPRESSSION);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(COMPRESSSION, meta);
    }

    @Nonnull
    @Override
    public IBlockState getStateForPlacement(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer, EnumHand hand) {
        return getStateFromMeta(meta);
    }

    @Override
    @Nonnull
    public String getUnlocalizedName() {
        return super.getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(String.format("Hardness: %.0f", ((ItemBlock) stack.getItem()).getBlock().getDefaultState().getBlockHardness(null, null)));

        super.addInformation(stack, world, tooltip, advanced);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
//        CompressedBlockAssets.addToTextureQueue(new CompressedBlockAssets.CompressedResourceLocation(getBaseModelLocation(), getRegistryName(), getCompressionAmount()));
//        ModelResourceLocation rl = new ModelResourceLocation(getRegistryName(), "inventory");
//
//        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, rl);
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
}
