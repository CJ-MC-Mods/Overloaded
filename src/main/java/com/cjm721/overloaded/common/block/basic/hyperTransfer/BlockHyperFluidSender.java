package com.cjm721.overloaded.common.block.basic.hyperTransfer;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.block.basic.hyperTransfer.base.AbstractBlockHyperSender;
import com.cjm721.overloaded.common.block.tile.hyperTransfer.TileHyperFluidSender;
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

public class BlockHyperFluidSender extends AbstractBlockHyperSender {

    public BlockHyperFluidSender() {
        super(Material.ROCK);

        setRegistryName("hyper_fluid_sender");
        setUnlocalizedName("hyper_fluid_sender");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();

        GameRegistry.registerTileEntity(TileHyperFluidSender.class, MODID + ":hyper_fluid_sender");
    }

    @Nonnull
    @Override
    public String getType() {
        return "Fluid";
    }

    @Override
    public void registerRecipe() {
        if(OverloadedConfig.recipeEnabledConfig.hyperFluidNodes)
            GameRegistry.addRecipe(new ItemStack(this), "IRI", "ENE", "IRI", 'R', Items.BUCKET, 'I', Blocks.IRON_BLOCK, 'N', ModBlocks.netherStarBlock, 'E', Items.ENDER_EYE);
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileHyperFluidSender();
    }
}
