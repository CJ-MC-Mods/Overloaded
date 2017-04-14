package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.tile.TileEnergyExtractor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by CJ on 4/13/2017.
 */
public class BlockEnergyExtractor extends ModBlock implements ITileEntityProvider {

    public BlockEnergyExtractor() {
        super(Material.ROCK);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileEnergyExtractor();
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    public void registerModel() {

    }
}
