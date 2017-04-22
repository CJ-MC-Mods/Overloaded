package com.cjm721.overloaded.common.block.basic.hyperTransfer;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.common.block.tile.hyperTransfer.TileHyperEnergyReceiver;
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

public class BlockHyperEnergyReceiver extends AbstractBlockHyperReceiver {

    public BlockHyperEnergyReceiver() {
        super(Material.ROCK);

        setRegistryName("hyper_energy_receiver");
        setUnlocalizedName("hyper_energy_receiver");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();
        GameRegistry.registerTileEntity(TileHyperEnergyReceiver.class, MODID + ":hyper_energy_receiver");
    }

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.hyperEnergyNodes)
            GameRegistry.addRecipe(new ItemStack(this), "IRI", "RNR", "IRI", 'R', Blocks.REDSTONE_BLOCK, 'I', Blocks.IRON_BLOCK, 'N', ModBlocks.netherStarBlock);
    }

    @Override
    @Nonnull
    protected String getType() {
        return "Energy";
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileHyperEnergyReceiver();
    }
}
