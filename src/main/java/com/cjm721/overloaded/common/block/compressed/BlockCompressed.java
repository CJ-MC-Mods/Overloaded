package com.cjm721.overloaded.common.block.compressed;

import com.cjm721.overloaded.client.render.block.compressed.CompressedBlockAssets;
import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockCompressed extends ModBlock {

    private final Block baseBlock;
    private final Block previousBlock;
    private final int compressionAmount;
    private final boolean recipeEnabled;

    BlockCompressed(@Nonnull Block baseBlock, @Nonnull Block previousBlock, int compressionAmount, @Nonnull Material materialIn, @Nonnull String registryName, @Nonnull String unlocalizedName, float hardness, String harvestTool, int harvestLevel, boolean recipeEnabled) {
        super(materialIn);
        this.baseBlock = baseBlock;
        this.previousBlock = previousBlock;
        this.compressionAmount = compressionAmount;

        this.recipeEnabled = recipeEnabled;

        setRegistryName(registryName);
        setUnlocalizedName(unlocalizedName);
        setSoundType(baseBlock.getSoundType());
        setHardness(hardness);


        if(harvestTool != null)
            setHarvestLevel(harvestTool, harvestLevel);
        setCreativeTab(OverloadedCreativeTabs.COMPRESSED_BLOCKS);
        register();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add(String.format("Hardness: %.0f", ((ItemBlock) stack.getItem()).getBlock().getDefaultState().getBlockHardness(null,null)));

        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.compressedBlocks && recipeEnabled) {
            GameRegistry.addRecipe(new ItemStack(this), "AAA", "AAA", "AAA", 'A', previousBlock);
            GameRegistry.addShapelessRecipe(new ItemStack(previousBlock, 9), this);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        CompressedBlockAssets.addToTextureQueue(new CompressedBlockAssets.CompressedResourceLocation(getBaseModelLocation(), getRegistryName(), getCompressionAmount()));
        ModelResourceLocation rl = new ModelResourceLocation(getRegistryName(), "inventory");

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, rl);
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
