package com.cjm721.overloaded.common.block.basic.hyperTransfer;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.common.block.tile.hyperTransfer.TileHyperFluidReceiver;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperFluidReceiver extends AbstractBlockHyperReceiver {

    public BlockHyperFluidReceiver() {
        super(Material.ROCK);

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();

        GameRegistry.registerTileEntity(TileHyperFluidReceiver.class, MODID + ":hyperFluidReceiver");
    }

    @Override
    public void registerRecipe() {

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
