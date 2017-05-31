package com.cjm721.overloaded.common.block.basic.hyperTransfer;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.block.basic.hyperTransfer.base.AbstractBlockHyperSender;
import com.cjm721.overloaded.common.block.tile.hyperTransfer.TileHyperEnergySender;
import com.cjm721.overloaded.common.config.OverloadedConfig;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperEnergySender extends AbstractBlockHyperSender implements ITileEntityProvider {

    public BlockHyperEnergySender() {
        super(Material.ROCK);

        setRegistryName("hyper_energy_sender");
        setUnlocalizedName("hyper_energy_sender");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();

        GameRegistry.registerTileEntity(TileHyperEnergySender.class, MODID + ":hyper_energy_sender");
    }

    @Override
    public void registerRecipe() {
        if(OverloadedConfig.recipeEnabledConfig.hyperEnergyNodes)
            GameRegistry.addRecipe(new ItemStack(this), "IRI", "ENE", "IRI", 'R', Blocks.REDSTONE_BLOCK, 'I', Blocks.IRON_BLOCK, 'N', ModBlocks.netherStarBlock, 'E', Items.ENDER_EYE);
    }

    @Override
    @Nonnull
    public String getType() {
        return "Energy";
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileHyperEnergySender();
    }
}
