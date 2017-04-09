package com.cjm721.overloaded.common.block.compressed;

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

/**
 * Created by CJ on 4/2/2017.
 */
public class BlockCompressed extends ModBlock {

    private final Block baseBlock;
    private final Block previousBlock;
    private final int compressionAmount;

    public BlockCompressed(Block baseBlock, Block previousBlock, int compressionAmount, Material materialIn, String registryName, String unlocalizedName, float hardness, String harvestTool, int harvestLevel) {
        super(materialIn);
        this.baseBlock = baseBlock;
        this.previousBlock = previousBlock;
        this.compressionAmount = compressionAmount;

        setRegistryName(registryName);
        setUnlocalizedName(unlocalizedName);
        setHardness(hardness);
        setHarvestLevel(harvestTool, harvestLevel);
        setCreativeTab(OverloadedCreativeTabs.COMPRESSED_BLOCKS);
        register();
    }

    public void registerRecipe() {
        GameRegistry.addRecipe(new ItemStack(this), "AAA", "AAA", "AAA", 'A', previousBlock);
        GameRegistry.addShapelessRecipe(new ItemStack(previousBlock, 9), this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = getBaseModelLocation();
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

//        StateMapperBase ignoreState = new StateMapperBase() {
//            @Override
//            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
//                return location;
//            }
//        };

        ModelResourceLocation rl = new ModelResourceLocation(this.getRegistryName(), null);

        // To make sure that our baked models models is chosen for all states we use this custom state mapper:
        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return rl;
            }
        };
        CompressedModelLoader.addModel(rl, baseBlock.getDefaultState());
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }

    @SideOnly(Side.CLIENT)
    public ModelResourceLocation getBaseModelLocation() {
        return new ModelResourceLocation(this.baseBlock.getRegistryName(), null);
    }

    public Block getBaseBlock() {
        return baseBlock;
    }

    public int getCompressionAmount() {
        return compressionAmount;
    }

    @Override
    public String getLocalizedName() {
        return baseBlock.getLocalizedName();
    }
}
