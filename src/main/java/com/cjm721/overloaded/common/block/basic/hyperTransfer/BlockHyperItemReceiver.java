package com.cjm721.overloaded.common.block.basic.hyperTransfer;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.common.block.tile.hyperTransfer.TileHyperItemReceiver;
import com.cjm721.overloaded.common.config.OverloadedConfig;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperItemReceiver extends AbstractBlockHyperReceiver {

    public BlockHyperItemReceiver() {
        super(Material.ROCK);

        setRegistryName("hyper_item_receiver");
        setUnlocalizedName("hyper_item_receiver");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();
        GameRegistry.registerTileEntity(TileHyperItemReceiver.class, MODID + ":hyper_item_receiver");
    }

    @Override
    public void registerRecipe() {
        if(OverloadedConfig.recipeEnabledConfig.hyperItemNodes)
            GameRegistry.addRecipe(new ItemStack(this), "IRI", "RNR", "IRI", 'R', Blocks.CHEST, 'I', Blocks.IRON_BLOCK, 'N', ModBlocks.netherStarBlock);
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileHyperItemReceiver();
    }

    @Nonnull
    @Override
    protected String getType() {
        return "Item";
    }
}
