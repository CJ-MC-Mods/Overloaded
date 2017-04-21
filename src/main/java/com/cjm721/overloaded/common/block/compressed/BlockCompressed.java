package com.cjm721.overloaded.common.block.compressed;

import com.cjm721.overloaded.client.render.block.compressed.CompressedBlockAssets;
import com.cjm721.overloaded.client.render.block.compressed.CompressedModelLoader;
import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockCompressed extends ModBlock {

    @Nonnull
    private final Block baseBlock;
    @Nonnull
    private final Block previousBlock;
    private final int compressionAmount;

    BlockCompressed(@Nonnull Block baseBlock, @Nonnull Block previousBlock, int compressionAmount, @Nonnull Material materialIn, @Nonnull String registryName, @Nonnull String unlocalizedName, float hardness, String harvestTool, int harvestLevel) {
        super(materialIn);
        this.baseBlock = baseBlock;
        this.previousBlock = previousBlock;
        this.compressionAmount = compressionAmount;

        setRegistryName(registryName);
        setUnlocalizedName(unlocalizedName);
        setHardness(hardness);
        if(harvestTool != null)
            setHarvestLevel(harvestTool, harvestLevel);
        setCreativeTab(OverloadedCreativeTabs.COMPRESSED_BLOCKS);
        register();
    }

    @Override
    public void registerRecipe() {
        GameRegistry.addRecipe(new ItemStack(this), "AAA", "AAA", "AAA", 'A', previousBlock);
        GameRegistry.addShapelessRecipe(new ItemStack(previousBlock, 9), this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = getBaseModelLocation();
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);


        CompressedBlockAssets.addToTextureQueue(new CompressedBlockAssets.CompressedResourceLocation(getBaseModelLocation(), getRegistryName(), getCompressionAmount()));
        ModelResourceLocation rl = new ModelResourceLocation(this.getRegistryName(), null);

        // To make sure that our baked models models is chosen for all states we use this custom state mapper:
        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState iBlockState) {
                return rl;
            }
        };
//        CompressedModelLoader.addModel(rl, baseBlock.getDefaultState());
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    private ModelResourceLocation getBaseModelLocation() {
        return new ModelResourceLocation(this.baseBlock.getRegistryName(), null);
    }

    @Nonnull
    public Block getBaseBlock() {
        return baseBlock;
    }

    public int getCompressionAmount() {
        return compressionAmount;
    }

    @Override
    @Nonnull
    public String getLocalizedName() {
        return baseBlock.getLocalizedName();
    }
}
