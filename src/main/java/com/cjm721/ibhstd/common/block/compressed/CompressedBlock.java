package com.cjm721.ibhstd.common.block.compressed;

import com.cjm721.ibhstd.common.block.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by CJ on 4/2/2017.
 */
public class CompressedBlock extends ModBlock {
    private final Block baseBlock;
    private final Block previousBlock;

    public CompressedBlock(Block baseBlock, Block previousBlock, Material materialIn, String registryName, String unlocalizedName, float hardness, String harvestTool, int harvestLevel) {
        super(materialIn);

        this.baseBlock = baseBlock;
        this.previousBlock = previousBlock;
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

    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(this.baseBlock.getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

//        DefaultStateMapper mapper = new DefaultStateMapper();
//        mapper.putStateModelLocations(this).put(this.getDefaultState(), location);
//
//        ModelLoader.setCustomStateMapper(this, mapper);
    }

    public Block getBaseBlock() {
        return baseBlock;
    }
}
