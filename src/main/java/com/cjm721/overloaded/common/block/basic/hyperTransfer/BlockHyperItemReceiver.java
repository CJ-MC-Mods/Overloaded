package com.cjm721.overloaded.common.block.basic.hyperTransfer;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.common.block.tile.hyperTransfer.TileHyperItemReceiver;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

/**
 * Created by CJ on 4/8/2017.
 */
public class BlockHyperItemReceiver extends AbstractBlockHyperReceiver {

    public BlockHyperItemReceiver() {
        super(Material.ROCK);

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.UTILITY);
        register();
        GameRegistry.registerTileEntity(TileHyperItemReceiver.class, MODID + ":hyperItemReceiver");
    }

    @Override
    public void registerRecipe() {

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
        return new TileHyperItemReceiver();
    }

    @Override
    protected String getType() {
        return "Item";
    }
}
