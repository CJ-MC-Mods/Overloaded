package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.block.tile.hyperTransfer.TileHyperFluidReceiver;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperFluidReceiver extends AbstractBlockHyperReceiver {

    public BlockHyperFluidReceiver() {
        super(Material.ROCK);

        setRegistryName("hyper_fluid_receiver");
        setUnlocalizedName("hyper_fluid_receiver");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();

        GameRegistry.registerTileEntity(TileHyperFluidReceiver.class, MODID + ":hyper_fluid_receiver");
    }

    @Override
    public void registerRecipe() {
        if(OverloadedConfig.recipeEnabledConfig.hyperFluidNodes)
            GameRegistry.addRecipe(new ItemStack(this), "IRI", "RNR", "IRI", 'R', Items.BUCKET, 'I', Blocks.IRON_BLOCK, 'N', ModBlocks.netherStarBlock);
    }

    @Override
    @Nonnull
    protected String getType() {
        return "Fluid";
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileHyperFluidReceiver();
    }
}
