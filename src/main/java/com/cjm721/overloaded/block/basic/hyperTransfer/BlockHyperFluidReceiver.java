package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.block.tile.hyperTransfer.TileHyperFluidReceiver;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperFluidReceiver extends AbstractBlockHyperReceiver {

    public BlockHyperFluidReceiver() {
        super(Material.ROCK);
    }

    @Override
    public void baseInit() {
        setRegistryName("hyper_fluid_receiver");
        setUnlocalizedName("hyper_fluid_receiver");

        GameRegistry.registerTileEntity(TileHyperFluidReceiver.class, MODID + ":hyper_fluid_receiver");
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
