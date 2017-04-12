package com.cjm721.overloaded.common.block.basic.hyperTransfer;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.common.block.tile.hyperTransfer.TileHyperEnergyReceiver;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperEnderReceiver extends AbstractBlockHyperReceiver {

    public BlockHyperEnderReceiver() {
        super(Material.ROCK);

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.UTILITY);
        register();
        GameRegistry.registerTileEntity(TileHyperEnergyReceiver.class, MODID + ":hyperEnergyReceiver");
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    @Nonnull
    protected String getType() {
        return "Energy";
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Override
    @Nonnull
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileHyperEnergyReceiver();
    }
}
