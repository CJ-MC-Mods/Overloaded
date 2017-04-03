package com.cjm721.ibhstd.common.block.compressed;

import com.cjm721.ibhstd.client.block.compressed.CompressedBaskedModel;
import com.cjm721.ibhstd.common.block.ModBlock;
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
public class CompressedBlock extends ModBlock {

    private final Block baseBlock;
    private final Block previousBlock;
    private final int compressionAmount;

    public CompressedBlock(Block baseBlock, Block previousBlock, int compressionAmount, Material materialIn, String registryName, String unlocalizedName, float hardness, String harvestTool, int harvestLevel) {
        super(materialIn);

        this.baseBlock = baseBlock;
        this.previousBlock = previousBlock;
        this.compressionAmount = compressionAmount;
        setRegistryName(registryName);
        setUnlocalizedName(unlocalizedName);
        setHardness(hardness);
        setHarvestLevel(harvestTool, harvestLevel);
        register();
    }

    public void registerRecipe() {
        GameRegistry.addRecipe(new ItemStack(this), "AAA", "AAA", "AAA", 'A', previousBlock);
        GameRegistry.addShapelessRecipe(new ItemStack(previousBlock, 9), this);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation location = getBaseModelLocation();
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);


        // To make sure that our baked model model is chosen for all states we use this custom state mapper:
        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return CompressedBaskedModel.BAKED_MODEL;
            }
        };
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
}
